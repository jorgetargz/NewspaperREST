package domain.services;

import domain.modelo.Newspaper;

import java.util.List;

public interface ServicesNewspapers {

    List<Newspaper> getNewspapers();

    Newspaper get(int id);

    boolean saveNewspaper(Newspaper newspaper);

    boolean updateNewspaper(Newspaper newspaper);

    boolean deleteNewspaper(Newspaper newspaper);

}
