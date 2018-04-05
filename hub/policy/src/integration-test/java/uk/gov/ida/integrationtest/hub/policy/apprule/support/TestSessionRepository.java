package uk.gov.ida.integrationtest.hub.policy.apprule.support;

import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.State;
import uk.gov.ida.hub.policy.statemachine.Session;

import javax.inject.Inject;
import java.util.concurrent.ConcurrentMap;

public class TestSessionRepository {

    private final ConcurrentMap<SessionId, State> dataStore;
    private final ConcurrentMap<SessionId, Session> sessionStore;
    private final ConcurrentMap<SessionId, DateTime> sessionStartedMap;

    @Inject
    public TestSessionRepository(ConcurrentMap<SessionId, State> dataStore, ConcurrentMap<SessionId, Session> sessionStore, ConcurrentMap<SessionId, DateTime> sessionStartedMap) {
        this.dataStore = dataStore;
        this.sessionStore = sessionStore;
        this.sessionStartedMap = sessionStartedMap;
    }

    public void createSession(SessionId sessionId, State state) {
        dataStore.put(sessionId, state);
        sessionStartedMap.put(sessionId, state.getSessionExpiryTimestamp());
    }

    public void createSession(SessionId sessionId, State state, Session session) {
        dataStore.put(sessionId, state);
        sessionStore.put(sessionId, session);
        sessionStartedMap.put(sessionId, state.getSessionExpiryTimestamp());
    }

    public State getSession(SessionId sessionId) {
        return dataStore.get(sessionId);
    }
}
