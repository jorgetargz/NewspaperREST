package jakarta.rest.impl;

import domain.modelo.Reader;
import domain.services.ServicesReaders;
import jakarta.inject.Inject;
import jakarta.common.Constantes;
import jakarta.rest.RESTReaders;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(Constantes.PATH_READERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTReadersImpl implements RESTReaders {

    private final ServicesReaders servicesReaders;

    @Inject
    public RESTReadersImpl(ServicesReaders servicesReaders) {
        this.servicesReaders = servicesReaders;
    }

    @Override
    @GET
    public List<Reader> getAllReaders() {
        return servicesReaders.getReaders();
    }

    @Override
    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    public List<Reader> getReadersByNewspaper(@PathParam(Constantes.ID) String id) {
        return servicesReaders.getReadersByNewspaper(id);
    }

    @Override
    @GET
    @Path(Constantes.ARTICLE_TYPE_PATH + Constantes.ID_PATH_PARAM)
    public List<Reader> getReadersByArticleType(@PathParam(Constantes.ID) String id) {
        return servicesReaders.getReadersByArticleType(id);
    }

    @Override
    @GET
    @Path(Constantes.ID_PATH_PARAM)
    public Reader getReader(@PathParam(Constantes.ID) String id) {
        return servicesReaders.getReader(id);
    }

    @Override
    @POST
    public Response saveReader(Reader reader) {
        if (servicesReaders.saveReader(reader)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @PUT
    public Response updateReader(Reader reader) {
        if (servicesReaders.updateReader(reader)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    public Response deleteReader(@PathParam(Constantes.ID) String id) {
        if (servicesReaders.deleteReader(servicesReaders.getReader(id))) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
