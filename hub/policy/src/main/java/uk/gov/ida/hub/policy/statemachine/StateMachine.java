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
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
//                    case No_Match_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.NULL;
                    case Match_Response_From_Matching_Service: return StateTNG.Successful_Match;
//                    case No_Match_Response_From_Matching_Service: return StateTNG.User_Account_Ceation_Request_Sent;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Cycle3_Data_Input_Cancelled:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Cycle3_Match_Request_Sent:
                switch(event){
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case Cancellation_Recieved: return StateTNG.Cycle3_Data_Input_Cancelled;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.NULL;
                    case Match_Response_From_Matching_Service: return StateTNG.Successful_Match;
                    case No_Match_Response_From_Matching_Service: return StateTNG.Successful_Match;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Eidas_Awaiting_Cycle3_Data:
                switch(event){
                    case Cycle3_Data_Submitted: return StateTNG.Eidas_Cycle3_Match_Request_Sent;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Eidas_Cycle0_And1_Match_Request_Sent:
                switch(event){
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case No_Match_Response_From_Matching_Service: return StateTNG.Eidas_Awaiting_Cycle3_Data;
                    case Match_Response_From_Matching_Service: return StateTNG.Eidas_Successful_Match;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
//                    case No_Match_Response_From_Matching_Service: return StateTNG.No_Match;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.NULL;

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
                    case Authentication_Failed_Response_From_Idp: return StateTNG.Authn_Failed_Error;
                    case No_Authentication_Context_Response_From_Idp: return StateTNG.Authn_Failed_Error;
                    case Success_Response_From_Idp_Recieved: return StateTNG.Cycle0_And1_Match_Request_Sent;
                    case Fraud_Response_From_Idp: return StateTNG.Fraud_Event_Detected;
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Paused_Registration_Response_From_Idp: return StateTNG.Paused_Registration;
                    case Requester_Error_Response_From_Idp: return StateTNG.Requester_Error;
//                    case No_Authentication_Context_Response_From_Idp: return StateTNG.Session_Started;
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
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Session_Started:
                switch(event){
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Idp_Registering: return StateTNG.Idp_Registering;
                    case Country_Selected: return StateTNG.Country_Selected;
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case Successful_Match:
                switch(event){
                    default: throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
                }

            case User_Account_Ceation_Request_Sent:
                switch(event){
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
                    case Match_Response_From_Matching_Service: return StateTNG.NULL;
                    case No_Match_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.User_Account_Created;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.User_Account_Creation_Failed;
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
