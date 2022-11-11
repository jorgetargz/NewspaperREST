package dao;

import domain.modelo.*;

import java.util.List;

public interface ArticlesDao {
    List<Article> getAll();

    List<Article> getAllByNewspaperId(int newspaperId);

    List<Article> getAllByArticleTypeId(int articleTypeId);

    List<Article> getAllByReaderId(int readerId);

    Article get(int id);

    boolean save(Article article);

    boolean update(Article article);

    boolean delete(Article article);

    boolean deleteAll(Newspaper newspaper);

    ArticleQuery1 getArticleQuery1(int id);

    List<ArticleQuery2> getArticlesQuery2(int articleTypeId);

    List<ArticleQuery3> getArticlesQuery3(int newspaperId);
}
