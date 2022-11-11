package dao;

import domain.modelo.ArticleRating;

import java.util.List;

public interface RatingsDao {
    List<ArticleRating> getAll();

    List<ArticleRating> getAllByReaderId(int readerId);

    ArticleRating get(int id);

    boolean save(ArticleRating articleRating);

    boolean update(ArticleRating articleRating);

    boolean delete(ArticleRating articleRating);

    boolean deleteAllByNewspaperId(int newspaperId);
}
