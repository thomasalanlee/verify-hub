package uk.gov.ida.hub.policy.eventhandler;

import com.google.inject.Inject;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.domain.exception.SessionNotFoundException;
import uk.gov.ida.hub.policy.eventhandler.StateMachineEventHandler;
import uk.gov.ida.hub.policy.logging.HubEventLogger;
import uk.gov.ida.hub.policy.statemachine.Event;
import uk.gov.ida.hub.policy.statemachine.Session;
import uk.gov.ida.hub.policy.statemachine.StateMachine;
import uk.gov.ida.hub.policy.statemachine.StateTNG;

public class IdpSelectedEventHandler extends StateMachineEventHandler {
    private final HubEventLogger hubEventLogger;
    private final String selectedIdpEntityId;

//    @Inject
//    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger){
//        this.sessionRepository = sessionRepository;
//        this.hubEventLogger = hubEventLogger;
//    }

    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger, SessionId sessionId, String selectedIdpEntityId){
        super(sessionRepository, sessionId);
        this.hubEventLogger = hubEventLogger;
        this.selectedIdpEntityId = selectedIdpEntityId;
    }

    @Override
    public void delegatedEventHandling(Session session) {

        session.setIdpEntityId(selectedIdpEntityId);
    }

}
