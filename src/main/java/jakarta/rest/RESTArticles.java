package jakarta.rest;

import domain.modelo.*;
import jakarta.common.Constantes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface RESTArticles {
    @GET
    List<Article> getAllArticles();

    @GET
    @Path(Constantes.ID_PATH_PARAM)
    ArticleQuery1 getArticleById(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.READER_PATH + Constantes.ID_PATH_PARAM)
    List<Article> getArticlesAvailableForReader(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM)
    List<Article> getArticlesByNewspaper(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.NEWSPAPER_PATH + Constantes.ID_PATH_PARAM + Constantes.BAD_RATINGS_PATH)
    List<ArticleQuery3> getArticlesByNewspaperWithBadRatings(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.TYPE_PATH + Constantes.ID_PATH_PARAM)
    List<Article> getArticlesByTypeIdWithoutNewspaperName(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.TYPE_PATH + Constantes.ID_PATH_PARAM + Constantes.NEWSPAPER_PATH)
    List<ArticleQuery2> getArticlesByTypeIdWithNewspaperName(@PathParam(Constantes.ID) String id);

    @GET
    @Path(Constantes.ARTICLE_TYPES_PATH)
    List<ArticleType> getArticlesTypes();

    @GET
    @Path(Constantes.ARTICLE_TYPES_PATH + Constantes.ID_PATH_PARAM)
    ArticleType getArticleTypeById(@PathParam(Constantes.ID) String id);

    @POST
    Response saveArticle(Article article);

    @POST
    @Path(Constantes.ARTICLE_TYPES_PATH)
    Response saveArticleType(ArticleType articleType);

    @PUT
    Response updateArticle(Article article);

    @PUT
    @Path(Constantes.ARTICLE_TYPES_PATH)
    Response updateArticleType(ArticleType articleType);

    @DELETE
    @Path(Constantes.ID_PATH_PARAM)
    Response deleteArticle(@PathParam(Constantes.ID) String id);

    @DELETE
    @Path(Constantes.ARTICLE_TYPES_PATH + Constantes.ID_PATH_PARAM)
    Response deleteArticleType(@PathParam(Constantes.ID) String id);
}
