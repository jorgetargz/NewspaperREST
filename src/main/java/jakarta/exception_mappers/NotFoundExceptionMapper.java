package jakarta.exception_mappers;

import dao.excepciones.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException e) {
        BaseError baseError = new BaseError(e.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.NOT_FOUND).entity(baseError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
