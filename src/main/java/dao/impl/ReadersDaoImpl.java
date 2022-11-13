package dao.impl;

import dao.CredentialsDao;
import dao.DBConnection;
import dao.ReadersDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.utils.SQLQueries;
import domain.modelo.Reader;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ReadersDaoImpl implements ReadersDao {


    private final DBConnection dbConnection;
    private final CredentialsDao credentialsDao;

    @Inject
    public ReadersDaoImpl(DBConnection dbConnection, CredentialsDao credentialsDao) {
        this.dbConnection = dbConnection;
        this.credentialsDao = credentialsDao;
    }

    @Override
    public List<Reader> getAll() {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> list = getReadersFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.NO_READERS);
                throw new NotFoundException(Constantes.NO_READERS);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public List<Reader> getAllByNewspaperId(int newspaperId) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_NEWSPAPER_QUERY)) {
            preparedStatement.setInt(1, newspaperId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> list = getReadersFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.NO_READERS_OF_THIS_NEWSPAPER);
                throw new NotFoundException(Constantes.NO_READERS_OF_THIS_NEWSPAPER);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public List<Reader> getAllByArticleTypeId(int articleTypeId) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_ARTICLE_TYPE_QUERY)) {
            preparedStatement.setInt(1, articleTypeId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> list = getReadersFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.NO_READERS_OF_THIS_ARTICLE_TYPE);
                throw new NotFoundException(Constantes.NO_READERS_OF_THIS_ARTICLE_TYPE);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public Reader get(int id) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getReaderFromRow(rs);
            } else {
                log.info(Constantes.READER_NOT_FOUND);
                throw new NotFoundException(Constantes.READER_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean save(Reader reader) {
        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);
            return executeSavingReaderStatements(reader, con);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }

    }

    @Override
    public boolean update(Reader reader) {
        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);
            return executeUpdatingReaderStatements(reader, con);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean delete(Reader reader) {
        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);
            return executeDeletingReaderStatements(reader, con);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    private boolean executeSavingReaderStatements(Reader reader, Connection con) {
        try (PreparedStatement preparedStatementInsertReader = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementInsertCredentials = con.prepareStatement(SQLQueries.INSERT_LOGIN_QUERY)) {

            preparedStatementInsertReader.setString(1, reader.getName());
            preparedStatementInsertReader.setDate(2, Date.valueOf(reader.getDateOfBirth()));
            preparedStatementInsertReader.executeUpdate();
            ResultSet rs = preparedStatementInsertReader.getGeneratedKeys();
            if (rs.next()) {
                //Add the id to the reader object in order to display it in the view
                reader.setId(rs.getInt(1));
                reader.getLogin().setIdReader(rs.getInt(1));
            }

            preparedStatementInsertCredentials.setString(1, reader.getLogin().getUsername());
            preparedStatementInsertCredentials.setString(2, reader.getLogin().getPassword());
            preparedStatementInsertCredentials.setInt(3, reader.getId());
            preparedStatementInsertCredentials.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException ex) {
            transactionFailed(con, ex);
            return false;
        }
    }

    private boolean executeUpdatingReaderStatements(Reader reader, Connection con) {
        try (PreparedStatement preparedStatementUpdateReader = con.prepareStatement(SQLQueries.UPDATE_READER);
             PreparedStatement preparedStatementUpdateCredentials = con.prepareStatement(SQLQueries.UPDATE_LOGIN_QUERY)) {

            preparedStatementUpdateReader.setString(1, reader.getName());
            preparedStatementUpdateReader.setDate(2, java.sql.Date.valueOf(reader.getDateOfBirth()));
            preparedStatementUpdateReader.setInt(3, reader.getId());
            preparedStatementUpdateReader.executeUpdate();

            if (reader.getLogin() != null) {
                preparedStatementUpdateCredentials.setString(1, reader.getLogin().getUsername());
                preparedStatementUpdateCredentials.setString(2, reader.getLogin().getPassword());
                preparedStatementUpdateCredentials.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    private boolean executeDeletingReaderStatements(Reader reader, Connection con) {
        try (PreparedStatement preparedStatementDeleteReader = con.prepareStatement(SQLQueries.DELETE_READER);
             PreparedStatement preparedStatementDeleteReaderRatings = con.prepareStatement(SQLQueries.DELETE_RATINGS_BY_READER_ID_QUERY);
             PreparedStatement preparedStatementDeleteReaderSubscriptions = con.prepareStatement(SQLQueries.DELETE_SUBSCRIPTIONS_BY_READER_ID);
             PreparedStatement preparedStatementDeleteReaderCredentials = con.prepareStatement(SQLQueries.DELETE_LOGIN_BY_READER_ID)) {

            preparedStatementDeleteReaderRatings.setInt(1, reader.getId());
            preparedStatementDeleteReaderRatings.executeUpdate();

            preparedStatementDeleteReaderSubscriptions.setInt(1, reader.getId());
            preparedStatementDeleteReaderSubscriptions.executeUpdate();

            preparedStatementDeleteReaderCredentials.setInt(1, reader.getId());
            preparedStatementDeleteReaderCredentials.executeUpdate();

            preparedStatementDeleteReader.setInt(1, reader.getId());
            preparedStatementDeleteReader.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException ex) {
            transactionFailed(con, ex);
            return false;
        }
    }

    private static void transactionFailed(Connection con, SQLException ex) {
        try {
            con.rollback();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(ex.getMessage());
        }
        log.error(ex.getMessage(), ex);
        throw new DatabaseException(ex.getMessage());
    }

    private List<Reader> getReadersFromRS(ResultSet rs) throws SQLException {
        List<Reader> readers = new ArrayList<>();
        while (rs.next()) {
            Reader reader = getReaderFromRow(rs);
            if (reader.getId() > Constantes.MIN_ID_READER) {
                readers.add(reader);
            }
        }
        return readers;
    }

    private Reader getReaderFromRow(ResultSet rs) throws SQLException {
        Reader reader = new Reader();
        reader.setId(rs.getInt(Constantes.ID));
        reader.setName(rs.getString(Constantes.NAME_READER));
        reader.setDateOfBirth(rs.getDate(Constantes.BIRTH_READER).toLocalDate());
        reader.setLogin(credentialsDao.get(rs.getInt(Constantes.ID)));
        return reader;
    }
}