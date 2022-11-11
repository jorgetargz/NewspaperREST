package dao;

import domain.modelo.ArticleType;

import java.util.List;

public interface ArticleTypesDao {
    List<ArticleType> getAll();

    ArticleType get(int id);

    boolean save(ArticleType articleType);

    boolean update(ArticleType articleType);

    boolean delete(ArticleType articleType);
}
