package persistence.daos;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import persistence.dtos.User;
import persistence.exceptions.PersistenceException;

/**
 * Persistence layer for vehicles.
 * Owns the typical CRUD-methods.
 */
public interface UserDAO {

    void create(User user) throws PersistenceException, NoSuchAlgorithmException;

    List<User> read() throws PersistenceException;

    void delete(List<User> vehicles) throws PersistenceException;

    void update(User vehicle) throws PersistenceException;

    User getUser(String username) throws PersistenceException;

    boolean canLogin(String username, String password) throws NoSuchAlgorithmException;

    //List<User> search(UserSearchFilter userSearchFilter) throws PersistenceException;
}
