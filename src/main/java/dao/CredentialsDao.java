package dao;

import domain.modelo.Login;

public interface CredentialsDao {

    Login get(String username, String password);

    Login get(int idReader);

    boolean save(Login login);

    boolean update(Login login);

    boolean delete(Login login);
}
