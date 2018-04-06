package uk.gov.ida.hub.policy.statemachine;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uk.gov.ida.hub.policy.domain.State;

public interface PolicyState {

    default State toOldStyleState(MegaStateInfo stateInfo) {
        throw new NotImplementedException();
    }

    default PolicyState selectIdp() {
        throw new IllegalStateException();
    }

    default PolicyState idp_paused_registration() {
        throw new IllegalStateException();
    }


}
