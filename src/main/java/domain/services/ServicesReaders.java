package domain.services;

import domain.modelo.Reader;

import java.util.List;

public interface ServicesReaders {
    List<Reader> getReaders();

    List<Reader> getReadersByNewspaper(String newspaperId);

    List<Reader> getReadersByArticleType(String articleTypeId);

    Reader getReader(String id);

    boolean saveReader(Reader reader);

    boolean updateReader(Reader reader);

    boolean deleteReader(Reader reader);

}
