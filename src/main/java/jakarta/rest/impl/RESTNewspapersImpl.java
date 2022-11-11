package jakarta.rest.impl;

import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import jakarta.inject.Inject;
import jakarta.common.Constantes;
import jakarta.rest.RESTNewspapers;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(Constantes.PATH_NEWSPAPERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTNewspapersImpl implements RESTNewspapers {

    private final ServicesNewspapers servicesNewspapers;

    @Inject
    public RESTNewspapersImpl(ServicesNewspapers servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
    }


    @Override
    @GET
    public List<Newspaper> getAllNewspapers() {
        return servicesNewspapers.getNewspapers();
    }

    @Override
    @GET
    @Path(Constantes.ID_PATH_PARAM)
    public Newspaper getNewspaper(@PathParam(Constantes.ID) String id) {
        return servicesNewspapers.get(Integer.parseInt(id));
    }

    @Override
    @POST
    public Response saveNewspaper(Newspaper newspaper) {
        if (servicesNewspapers.saveNewspaper(newspaper)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @PUT
    public Response updateNewspaper(Newspaper newspaper) {
        if (servicesNewspapers.updateNewspaper(newspaper)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    public Response deleteNewspaper(@PathParam(Constantes.ID) String id) {
        if (servicesNewspapers.deleteNewspaper(servicesNewspapers.get(Integer.parseInt(id)))) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}