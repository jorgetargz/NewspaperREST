package dao;

import domain.modelo.Newspaper;

import java.util.List;

public interface NewspapersDao {
    List<Newspaper> getAll();

    Newspaper get(int id);

    boolean save(Newspaper newspaper);

    boolean update(Newspaper newspaper);

    boolean delete(Newspaper newspaper);
}
