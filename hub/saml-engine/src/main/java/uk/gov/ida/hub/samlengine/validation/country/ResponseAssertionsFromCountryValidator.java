package uk.gov.ida.hub.samlengine.validation.country;

import org.opensaml.saml.saml2.core.Assertion;
import uk.gov.ida.saml.core.errors.SamlTransformationErrorFactory;
import uk.gov.ida.saml.core.validation.SamlTransformationErrorException;
import uk.gov.ida.saml.core.validation.SamlValidationSpecificationFailure;
import uk.gov.ida.saml.core.validators.assertion.AssertionValidator;
import uk.gov.ida.saml.core.validators.assertion.AuthnStatementAssertionValidator;
import uk.gov.ida.saml.core.validators.assertion.IdentityProviderAssertionValidator;
import uk.gov.ida.saml.security.validators.ValidatedResponse;

public class ResponseAssertionsFromCountryValidator {

    private final AssertionValidator assertionValidator;
    private final String expectedRecipientId;
    private final EidasAttributeStatementAssertionValidator eidasAttributeStatementAssertionValidator;
    private final AuthnStatementAssertionValidator authnStatementAssertionValidator;
    private final EidasAuthnResponseIssuerValidator authnResponseIssuerValidator;

    public ResponseAssertionsFromCountryValidator(
        IdentityProviderAssertionValidator assertionValidator,
        EidasAttributeStatementAssertionValidator eidasAttributeStatementAssertionValidator,
        AuthnStatementAssertionValidator authnStatementAssertionValidator,
        EidasAuthnResponseIssuerValidator authnResponseIssuerValidator,
        String expectedRecipientId) {

        this.assertionValidator = assertionValidator;
        this.expectedRecipientId = expectedRecipientId;
        this.eidasAttributeStatementAssertionValidator = eidasAttributeStatementAssertionValidator;
        this.authnStatementAssertionValidator = authnStatementAssertionValidator;
        this.authnResponseIssuerValidator = authnResponseIssuerValidator;
    }

    public void validate(ValidatedResponse validatedResponse, Assertion validatedIdentityAssertion) {

        assertionValidator.validate(validatedIdentityAssertion, validatedResponse.getInResponseTo(), expectedRecipientId);

        if (validatedResponse.isSuccess()) {

            if (validatedIdentityAssertion.getAuthnStatements().size() > 1) {
                SamlValidationSpecificationFailure failure = SamlTransformationErrorFactory.multipleAuthnStatements();
                throw new SamlTransformationErrorException(failure.getErrorMessage(), failure.getLogLevel());
            }

            authnStatementAssertionValidator.validate(validatedIdentityAssertion);
            eidasAttributeStatementAssertionValidator.validate(validatedIdentityAssertion);
            authnResponseIssuerValidator.validate(validatedResponse, validatedIdentityAssertion);
        }
    }
}
