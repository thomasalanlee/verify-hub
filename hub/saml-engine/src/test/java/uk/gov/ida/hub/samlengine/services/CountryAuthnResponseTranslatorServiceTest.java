package uk.gov.ida.hub.samlengine.services;

import io.dropwizard.testing.ResourceHelpers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import uk.gov.ida.hub.samlengine.contracts.SamlAuthnResponseTranslatorDto;
import uk.gov.ida.hub.samlengine.domain.InboundResponseFromCountry;
import uk.gov.ida.hub.samlengine.validation.country.ResponseAssertionsFromCountryValidator;
import uk.gov.ida.hub.samlengine.validation.country.ResponseFromCountryValidator;
import uk.gov.ida.saml.core.IdaSamlBootstrap;
import uk.gov.ida.saml.core.transformers.AuthnContextFactory;
import uk.gov.ida.saml.core.transformers.outbound.decorators.AssertionBlobEncrypter;
import uk.gov.ida.saml.deserializers.StringToOpenSamlObjectTransformer;
import uk.gov.ida.saml.deserializers.parser.SamlObjectParser;
import uk.gov.ida.saml.hub.domain.IdpIdaStatus;
import uk.gov.ida.saml.hub.transformers.inbound.IdpIdaStatusUnmarshaller;
import uk.gov.ida.saml.hub.transformers.inbound.PassthroughAssertionUnmarshaller;
import uk.gov.ida.saml.hub.transformers.inbound.SamlStatusToIdpIdaStatusMappingsFactory;
import uk.gov.ida.saml.hub.transformers.inbound.decorators.ValidateSamlResponseIssuedByIdpDestination;
import uk.gov.ida.saml.security.AssertionDecrypter;
import uk.gov.ida.saml.security.SamlAssertionsSignatureValidator;
import uk.gov.ida.saml.security.validators.ValidatedAssertions;
import uk.gov.ida.saml.security.validators.ValidatedResponse;
import uk.gov.ida.saml.security.validators.signature.SamlResponseSignatureValidator;
import uk.gov.ida.saml.serializers.XmlObjectToBase64EncodedStringTransformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static uk.gov.ida.hub.samlengine.domain.LevelOfAssurance.LEVEL_3;

@RunWith(MockitoJUnitRunner.class)
public class CountryAuthnResponseTranslatorServiceTest {

    @Mock
    private StringToOpenSamlObjectTransformer<Response> stringToOpenSamlResponseTransformer;
    @Mock
    private ResponseFromCountryValidator responseFromCountryValidator;
    @Mock
    private ResponseAssertionsFromCountryValidator responseAssertionsFromCountryValidator;
    @Mock
    private SamlAuthnResponseTranslatorDto samlAuthnResponseTranslatorDto;
    @Mock
    private AssertionDecrypter assertionDecrypter;
    @Mock
    private AssertionBlobEncrypter assertionBlobEncrypter;
    @Mock
    private SamlResponseSignatureValidator samlResponseSignatureValidator;
    @Mock
    private SamlAssertionsSignatureValidator samlAssertionsSignatureValidator;
    @Mock
    private ValidateSamlResponseIssuedByIdpDestination validateSamlResponseIssuedByIdpDestination;

    private String persistentIdName = "UK/GB/12345";
    private String responseIssuer = "http://localhost:56002/ServiceMetadata";
    private String identityUnderlyingAssertionBlob = "encryptedBlob";

    private CountryAuthnResponseTranslatorService service;

    private SAMLObject buildResponseFromFile() throws IOException, javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, org.opensaml.core.xml.io.UnmarshallingException {
        String xmlString = new String(Files.readAllBytes(Paths.get(ResourceHelpers.resourceFilePath("EIDASAMLResponse.xml"))));
        return (Response) new SamlObjectParser().getSamlObject(xmlString);
    }

    @Before
    public void setup() throws Exception {
        IdaSamlBootstrap.bootstrap();
        service = new CountryAuthnResponseTranslatorService(
                stringToOpenSamlResponseTransformer,
                responseFromCountryValidator,
                new IdpIdaStatusUnmarshaller(new IdpIdaStatus.IdpIdaStatusFactory(), new SamlStatusToIdpIdaStatusMappingsFactory()),
                responseAssertionsFromCountryValidator,
                validateSamlResponseIssuedByIdpDestination,
                assertionDecrypter,
                assertionBlobEncrypter,
                samlResponseSignatureValidator,
                samlAssertionsSignatureValidator,
                new PassthroughAssertionUnmarshaller(new XmlObjectToBase64EncodedStringTransformer<>(), new AuthnContextFactory()));

        Response eidasSAMLResponse = (Response) buildResponseFromFile();
        ValidatedResponse validateEIDASSAMLResponse = new ValidatedResponse(eidasSAMLResponse);
        List<Assertion> decryptedAssertions = eidasSAMLResponse.getAssertions();

        when(samlAuthnResponseTranslatorDto.getSamlResponse()).thenReturn("eidas");
        when(samlAuthnResponseTranslatorDto.getMatchingServiceEntityId()).thenReturn("mid");
        when(stringToOpenSamlResponseTransformer.apply("eidas")).thenReturn(eidasSAMLResponse);
        doNothing().when(responseFromCountryValidator).validate(eidasSAMLResponse);
        when(samlResponseSignatureValidator.validate(eidasSAMLResponse, IDPSSODescriptor.DEFAULT_ELEMENT_NAME)).thenReturn(validateEIDASSAMLResponse);
        when(assertionDecrypter.decryptAssertions(validateEIDASSAMLResponse)).thenReturn(decryptedAssertions);
        when(assertionBlobEncrypter.encryptAssertionBlob(eq("mid"), any(String.class))).thenReturn(identityUnderlyingAssertionBlob);
        when(samlAssertionsSignatureValidator.validate(decryptedAssertions, IDPSSODescriptor.DEFAULT_ELEMENT_NAME)).thenReturn(new ValidatedAssertions(decryptedAssertions));
    }

    @Test
    public void shouldExtractAuthnStatementAssertionDetails() {
        InboundResponseFromCountry result = service.translate(samlAuthnResponseTranslatorDto);

        assertThat(result.getIssuer()).isEqualTo(responseIssuer);

        assertThat(result.getStatus().isPresent()).isTrue();
        assertThat(result.getStatus().get()).isEqualTo("Success");

        assertThat(result.getStatusMessage().isPresent()).isFalse();

        assertThat(result.getLevelOfAssurance().isPresent()).isTrue();
        assertThat(result.getLevelOfAssurance().get()).isEqualTo(LEVEL_3);

        assertThat(result.getPersistentId().isPresent()).isTrue();
        assertThat(result.getPersistentId().get()).isEqualTo(persistentIdName);

        assertThat(result.getEncryptedIdentityAssertionBlob().isPresent()).isTrue();
        assertThat(result.getEncryptedIdentityAssertionBlob().get()).isEqualTo(identityUnderlyingAssertionBlob);
    }

}