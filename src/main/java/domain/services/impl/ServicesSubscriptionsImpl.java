package domain.services.impl;

import dao.SubscriptionsDao;
import domain.common.Constantes;
import domain.modelo.Subscription;
import domain.modelo.excepciones.ValidationException;
import domain.services.ServicesSubscriptions;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

public class ServicesSubscriptionsImpl implements ServicesSubscriptions {

    private final SubscriptionsDao daoSubscriptions;

    @Inject
    public ServicesSubscriptionsImpl(SubscriptionsDao daoSubscriptions) {
        this.daoSubscriptions = daoSubscriptions;
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return daoSubscriptions.getAll();
    }


    @Override
    public List<Subscription> getSubscriptionsByNewspaper(String newspaperId) {
        try {
            return daoSubscriptions.getAllByNewspaper(Integer.parseInt(newspaperId));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Subscription> getOldestSubscriptionsByNewspaper(String newspaperId) {
        try {
            return daoSubscriptions.getOldestSubscriptionsByNewspaper(Integer.parseInt(newspaperId));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Subscription> getSubscriptionsByReader(String readerId) {
        try {
            return daoSubscriptions.getAllByReader(Integer.parseInt(readerId));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Subscription getSubscription(String idReader, String idNewspaper) {
        try {
            return daoSubscriptions.get(Integer.parseInt(idReader), Integer.parseInt(idNewspaper));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public boolean addSubscription(Subscription subscription) {
        return daoSubscriptions.save(subscription);
    }

    @Override
    public boolean cancelSubscription(Subscription subscription) {
        LocalDate today = LocalDate.now();
        subscription.setCancellationDate(today);
        return daoSubscriptions.update(subscription);
    }

    @Override
    public boolean deleteSubscription(Subscription subscription) {
        return daoSubscriptions.delete(subscription);
    }
}
