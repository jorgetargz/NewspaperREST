package jakarta.rest;

import domain.modelo.Reader;
import jakarta.common.Constantes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface RESTReaders {
    @GET
    List<Reader> getAllReaders();

    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    List<Reader> getReadersByNewspaper(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.ARTICLE_TYPE_PATH + Constantes.ID_PATH_PARAM)
    List<Reader> getReadersByArticleType(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.ID_PATH_PARAM)
    Reader getReader(@PathParam(Constantes.ID) String id);

    @POST
    Response saveReader(Reader reader);

    @PUT
    Response updateReader(Reader reader);

    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    Response deleteReader(@PathParam(Constantes.ID) String id);
}
