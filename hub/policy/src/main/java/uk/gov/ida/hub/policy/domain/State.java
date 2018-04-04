package uk.gov.ida.hub.policy.domain;

import org.apache.commons.lang3.NotImplementedException;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.statemachine.StateTNG;

import java.net.URI;

public interface State {
    String getRequestId();

    SessionId getSessionId();

    @SuppressWarnings("unused") // marker method
    void doNotDirectlyImplementThisInterface();

    String getRequestIssuerEntityId();

    DateTime getSessionExpiryTimestamp();

    URI getAssertionConsumerServiceUri();

    boolean getTransactionSupportsEidas();

    default StateTNG getThisState(){
        throw new NotImplementedException("You suck!");
    }

}
