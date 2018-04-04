package uk.gov.ida.hub.policy.controllogic;

import com.google.inject.Inject;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.logging.HubEventLogger;
import uk.gov.ida.hub.policy.statemachine.*;

public class IdpSelectedEventHandler {

    private final HubEventLogger hubEventLogger;
    private final SessionRepository sessionRepository;

    @Inject
    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger){
        this.sessionRepository = sessionRepository;
        this.hubEventLogger = hubEventLogger;
    }

    public void register(SessionId sessionId) {
        Event event =Event.Idp_Registering;
        Session session = sessionRepository.getSession(sessionId);
        StateTNG currentState = session.getCurrentState();
        StateTNG newState = StateMachine.transition(currentState, event);

        session.setCurrentState(newState);
        sessionRepository.updateSession(session);

        hubEventLogger.logIdpSelectedEvent(session);
    }

    public void signin(SessionId sessionId) {
        Event event =Event.Idp_Selected;
        Session session = sessionRepository.getSession(sessionId);
        StateTNG currentState = session.getCurrentState();
        StateTNG newState = StateMachine.transition(currentState, event);

        session.setCurrentState(newState);
        sessionRepository.updateSession(session);

    }
}
