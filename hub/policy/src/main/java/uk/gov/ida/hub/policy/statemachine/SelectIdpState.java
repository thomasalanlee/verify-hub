package uk.gov.ida.hub.policy.statemachine;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.gov.ida.hub.policy.domain.State;
import uk.gov.ida.hub.policy.domain.state.IdpSelectedState;

public class SelectIdpState implements PolicyState {

    @Override
    public PolicyState selectIdp() {
        return new SelectIdpState();
    }

    @Override
    public PolicyState idp_paused_registration() {
        return new PausedRegistrationState();
    }

    @Override
    public State toOldStyleState(MegaStateInfo stateInfo) {
        return new IdpSelectedState(
                stateInfo.requestId,
                stateInfo.idpEntity,
                stateInfo.matchingServiceEntityId,
                stateInfo.levelsOfAssurance,
                stateInfo.useExactComparisonType,
                stateInfo.forceAuthentication,
                stateInfo.assertionConsumerServiceUri,
                stateInfo.requestIssuerId,
                stateInfo.relayState,
                stateInfo.sessionExpiryTimestamp,
                stateInfo.registering,
                stateInfo.requestedLoa,
                stateInfo.sessionId,
                stateInfo.availableIdentityProviders,
                stateInfo.transactionSupportsEidas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .toHashCode();
    }

}
