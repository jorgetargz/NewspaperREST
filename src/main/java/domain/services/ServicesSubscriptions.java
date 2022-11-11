package domain.services;

import domain.modelo.Subscription;

import java.util.List;

public interface ServicesSubscriptions {
    List<Subscription> getSubscriptions();

    List<Subscription> getSubscriptionsByNewspaper(String newspaperId);

    List<Subscription> getOldestSubscriptionsByNewspaper(String newspaperId);

    List<Subscription> getSubscriptionsByReader(String readerId);

    Subscription getSubscription(String idReader, String idNewspaper);

    boolean addSubscription(Subscription subscription);

    boolean cancelSubscription(Subscription subscription);

    boolean deleteSubscription(Subscription subscription);
}
