package uk.gov.ida.hub.policy.domain;

public enum ResponseProcessingStatus {
    GOTO_HUB_LANDING_PAGE,
    WAIT,
    GET_C3_DATA,
    SEND_NO_MATCH_RESPONSE_TO_TRANSACTION,
    SEND_SUCCESSFUL_MATCH_RESPONSE_TO_TRANSACTION,
    SHOW_MATCHING_ERROR_PAGE,
    SEND_USER_ACCOUNT_CREATED_RESPONSE_TO_TRANSACTION,
    USER_ACCOUNT_CREATION_FAILED,
}