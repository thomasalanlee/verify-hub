package uk.gov.ida.hub.policy.statemachine;

public class PolicyStateMachine {

    public PolicyState determineState(PolicyState currentState, PolicyEvents event) {
        return event.execute(currentState.selectIdp());
    }
}
