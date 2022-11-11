package jakarta.rest;

import domain.modelo.ArticleRating;
import jakarta.common.Constantes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface RESTRatings {
    @GET
    List<ArticleRating> getAllRatings();

    @GET
    @Path(Constantes.READER_PATH + Constantes.ID_PATH_PARAM)
    List<ArticleRating> getRatingsByReaderId(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.ID_PATH_PARAM)
    ArticleRating getRating(@PathParam(Constantes.ID) String id);

    @POST
    Response saveRating(ArticleRating articleRating);

    @PUT
    Response updateRating(ArticleRating articleRating);

    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    Response deleteRating(@PathParam(Constantes.ID) String id);

    @DELETE
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    Response deleteAllRatingsByNewspaper(@PathParam(Constantes.ID) String id);
}
