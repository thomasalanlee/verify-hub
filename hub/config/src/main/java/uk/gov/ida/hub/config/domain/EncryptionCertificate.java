package uk.gov.ida.hub.config.domain;


import uk.gov.ida.common.shared.configuration.DeserializablePublicKeyConfiguration;

public class EncryptionCertificate extends Certificate {

    protected String type;

    public EncryptionCertificate() {
    }

    public EncryptionCertificate(DeserializablePublicKeyConfiguration publicKeyConfiguration) {
        this.fullCert = publicKeyConfiguration.getCert();
        this.type = publicKeyConfiguration.getType();
    }

    public String getType() {
        return type;
    }

    @Override
    public CertificateType getUse() {
        return CertificateType.ENCRYPTION;
    }
}
