package uk.gov.ida.hub.policy.eventhandler;

import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.domain.exception.SessionNotFoundException;
import uk.gov.ida.hub.policy.statemachine.Event;
import uk.gov.ida.hub.policy.statemachine.Session;
import uk.gov.ida.hub.policy.statemachine.StateMachine;
import uk.gov.ida.hub.policy.statemachine.StateTNG;

public abstract class StateMachineEventHandler {

    private Session session;
    private SessionRepository sessionRepository;
    private StateTNG startState;
    private StateTNG endState;

    public StateMachineEventHandler(SessionRepository sessionRepository, SessionId sessionId){
        this.sessionRepository = sessionRepository;
        session = sessionRepository.getSession(sessionId);
        if (session == null){
            throw new SessionNotFoundException(sessionId);
        }
    }

    public void handleEvent(Event event){
        startState = session.getCurrentState();
        endState = StateMachine.transition(startState, event);

        delegatedEventHandling(session);

        session.setCurrentState(endState);
        sessionRepository.updateSession(session);
    }

    public abstract void delegatedEventHandling(Session session);
}
