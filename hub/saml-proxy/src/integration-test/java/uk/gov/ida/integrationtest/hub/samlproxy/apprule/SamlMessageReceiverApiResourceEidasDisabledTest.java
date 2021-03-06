package uk.gov.ida.integrationtest.hub.samlproxy.apprule;

import helpers.JerseyClientConfigurationBuilder;
import httpstub.HttpStubRule;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.util.Duration;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.opensaml.xmlsec.algorithm.DigestAlgorithm;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;
import org.opensaml.xmlsec.algorithm.descriptors.DigestSHA256;
import org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA1;
import uk.gov.ida.hub.samlproxy.Urls;
import uk.gov.ida.hub.samlproxy.contracts.SamlRequestDto;
import uk.gov.ida.hub.samlproxy.domain.LevelOfAssurance;
import uk.gov.ida.integrationtest.hub.samlproxy.apprule.support.PolicyStubRule;
import uk.gov.ida.integrationtest.hub.samlproxy.apprule.support.SamlProxyAppRule;
import uk.gov.ida.saml.hub.domain.Endpoints;
import uk.gov.ida.saml.idp.test.AuthnResponseFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.ida.saml.core.test.TestCertificateStrings.STUB_IDP_PUBLIC_PRIMARY_CERT;
import static uk.gov.ida.saml.core.test.TestCertificateStrings.STUB_IDP_PUBLIC_PRIMARY_PRIVATE_KEY;
import static uk.gov.ida.saml.core.test.TestEntityIds.STUB_IDP_ONE;
import static uk.gov.ida.saml.idp.test.AuthnResponseFactory.anAuthnResponseFactory;

public class SamlMessageReceiverApiResourceEidasDisabledTest {
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = new SignatureRSASHA1();
    private static final DigestAlgorithm DIGEST_ALGORITHM = new DigestSHA256();

    private static Client client;

    @ClassRule
    public static PolicyStubRule policyStubRule = new PolicyStubRule();

    @ClassRule
    public static HttpStubRule eventSinkStubRule = new HttpStubRule();

    @Before
    public void resetStubRules() {
        policyStubRule.reset();
        eventSinkStubRule.reset();
    }

    @ClassRule
    public static SamlProxyAppRule samlProxyAppRule = new SamlProxyAppRule(false,
            ConfigOverride.config("policyUri", policyStubRule.baseUri().build().toASCIIString()),
            ConfigOverride.config("eventSinkUri", eventSinkStubRule.baseUri().build().toASCIIString()));

    private AuthnResponseFactory authnResponseFactory = anAuthnResponseFactory();

    @Before
    public void setUp() throws Exception {
    }

    @BeforeClass
    public static void setUpClient() throws Exception {
        JerseyClientConfiguration jerseyClientConfiguration = JerseyClientConfigurationBuilder.aJerseyClientConfiguration().withTimeout(Duration.seconds(10)).build();
        client =  new JerseyClientBuilder(samlProxyAppRule.getEnvironment()).using(jerseyClientConfiguration).build
                (SamlMessageReceiverApiResourceEidasDisabledTest.class.getSimpleName());
        eventSinkStubRule.register(Urls.HubSupportUrls.HUB_SUPPORT_EVENT_SINK_RESOURCE, Response.Status.OK.getStatusCode());
    }

    @Test
    public void responsePost_shouldRespondWith404_whenEidasIsDisabled() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        policyStubRule.receiveAuthnResponseFromIdp(sessionId, LevelOfAssurance.LEVEL_2);

        final String samlResponse = authnResponseFactory.aSamlResponseFromIdp(
                STUB_IDP_ONE,
                STUB_IDP_PUBLIC_PRIMARY_CERT,
                STUB_IDP_PUBLIC_PRIMARY_PRIVATE_KEY,
                Endpoints.SSO_RESPONSE_ENDPOINT,
                SIGNATURE_ALGORITHM,
                DIGEST_ALGORITHM);
        SamlRequestDto authnResponse = new SamlRequestDto(samlResponse, sessionId, "127.0.0.1");

        final Response response = postSAML(authnResponse, Urls.SamlProxyUrls.EIDAS_SAML2_SSO_RECEIVER_API_RESOURCE);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    private Response postSAML(SamlRequestDto requestDTO, String path) {
        return client.target(samlProxyAppRule.getUri(path)).request().post(Entity
                .entity(requestDTO, MediaType
                .APPLICATION_JSON_TYPE));
    }
}
