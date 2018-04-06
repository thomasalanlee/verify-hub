package uk.gov.ida.hub.policy.statemachine;

import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;
import java.util.List;

public class MegaStateInfo {

    public final String requestId;
    public final String idpEntity;
    public final String matchingServiceEntityId;
    public final List<LevelOfAssurance> levelsOfAssurance;
    public final Boolean useExactComparisonType;
    public final Boolean forceAuthentication;
    public final URI assertionConsumerServiceUri;
    public final String requestIssuerId;
    public final String relayState;
    public final DateTime sessionExpiryTimestamp;
    public final boolean registering;
    public final LevelOfAssurance requestedLoa;
    public final SessionId sessionId;
    public final List<String> availableIdentityProviders;
    public final boolean transactionSupportsEidas;
    public final String requestIssuerEntityId;

    public MegaStateInfo(String requestId,
                         String idpEntity,
                         String matchingServiceEntityId,
                         List<LevelOfAssurance> levelsOfAssurance,
                         Boolean useExactComparisonType,
                         Boolean forceAuthentication,
                         URI assertionConsumerServiceUri,
                         String requestIssuerId,
                         String relayState,
                         DateTime sessionExpiryTimestamp,
                         boolean registering,
                         LevelOfAssurance requestedLoa,
                         SessionId sessionId,
                         List<String> availableIdentityProviders,
                         boolean transactionSupportsEidas,
                         String requestIssuerEntityId) {
        this.requestId = requestId;
        this.idpEntity = idpEntity;
        this.matchingServiceEntityId = matchingServiceEntityId;
        this.levelsOfAssurance = levelsOfAssurance;
        this.useExactComparisonType = useExactComparisonType;
        this.forceAuthentication = forceAuthentication;
        this.assertionConsumerServiceUri = assertionConsumerServiceUri;
        this.requestIssuerId = requestIssuerId;
        this.relayState = relayState;
        this.sessionExpiryTimestamp = sessionExpiryTimestamp;
        this.registering = registering;
        this.requestedLoa = requestedLoa;
        this.sessionId = sessionId;
        this.availableIdentityProviders = availableIdentityProviders;
        this.transactionSupportsEidas = transactionSupportsEidas;
        this.requestIssuerEntityId = requestIssuerEntityId;
    }

    public static class MegaStateInfoBuilder {
        private String requestId;
        private String idpEntity;
        private String matchingServiceEntityId;
        private List<LevelOfAssurance> levelsOfAssurance;
        private Boolean useExactComparisonType;
        private Boolean forceAuthentication;
        private URI assertionConsumerServiceUri;
        private String requestIssuerId;
        private String relayState;
        private DateTime sessionExpiryTimestamp;
        private boolean registering;
        private LevelOfAssurance requestedLoa;
        private SessionId sessionId;
        private List<String> availableIdentityProviders;
        private boolean transactionSupportsEidas;
        private String requestIssuerEntityId;

        public static MegaStateInfoBuilder aMegaStateInfo() {
            return new MegaStateInfoBuilder();
        }

        public MegaStateInfoBuilder withRequestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public MegaStateInfoBuilder withIdpEntity(String idpEntity) {
            this.idpEntity = idpEntity;
            return this;
        }

        public MegaStateInfoBuilder withMatchingServiceEntityId(String matchingServiceEntityId) {
            this.matchingServiceEntityId = matchingServiceEntityId;
            return this;
        }

        public MegaStateInfoBuilder with(List<LevelOfAssurance> levelsOfAssurance) {
            this.levelsOfAssurance = levelsOfAssurance;
            return this;
        }

        public MegaStateInfoBuilder withUseExactComparisonType(Boolean useExactComparisonType) {
            this.useExactComparisonType = useExactComparisonType;
            return this;
        }

        public MegaStateInfoBuilder withForceAuthentication(Boolean forceAuthentication) {
            this.forceAuthentication = forceAuthentication;
            return this;
        }

        public MegaStateInfoBuilder withAssertionConsumerServiceUri(URI assertionConsumerServiceUri) {
            this.assertionConsumerServiceUri = assertionConsumerServiceUri;
            return this;
        }

        public MegaStateInfoBuilder withRequestIssuerId(String requestIssuerId) {
            this.requestIssuerId = requestIssuerId;
            return this;
        }

        public MegaStateInfoBuilder withRelayState(String relayState) {
            this.relayState = relayState;
            return this;
        }

        public MegaStateInfoBuilder withSessionExpiryTimestamp(DateTime sessionExpiryTimestamp) {
            this.sessionExpiryTimestamp = sessionExpiryTimestamp;
            return this;
        }

        public MegaStateInfoBuilder withRegistering(Boolean registering) {
            this.registering = registering;
            return this;
        }

        public MegaStateInfoBuilder withRequestedLoa(LevelOfAssurance requestedLoa) {
            this.requestedLoa = requestedLoa;
            return this;
        }

        public MegaStateInfoBuilder withsessionId(SessionId sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public MegaStateInfoBuilder withavailableIdentityProviders(List<String> availableIdentityProviders) {
            this.availableIdentityProviders = availableIdentityProviders;
            return this;
        }

        public MegaStateInfoBuilder withtransactionSupportsEidas(boolean transactionSupportsEidas) {
            this.transactionSupportsEidas = transactionSupportsEidas;
            return this;
        }

        public MegaStateInfoBuilder withrequestIssuerEntityId(String requestIssuerEntityId) {
            this.requestIssuerEntityId = requestIssuerEntityId;
            return this;
        }

        public MegaStateInfo build() {
            return new MegaStateInfo(
                requestId,
                idpEntity,
                matchingServiceEntityId,
                levelsOfAssurance,
                useExactComparisonType,
                forceAuthentication,
                assertionConsumerServiceUri,
                requestIssuerId,
                relayState,
                sessionExpiryTimestamp,
                registering,
                requestedLoa,
                sessionId,
                availableIdentityProviders,
                transactionSupportsEidas,
                requestIssuerEntityId);
        }
    }
}
