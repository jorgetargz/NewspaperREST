package jakarta.rest;

import domain.modelo.Subscription;
import jakarta.common.Constantes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface RESTSubscriptions {
    @GET
    List<Subscription> getAllSubscriptions();

    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    List<Subscription> getSubscriptionsByNewspaper(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM + Constantes.OLDEST_PATH)
    List<Subscription> getOldestSubscriptionsByNewspaper(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.READER_PATH + Constantes.ID_PATH_PARAM)
    List<Subscription> getSubscriptionsByReader(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.ID_PATH)
    Subscription getSubscription(@QueryParam(Constantes.ID_READER) String idReader, @QueryParam(Constantes.ID_NEWSPAPER) String idNewspaper);

    @POST
    Response saveSubscription(Subscription subscription);

    @PUT
    Response cancelSubscription(Subscription subscription);

    @DELETE
    @Path(Constantes.ID_PATH)
    Response deleteSubscription(@QueryParam(Constantes.ID_READER) String idReader, @QueryParam(Constantes.ID_NEWSPAPER) String idNewspaper);
}
