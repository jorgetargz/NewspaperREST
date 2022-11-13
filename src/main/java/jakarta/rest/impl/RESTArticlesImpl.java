package jakarta.rest.impl;

import domain.modelo.*;
import domain.services.ServicesArticles;
import jakarta.common.Constantes;
import jakarta.inject.Inject;
import jakarta.rest.RESTArticles;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(Constantes.PATH_ARTICLES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTArticlesImpl implements RESTArticles {

    private final ServicesArticles servicesArticles;

    @Inject
    public RESTArticlesImpl(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
    }

    @Override
    @GET
    public List<Article> getAllArticles() {
        return servicesArticles.getArticles();
    }

    @Override
    @GET
    @Path(Constantes.ID_PATH_PARAM)
    public ArticleQuery1 getArticleById(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticleInfo(id);
    }

    @Override
    @GET
    @Path(Constantes.READER_PATH + Constantes.ID_PATH_PARAM)
    public List<Article> getArticlesAvailableForReader(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticlesAvailableForReader(id);
    }

    @Override
    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    public List<Article> getArticlesByNewspaper(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticlesByNewspaper(id);
    }

    @Override
    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM + Constantes.BAD_RATINGS_PATH)
    public List<ArticleQuery3> getArticlesByNewspaperWithBadRatings(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticlesByNewspaperWithBadRatings(id);
    }

    @Override
    @GET
    @Path(Constantes.TYPE_PATH + Constantes.ID_PATH_PARAM)
    public List<Article> getArticlesByTypeIdWithoutNewspaperName(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticlesByType(id);
    }

    @Override
    @GET
    @Path(Constantes.TYPE_PATH + Constantes.ID_PATH_PARAM + Constantes.NEWSPAPER_PATH)
    public List<ArticleQuery2> getArticlesByTypeIdWithNewspaperName(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticlesByTypeWithNewspaper(id);
    }

    @Override
    @GET
    @Path(Constantes.ARTICLE_TYPES_PATH)
    public List<ArticleType> getArticlesTypes() {
        return servicesArticles.getArticleTypes();
    }

    @Override
    @GET
    @Path(Constantes.ARTICLE_TYPES_PATH + Constantes.ID_PATH_PARAM)
    public ArticleType getArticleTypeById(@PathParam(Constantes.ID) String id) {
        return servicesArticles.getArticleType(id);
    }

    @Override
    @POST
    public Response saveArticle(Article article) {
        if (servicesArticles.saveArticle(article)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @POST
    @Path(Constantes.ARTICLE_TYPES_PATH)
    public Response saveArticleType(ArticleType articleType) {
        if (servicesArticles.saveArticleType(articleType)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @PUT
    public Response updateArticle(Article article) {
        if (servicesArticles.updateArticle(article)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @PUT
    @Path(Constantes.ARTICLE_TYPES_PATH)
    public Response updateArticleType(ArticleType articleType) {
        if (servicesArticles.updateArticleType(articleType)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    public Response deleteArticle(@PathParam(Constantes.ID) String id) {
        if (servicesArticles.deleteArticle(servicesArticles.getArticle(id))) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @DELETE
    @Path(Constantes.ARTICLE_TYPES_PATH + Constantes.ID_PATH_PARAM)
    public Response deleteArticleType(@PathParam(Constantes.ID) String id) {
        if (servicesArticles.deleteArticleType(servicesArticles.getArticleType(id))) {
            return Response.status(Response.Status.RESET_CONTENT).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}