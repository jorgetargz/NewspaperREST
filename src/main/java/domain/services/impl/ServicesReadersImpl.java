package domain.services.impl;


import dao.ReadersDao;
import domain.common.Constantes;
import domain.modelo.Reader;
import domain.modelo.excepciones.ValidationException;
import domain.services.ServicesReaders;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesReadersImpl implements ServicesReaders {

    private final ReadersDao daoReaders;

    @Inject
    public ServicesReadersImpl(ReadersDao daoReaders) {
        this.daoReaders = daoReaders;
    }

    @Override
    public List<Reader> getReaders() {
        return daoReaders.getAll();
    }

    @Override
    public boolean saveReader(Reader reader) {
        return daoReaders.save(reader);
    }

    @Override
    public boolean updateReader(Reader reader) {
        return daoReaders.update(reader);
    }

    @Override
    public boolean deleteReader(Reader reader) {
        return daoReaders.delete(reader);
    }

    @Override
    public List<Reader> getReadersByNewspaper(String newspaper) {
        try {
            return daoReaders.getAllByNewspaperId(Integer.parseInt(newspaper));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Reader> getReadersByArticleType(String articleType) {
        try {
            return daoReaders.getAllByArticleTypeId(Integer.parseInt(articleType));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Reader getReader(String id) {
        try {
            return daoReaders.get(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }
}