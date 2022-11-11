package dao.impl;

import dao.ArticleTypesDao;
import dao.DBConnection;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.utils.SQLQueries;
import domain.modelo.ArticleType;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class ArticleTypesDaoImpl implements ArticleTypesDao {

    private final DBConnection dbConnection;

    @Inject
    public ArticleTypesDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ArticleType> getAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<ArticleType> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLE_TYPES_QUERY, new BeanPropertyRowMapper<>(ArticleType.class));
            if (list.isEmpty()) {
                log.error(Constantes.NO_ARTICLE_TYPES_FOUND);
                throw new DatabaseException(Constantes.NO_ARTICLE_TYPES_FOUND);
            } else {
                return list;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public ArticleType get(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<ArticleType> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLE_TYPE_BY_ID_QUERY, new BeanPropertyRowMapper<>(ArticleType.class), id);
            if (list.isEmpty()) {
                log.error(Constantes.ARTICLE_TYPE_NOT_FOUND);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_FOUND);
            } else {
                return list.get(0);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean save(ArticleType articleType) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.INSERT_ARTICLE_TYPE_QUERY, articleType.getDescription()) == 0) {
                log.error(Constantes.ARTICLE_TYPE_NOT_SAVED);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_SAVED);
            } else {
                return true;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean update(ArticleType articleType) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.UPDATE_ARTICLE_TYPE_QUERY, articleType.getDescription(), articleType.getId()) == 0) {
                log.error(Constantes.ARTICLE_TYPE_NOT_UPDATED);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_UPDATED);
            } else {
                return true;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean delete(ArticleType articleType) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.DELETE_ARTICLE_TYPE_QUERY, articleType.getId()) == 0) {
                log.error(Constantes.ARTICLE_TYPE_NOT_DELETED);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_DELETED);
            } else {
                return true;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
