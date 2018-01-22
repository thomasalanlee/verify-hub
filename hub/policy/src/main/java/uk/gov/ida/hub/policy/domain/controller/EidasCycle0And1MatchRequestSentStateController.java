package uk.gov.ida.hub.policy.domain.controller;

import com.google.common.base.Optional;
import uk.gov.ida.hub.policy.PolicyConfiguration;
import uk.gov.ida.hub.policy.domain.MatchFromMatchingService;
import uk.gov.ida.hub.policy.domain.ResponseFromHubFactory;
import uk.gov.ida.hub.policy.domain.State;
import uk.gov.ida.hub.policy.domain.StateTransitionAction;
import uk.gov.ida.hub.policy.domain.UserAccountCreatedFromMatchingService;
import uk.gov.ida.hub.policy.domain.state.EidasAwaitingCycle3DataState;
import uk.gov.ida.hub.policy.domain.state.EidasCycle0And1MatchRequestSentState;
import uk.gov.ida.hub.policy.domain.state.EidasSuccessfulMatchState;
import uk.gov.ida.hub.policy.logging.EventSinkHubEventLogger;
import uk.gov.ida.hub.policy.proxy.TransactionsConfigProxy;
import uk.gov.ida.hub.policy.services.AttributeQueryService;
import uk.gov.ida.hub.policy.validators.LevelOfAssuranceValidator;

public class EidasCycle0And1MatchRequestSentStateController extends MatchRequestSentStateController<EidasCycle0And1MatchRequestSentState> {
    private final TransactionsConfigProxy transactionsConfigProxy;

    public EidasCycle0And1MatchRequestSentStateController(
        final EidasCycle0And1MatchRequestSentState state,
        final StateTransitionAction stateTransitionAction,
        final EventSinkHubEventLogger eventSinkHubEventLogger,
        final PolicyConfiguration policyConfiguration,
        final LevelOfAssuranceValidator validator,
        final ResponseFromHubFactory responseFromHubFactory,
        final AttributeQueryService attributeQueryService,
        final TransactionsConfigProxy transactionsConfigProxy) {

        super(
            state,
            stateTransitionAction,
            eventSinkHubEventLogger,
            policyConfiguration,
            validator,
            responseFromHubFactory,
            attributeQueryService);

        this.transactionsConfigProxy = transactionsConfigProxy;
    }

    // TODO: The future story EID-269 will implement this method.
    @Override
    protected State getNextStateForUserAccountCreated(UserAccountCreatedFromMatchingService responseFromMatchingService) {
        return null;
    }

    // TODO: The future story EID-269 will implement this method.
    @Override
    protected State getNextStateForUserAccountCreationFailed() {
        return null;
    }

    protected EidasSuccessfulMatchState getEidasSuccessfulMatchState(MatchFromMatchingService responseFromMatchingService) {
        String matchingServiceAssertion = responseFromMatchingService.getMatchingServiceAssertion();
        validator.validate(responseFromMatchingService.getLevelOfAssurance(), state.getIdpLevelOfAssurance());
        String requestIssuerId = state.getRequestIssuerEntityId();
        return new EidasSuccessfulMatchState(
                state.getRequestId(),
                state.getSessionExpiryTimestamp(),
                state.getIdentityProviderEntityId(),
                matchingServiceAssertion,
                state.getRelayState(),
                requestIssuerId,
                state.getAssertionConsumerServiceUri(),
                state.getSessionId(),
                state.getIdpLevelOfAssurance(),
                state.getTransactionSupportsEidas()
        );
    }

    @Override
    protected State getNextStateForMatch(MatchFromMatchingService responseFromMatchingService) {
        eventSinkHubEventLogger.logCycle01SuccessfulMatchEvent(
            state.getSessionId(),
            state.getRequestIssuerEntityId(),
            state.getRequestId(),
            state.getSessionExpiryTimestamp());
        return getEidasSuccessfulMatchState(responseFromMatchingService);
    }

    @Override
    protected State getNextStateForNoMatch() {
        Optional<String> selfAssertedAttributeName = transactionsConfigProxy.getMatchingProcess(state.getRequestIssuerEntityId()).getAttributeName();
        if (selfAssertedAttributeName.isPresent()) {
            eventSinkHubEventLogger.logWaitingForCycle3AttributesEvent(
                state.getSessionId(),
                state.getRequestIssuerEntityId(),
                state.getRequestId(),
                state.getSessionExpiryTimestamp());
            return getAwaitingCycle3DataState();
        }

        eventSinkHubEventLogger.logCycle01NoMatchEvent(
            state.getSessionId(),
            state.getRequestIssuerEntityId(),
            state.getRequestId(),
            state.getSessionExpiryTimestamp());
        return getNoMatchState();
    }

    private EidasAwaitingCycle3DataState getAwaitingCycle3DataState() {
        return new EidasAwaitingCycle3DataState(
            state.getRequestId(),
            state.getRequestIssuerEntityId(),
            state.getSessionExpiryTimestamp(),
            state.getAssertionConsumerServiceUri(),
            state.getSessionId(),
            state.getTransactionSupportsEidas(),
            state.getIdentityProviderEntityId(),
            state.getMatchingServiceAdapterEntityId(),
            state.getRelayState(),
            state.getPersistentId(),
            state.getIdpLevelOfAssurance(),
            state.getEncryptedIdentityAssertion());
    }
}
