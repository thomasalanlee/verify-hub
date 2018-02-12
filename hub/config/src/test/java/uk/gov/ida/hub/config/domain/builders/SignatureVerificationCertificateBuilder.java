package uk.gov.ida.hub.config.domain.builders;

import uk.gov.ida.common.shared.configuration.DeserializablePublicKeyConfiguration;
import uk.gov.ida.common.shared.configuration.X509CertificateConfiguration;
import uk.gov.ida.hub.config.domain.SignatureVerificationCertificate;

import static uk.gov.ida.saml.core.test.TestCertificateStrings.HUB_TEST_PUBLIC_SIGNING_CERT;

public class SignatureVerificationCertificateBuilder {

    private String x509Value = HUB_TEST_PUBLIC_SIGNING_CERT;

    public static SignatureVerificationCertificateBuilder aSignatureVerificationCertificate() {
        return new SignatureVerificationCertificateBuilder();
    }

    public SignatureVerificationCertificate build() {
        DeserializablePublicKeyConfiguration configuration = new X509CertificateConfiguration(x509Value.trim());
        return new SignatureVerificationCertificate(configuration);
    }

    public SignatureVerificationCertificateBuilder withX509(String x509Value) {
        this.x509Value = x509Value;
        return this;
    }
}
