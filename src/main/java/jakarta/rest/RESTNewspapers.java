package jakarta.rest;

import domain.modelo.Newspaper;
import jakarta.common.Constantes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface RESTNewspapers {
    @GET
    List<Newspaper> getAllNewspapers();

    @GET
    @Path(Constantes.ID_PATH_PARAM)
    Newspaper getNewspaper(@PathParam(Constantes.ID) String id);

    @POST
    Response saveNewspaper(Newspaper newspaper);

    @PUT
    Response updateNewspaper(Newspaper newspaper);

    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    Response deleteNewspaper(@PathParam(Constantes.ID) String id);
}
