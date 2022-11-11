package jakarta.rest.impl;

import domain.modelo.ArticleRating;
import domain.services.ServicesRatings;
import jakarta.inject.Inject;
import jakarta.common.Constantes;
import jakarta.rest.RESTRatings;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(Constantes.PATH_RATINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTRatingsImpl implements RESTRatings {

    private final ServicesRatings servicesRatings;

    @Inject
    public RESTRatingsImpl(ServicesRatings servicesRatings) {
        this.servicesRatings = servicesRatings;
    }

    @Override
    @GET
    public List<ArticleRating> getAllRatings() {
        return servicesRatings.getRatings();
    }

    @Override
    @GET
    @Path(Constantes.READER_PATH + Constantes.ID_PATH_PARAM)
    public List<ArticleRating> getRatingsByReaderId(@PathParam(Constantes.ID) String id) {
        return servicesRatings.getRatingsByReaderId(id);
    }

    @Override
    @GET
    @Path(Constantes.ID_PATH_PARAM)
    public ArticleRating getRating(@PathParam(Constantes.ID) String id) {
        return servicesRatings.getRating(id);
    }

    @Override
    @POST
    public Response saveRating(ArticleRating articleRating) {
        if (servicesRatings.saveRating(articleRating)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @PUT
    public Response updateRating(ArticleRating articleRating) {
        if (servicesRatings.updateRating(articleRating)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    public Response deleteRating(@PathParam(Constantes.ID) String id) {
        if (servicesRatings.deleteRating(servicesRatings.getRating(id))) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    public Response deleteAllRatingsByNewspaper(@PathParam(Constantes.ID) String id) {
        if (servicesRatings.deleteAllRatingsByNewspaper(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
