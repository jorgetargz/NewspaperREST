package dao.impl;

import dao.DBConnection;
import dao.NewspapersDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.impl.row_mapers.NewspaperRowMapper;
import dao.utils.SQLQueries;
import domain.modelo.Newspaper;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class NewspapersDaoImpl implements NewspapersDao {

    private final DBConnection dbConnection;

    @Inject
    public NewspapersDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Newspaper> getAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Newspaper> list = jdbcTemplate.query(SQLQueries.SELECT_NEWSPAPERS_QUERY, new NewspaperRowMapper());
            if (list.isEmpty()) {
                log.error(Constantes.NO_NEWSPAPERS_FOUND);
                throw new NotFoundException(Constantes.NO_NEWSPAPERS_FOUND);
            } else {
                return list;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public Newspaper get(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Newspaper> list = jdbcTemplate.query(SQLQueries.SELECT_NEWSPAPER_BY_ID_QUERY, new NewspaperRowMapper(), id);
            if (list.isEmpty()) {
                log.error(Constantes.NEWSPAPER_NOT_FOUND);
                throw new NotFoundException(Constantes.NEWSPAPER_NOT_FOUND);
            } else {
                return list.get(0);
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }

    }

    @Override
    public boolean save(Newspaper newspaper) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.INSERT_NEWSPAPER_QUERY, newspaper.getNameNewspaper(), newspaper.getReleaseDate()) == 0) {
                log.error(Constantes.NEWSPAPER_NOT_SAVED);
                throw new DatabaseException(Constantes.NEWSPAPER_NOT_SAVED);
            } else {
                return true;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean update(Newspaper newspaper) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.UPDATE_NEWSPAPER_QUERY, newspaper.getNameNewspaper(), newspaper.getReleaseDate(), newspaper.getId()) == 0) {
                log.error(Constantes.NEWSPAPER_NOT_UPDATED);
                throw new NotFoundException(Constantes.NEWSPAPER_NOT_UPDATED);
            } else {
                return true;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean delete(Newspaper newspaper) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.DELETE_NEWSPAPER_QUERY, newspaper.getId()) == 0) {
                log.error(Constantes.NEWSPAPER_NOT_DELETED);
                throw new NotFoundException(Constantes.NEWSPAPER_NOT_DELETED);
            } else {
                return true;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}
