package uk.gov.ida.hub.policy.domain;

import org.joda.time.DateTime;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uk.gov.ida.hub.policy.statemachine.PolicyState;

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

    default PolicyState getNewState() {
        throw new NotImplementedException();
    }
}
