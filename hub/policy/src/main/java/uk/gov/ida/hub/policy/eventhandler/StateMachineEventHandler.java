package uk.gov.ida.hub.policy.eventhandler;

import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.domain.exception.SessionNotFoundException;
import uk.gov.ida.hub.policy.exception.SessionTimeoutException;
import uk.gov.ida.hub.policy.statemachine.Event;
import uk.gov.ida.hub.policy.statemachine.Session;
import uk.gov.ida.hub.policy.statemachine.StateMachine;
import uk.gov.ida.hub.policy.statemachine.StateTNG;

import static java.text.MessageFormat.format;

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
        if (session.isTimedOut()){
            handleTimeOut();
        }
    }

    public final void handleEvent(Event event){
        startState = session.getCurrentState();
        endState = StateMachine.transition(startState, event);

        delegatedEventHandling(session);

        session.setCurrentState(endState);
        sessionRepository.updateSession(session);
    }

    public void handleTimeOut(){
        startState = session.getCurrentState();
        endState = StateMachine.transition(startState, Event.Session_Time_Out_Triggered);

        session.setCurrentState(endState);
        sessionRepository.updateSession(session);
        SessionId sessionId = session.getSessionId();
        throw new SessionTimeoutException(format("Session {0} timed out.", sessionId.getSessionId()), sessionId, session.getRequestIssuerEntityId(), session.getSessionExpiryTimestamp(), session.getRequestId());
    }

    public abstract void delegatedEventHandling(Session session);
}
