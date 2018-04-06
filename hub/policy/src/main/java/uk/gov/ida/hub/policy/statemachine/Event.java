package uk.gov.ida.hub.policy.statemachine;

public enum Event {

    Authentication_Failed_Response_From_Idp,
    Cancellation_Received,
    Country_Selected,
    Cycle3_Data_Submitted,
    Fraud_Response_From_Idp,
    Idp_Selected_For_Registration,
    Idp_Selected,
    Match_Response_From_Matching_Service,
    No_Authentication_Context_Response_From_Idp,
    No_Match_Response_From_Matching_Service,
    Paused_Registration_Response_From_Idp,
    Requester_Error_Response_From_Idp,
    Request_Failure,
    Response_Processing_Details_Received,
    Session_Time_Out_Triggered,
    Success_Response_From_Idp_Received,
    Transition_To_Eidas_Cycle0_And1_Match_Request_Sent_State,
    Try_Another_Idp_Selected,
    User_Account_Created_Response_From_Matching_Service,
    User_Account_Creation_Failed_Response_From_Matching_Service

}
