package uk.gov.ida.hub.policy.statemachine;

import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.gov.ida.hub.policy.domain.State;

public class PausedRegistrationState implements PolicyState {

    public State toOldStyleState(MegaStateInfo stateInfo) {
        return new uk.gov.ida.hub.policy.domain.state.PausedRegistrationState(
             stateInfo.requestId,
             stateInfo.requestIssuerEntityId,
             stateInfo.sessionExpiryTimestamp,
             stateInfo.assertionConsumerServiceUri,
             stateInfo.sessionId,
             stateInfo.transactionSupportsEidas,
            Optional.fromNullable(stateInfo.relayState));
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
