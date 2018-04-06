package uk.gov.ida.hub.policy.statemachine;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.state.SessionStartedState;

import java.util.List;

public class Session {

    private SessionId sessionId;
    private StateTNG currentState;
    private List<LevelOfAssurance> levelsOfAssurance;
    private String requestIssuerEntityId;
    private DateTime sessionExpiryTimestamp;
    private String requestId;
    private String idpEntityId;

    public static Session createNewStartSession(SessionId sessionId) {
        Session session = new Session();
        session.setSessionId(sessionId);

        return session;
    }

    public boolean isTimedOut() {
        return sessionExpiryTimestamp == null || sessionExpiryTimestamp.isBeforeNow();
    }

    public SessionId getSessionId(){
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public StateTNG getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateTNG currentState) {
        this.currentState = currentState;
    }

    public List<LevelOfAssurance> getLevelsOfAssurance() {
        return levelsOfAssurance;
    }

    public void setLevelsOfAssurance(List<LevelOfAssurance> levelsOfAssurance) {
        this.levelsOfAssurance = levelsOfAssurance;
    }

    public String getRequestIssuerEntityId() {
        return requestIssuerEntityId;
    }

    public void setRequestIssuerEntityId(String requestIssuerEntityId) {
        this.requestIssuerEntityId = requestIssuerEntityId;
    }

    public DateTime getSessionExpiryTimestamp(){
        return sessionExpiryTimestamp;
    }

    public void setSessionExpiryTimestamp(DateTime sessionExpiryTimestamp) {
        this.sessionExpiryTimestamp = sessionExpiryTimestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getIdpEntityId() {
        return idpEntityId;

    }

    public void setIdpEntityId(String idpEntityId) {
        this.idpEntityId = idpEntityId;
    }


}
