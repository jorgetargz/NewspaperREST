package jakarta.exception_mappers;

import domain.modelo.excepciones.ValidationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        BaseError baseError = new BaseError(e.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.BAD_REQUEST).entity(baseError).
                type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}

