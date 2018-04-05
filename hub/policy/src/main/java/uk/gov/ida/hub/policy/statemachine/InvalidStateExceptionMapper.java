package uk.gov.ida.hub.policy.statemachine;

import uk.gov.ida.common.ErrorStatusDto;
import uk.gov.ida.common.ExceptionType;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.state.IdpSelectedState;
import uk.gov.ida.hub.policy.domain.state.SessionStartedState;
import uk.gov.ida.hub.policy.exception.InvalidSessionStateException;
import uk.gov.ida.shared.utils.logging.LogFormatter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.UUID;

public class InvalidStateExceptionMapper implements ExceptionMapper<InvalidStateException>{

    @Override
    public Response toResponse(InvalidStateException exception) {
        UUID errorId = UUID.randomUUID();
        ErrorStatusDto entity = ErrorStatusDto.createAuditedErrorStatus(errorId, ExceptionType.INVALID_STATE, exception.getMessage());

        Response.ResponseBuilder rb = Response.status(Response.Status.BAD_REQUEST);
        rb.entity(entity);
        return rb.build();
    }

}
