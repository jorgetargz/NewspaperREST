package domain.services;

import domain.modelo.*;

import java.util.List;

public interface ServicesArticles {
    List<Article> getArticles();

    List<Article> getArticlesByType(String typeId);

    List<ArticleQuery2> getArticlesByTypeWithNewspaper(String articleTypeId);

    List<Article> getArticlesByNewspaper(String idNewspaper);

    List<ArticleQuery3> getArticlesByNewspaperWithBadRatings(String newspaperId);

    List<Article> getArticlesAvailableForReader(String idReader);

    List<ArticleType> getArticleTypes();

    Article getArticle(String id);

    ArticleQuery1 getArticleInfo(String id);

    ArticleType getArticleType(String id);

    boolean saveArticle(Article article);

    boolean saveArticleType(ArticleType articleType);

    boolean updateArticle(Article article);

    boolean updateArticleType(ArticleType articleType);

    boolean deleteArticle(Article article);

    boolean deleteArticleType(ArticleType articleType);
}