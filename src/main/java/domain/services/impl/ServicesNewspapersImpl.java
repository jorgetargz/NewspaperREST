package domain.services.impl;

import dao.ArticlesDao;
import dao.NewspapersDao;
import dao.RatingsDao;
import dao.SubscriptionsDao;
import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesNewspapersImpl implements ServicesNewspapers {

    private final NewspapersDao daoNewspapers;
    private final RatingsDao daoRatings;
    private final SubscriptionsDao daoSubscriptions;
    private final ArticlesDao daoArticles;

    @Inject
    public ServicesNewspapersImpl(NewspapersDao daoNewspapers, RatingsDao daoRatings, SubscriptionsDao daoSubscriptions, ArticlesDao daoArticles) {
        this.daoNewspapers = daoNewspapers;
        this.daoRatings = daoRatings;
        this.daoSubscriptions = daoSubscriptions;
        this.daoArticles = daoArticles;
    }

    @Override
    public List<Newspaper> getNewspapers() {
        return daoNewspapers.getAll();
    }

    @Override
    public Newspaper get(int id) {
        return daoNewspapers.get(id);
    }

    @Override
    public boolean saveNewspaper(Newspaper newspaper) {
        return daoNewspapers.save(newspaper);
    }

    @Override
    public boolean updateNewspaper(Newspaper newspaper) {
        return daoNewspapers.update(newspaper);
    }

    @Override
    public boolean deleteNewspaper(Newspaper newspaper) {
        if (daoSubscriptions.deleteAll(newspaper)
                && daoRatings.deleteAllByNewspaperId(newspaper.getId())
                && daoArticles.deleteAll(newspaper)) {
            return daoNewspapers.delete(newspaper);
        } else {
            return false;
        }
    }

}
