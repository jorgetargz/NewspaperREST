package domain.services;

import domain.modelo.ArticleRating;

import java.util.List;

public interface ServicesRatings {
    List<ArticleRating> getRatings();

    List<ArticleRating> getRatingsByReaderId(String readerId);

    ArticleRating getRating(String id);

    boolean updateRating(ArticleRating articleRating);

    boolean saveRating(ArticleRating articleRating);

    boolean deleteRating(ArticleRating articleRating);

    boolean deleteAllRatingsByNewspaper(String newspaperId);
}
