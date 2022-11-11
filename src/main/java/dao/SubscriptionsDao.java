package dao;

import domain.modelo.Newspaper;
import domain.modelo.Subscription;

import java.util.List;

public interface SubscriptionsDao {
    List<Subscription> getAll();

    List<Subscription> getAllByNewspaper(int newspaperId);

    List<Subscription> getOldestSubscriptionsByNewspaper(int newspaperId);

    List<Subscription> getAllByReader(int readerId);

    Subscription get(int newspaperId, int readerId);

    boolean save(Subscription subscription);

    boolean update(Subscription subscription);

    boolean delete(Subscription subscription);

    boolean deleteAll(Newspaper newspaper);
}
