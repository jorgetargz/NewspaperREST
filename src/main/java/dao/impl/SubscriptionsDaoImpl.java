package dao.impl;

import dao.DBConnection;
import dao.SubscriptionsDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.utils.SQLQueries;
import domain.modelo.Newspaper;
import domain.modelo.Subscription;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SubscriptionsDaoImpl implements SubscriptionsDao {

    private final DBConnection dbConnection;

    @Inject
    public SubscriptionsDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Subscription> getAll() {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SUBSCRIPTIONS_QUERY)) {

            ResultSet rs = preparedStatement.executeQuery();
            List<Subscription> list = getSubscriptionsFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.NO_SUBSCRIPTIONS);
                throw new NotFoundException(Constantes.NO_SUBSCRIPTIONS);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public List<Subscription> getAllByNewspaper(int newspaperId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SUBSCRIPTIONS_BY_NEWSPAPER_QUERY)) {

            return getSubscriptions(newspaperId, preparedStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public List<Subscription> getAllByReader(int readerId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SUBSCRIPTIONS_BY_READER_QUERY)) {

            preparedStatement.setInt(1, readerId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Subscription> list = getSubscriptionsFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.NO_SUBSCRIPTIONS_OF_THIS_READER);
                throw new NotFoundException(Constantes.NO_SUBSCRIPTIONS_OF_THIS_READER);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public Subscription get(int newspaperId, int readerId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SUBSCRIPTION_BY_NEWSPAPER_AND_READER_QUERY)) {

            preparedStatement.setInt(1, newspaperId);
            preparedStatement.setInt(2, readerId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setReaderId(rs.getInt("id_reader"));
                subscription.setNewspaperId(rs.getInt("id_newspaper"));
                subscription.setSigningDate(rs.getDate(Constantes.SIGNING_DATE).toLocalDate());
                Date cancellationDate = rs.getDate(Constantes.CANCELLATION_DATE);
                if (cancellationDate != null) {
                    subscription.setCancellationDate(cancellationDate.toLocalDate());
                }
                return subscription;
            } else {
                log.info(Constantes.SUBSCRIPTION_NOT_FOUND);
                throw new NotFoundException(Constantes.SUBSCRIPTION_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }


    @Override
    public boolean save(Subscription subscription) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_SUBSCRIPTION)) {

            preparedStatement.setInt(1, subscription.getReaderId());
            preparedStatement.setInt(2, subscription.getNewspaperId());
            preparedStatement.setDate(3, Date.valueOf(subscription.getSigningDate()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean update(Subscription subscription) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_SUBSCRIPTION)) {

            preparedStatement.setDate(1, Date.valueOf(subscription.getCancellationDate()));
            preparedStatement.setInt(2, subscription.getNewspaperId());
            preparedStatement.setInt(3, subscription.getReaderId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean delete(Subscription subscription) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_SUBSCRIPTION)) {

            preparedStatement.setInt(1, subscription.getReaderId());
            preparedStatement.setInt(2, subscription.getNewspaperId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean deleteAll(Newspaper newspaper) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_SUBSCRIPTIONS_BY_NEWSPAPER)) {

            preparedStatement.setInt(1, newspaper.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public List<Subscription> getOldestSubscriptionsByNewspaper(int newspaperId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_OLDEST_SUBSCRIPTIONS_BY_NEWSPAPER_QUERY)) {
            return getSubscriptions(newspaperId, preparedStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    private List<Subscription> getSubscriptions(int newspaperId, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, newspaperId);
        ResultSet rs = preparedStatement.executeQuery();
        List<Subscription> list = getSubscriptionsFromRS(rs);
        if (list.isEmpty()) {
            log.info(Constantes.NO_SUBSCRIPTIONS_OF_THIS_NEWSPAPER);
            throw new NotFoundException(Constantes.NO_SUBSCRIPTIONS_OF_THIS_NEWSPAPER);
        } else {
            return list;
        }
    }

    private List<Subscription> getSubscriptionsFromRS(ResultSet rs) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        while (rs.next()) {
            Subscription subscription = new Subscription();
            subscription.setReaderId(rs.getInt(Constantes.ID_READER));
            subscription.setNewspaperId(rs.getInt(Constantes.ID_NEWSPAPER));
            subscription.setSigningDate(rs.getDate(Constantes.SIGNING_DATE).toLocalDate());
            Date cancellationDate = rs.getDate(Constantes.CANCELLATION_DATE);
            if (cancellationDate != null) {
                subscription.setCancellationDate(cancellationDate.toLocalDate());
            }
            subscriptions.add(subscription);
        }
        return subscriptions;
    }


}