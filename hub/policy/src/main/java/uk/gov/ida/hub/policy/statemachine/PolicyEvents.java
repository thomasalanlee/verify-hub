package uk.gov.ida.hub.policy.statemachine;

public enum PolicyEvents {
    SELECT_IDP {
        @Override
        public PolicyState execute(PolicyState policyState) {
            return policyState.selectIdp();
        }
    },
    IDP_PAUSED_REGISTRATION {
        @Override
        public PolicyState execute(PolicyState policyState) {
            return policyState.idp_paused_registration();
        }
    };

    public abstract PolicyState execute(PolicyState policyState);
}
