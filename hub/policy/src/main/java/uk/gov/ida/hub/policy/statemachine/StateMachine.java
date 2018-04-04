package uk.gov.ida.hub.policy.statemachine;

import uk.gov.ida.hub.policy.statemachine.StateTNG;

public class StateMachine {


    public static StateTNG transition(StateTNG currentState, Event event){
        switch (currentState){

            case Authn_Failed_Error:
                switch(event){
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Try_Another_Idp_Selected: return StateTNG.Session_Started;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Awaiting_Cycle3_Data:
                switch(event){
                    case Cancellation_Recieved: return StateTNG.Cycle3_Data_Input_Cancelled;
                    case Cycle3_Data_Submitted: return StateTNG.Cycle3_Match_Request_Sent;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Country_Selected:
                switch(event){
                    case Country_Selected: return StateTNG.Country_Selected;
                    case Transition_To_Eidas_Cycle0_And1_Match_Request_Sent_State: return StateTNG.Eidas_Cycle0_And1_Match_Request_Sent;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Cycle0_And1_Match_Request_Sent:
                switch(event){
                    case No_Match_Response_From_Matching_Service: return StateTNG.Awaiting_Cycle3_Data;
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Cycle3_Data_Input_Cancelled:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Cycle3_Match_Request_Sent:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Eidas_Awaiting_Cycle3_Data:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Eidas_Cycle0_And1_Match_Request_Sent:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Eidas_Cycle3_Match_Request_Sent:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Eidas_Successful_Match:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Fraud_Event_Detected:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Idp_Selected:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Matching_Service_Request_Error:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case No_Match:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Paused_Registration:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Requester_Error:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Session_Started:
                switch(event){
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Country_Selected: return StateTNG.Country_Selected;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case Successful_Match:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case User_Account_Ceation_Request_Sent:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case User_Account_Created:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case User_Account_Creation_Failed:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
            case User_Account_Creation_Request_Sent:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }
        }

        throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
    }

}
