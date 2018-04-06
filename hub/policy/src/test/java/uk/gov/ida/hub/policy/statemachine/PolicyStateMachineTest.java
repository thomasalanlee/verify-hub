package uk.gov.ida.hub.policy.statemachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.ida.hub.policy.statemachine.PolicyEvents.IDP_PAUSED_REGISTRATION;
import static uk.gov.ida.hub.policy.statemachine.PolicyEvents.SELECT_IDP;

@RunWith(Parameterized.class)
public class PolicyStateMachineTest {
    private PolicyEvents event;
    private PolicyState currentState;
    private PolicyState expectedState;

    private static final PolicyState SELECTED_IDP_STATE = new SelectIdpState();
    private static final PolicyState PAUSED_REGISTRATION_STATE = new PausedRegistrationState();

    private PolicyStateMachine policyStateMachine;


    @Before
    public void initialize() {
        policyStateMachine = new PolicyStateMachine();
    }

    public PolicyStateMachineTest(PolicyEvents event, PolicyState currentState, PolicyState expectedState) {
        this.event = event;
        this.currentState = currentState;
        this.expectedState = expectedState;
    }

    @Parameterized.Parameters
    public static Collection stateAndEvent() {
        return Arrays.asList(new Object[][] {
                { SELECT_IDP, SELECTED_IDP_STATE, SELECTED_IDP_STATE},
                { IDP_PAUSED_REGISTRATION, SELECTED_IDP_STATE, PAUSED_REGISTRATION_STATE}
        });
    }

    @Test
    public void applyEventToState() {
        assertThat(policyStateMachine.determineState(currentState, event)).isEqualTo(expectedState);
    }
}
