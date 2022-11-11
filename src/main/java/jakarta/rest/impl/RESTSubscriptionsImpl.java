package jakarta.rest.impl;


import domain.modelo.Subscription;
import domain.services.ServicesSubscriptions;
import jakarta.inject.Inject;
import jakarta.common.Constantes;
import jakarta.rest.RESTSubscriptions;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(Constantes.PATH_SUBSCRIPTIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTSubscriptionsImpl implements RESTSubscriptions {

    private final ServicesSubscriptions servicesSubscriptions;

    @Inject
    public RESTSubscriptionsImpl(ServicesSubscriptions servicesSubscriptions) {
        this.servicesSubscriptions = servicesSubscriptions;
    }

    @Override
    @GET
    public List<Subscription> getAllSubscriptions() {
        return servicesSubscriptions.getSubscriptions();
    }

    @Override
    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    public List<Subscription> getSubscriptionsByNewspaper(@PathParam(Constantes.ID) String id) {
        return servicesSubscriptions.getSubscriptionsByNewspaper(id);
    }

    @Override
    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM + Constantes.OLDEST_PATH)
    public List<Subscription> getOldestSubscriptionsByNewspaper(@PathParam(Constantes.ID) String id) {
        return servicesSubscriptions.getOldestSubscriptionsByNewspaper(id);
    }

    @Override
    @GET
    @Path(Constantes.READER_PATH + Constantes.ID_PATH_PARAM)
    public List<Subscription> getSubscriptionsByReader(@PathParam(Constantes.ID) String id) {
        return servicesSubscriptions.getSubscriptionsByReader(id);
    }

    @Override
    @GET
    @Path(Constantes.ID_PATH)
    public Subscription getSubscription(@QueryParam(Constantes.ID_READER) String idReader, @QueryParam(Constantes.ID_NEWSPAPER) String idNewspaper) {
        return servicesSubscriptions.getSubscription(idReader, idNewspaper);
    }

    @Override
    @POST
    public Response saveSubscription(Subscription subscription) {
        if (servicesSubscriptions.addSubscription(subscription)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @PUT
    public Response cancelSubscription(Subscription subscription) {
        if (servicesSubscriptions.cancelSubscription(subscription)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.ID_PATH)
    public Response deleteSubscription(@QueryParam(Constantes.ID_READER) String idReader, @QueryParam(Constantes.ID_NEWSPAPER) String idNewspaper) {
        if (servicesSubscriptions.deleteSubscription(servicesSubscriptions.getSubscription(idReader, idNewspaper))) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
