package dao;

import domain.modelo.Reader;

import java.util.List;

public interface ReadersDao {
    List<Reader> getAll();

    List<Reader> getAllByNewspaperId(int newspaperId);

    List<Reader> getAllByArticleTypeId(int articleTypeId);

    Reader get(int id);

    boolean save(Reader reader);

    boolean update(Reader reader);

    boolean delete(Reader reader);
}
