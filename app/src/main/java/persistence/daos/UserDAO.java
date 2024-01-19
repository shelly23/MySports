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

    long create(User user) throws PersistenceException, NoSuchAlgorithmException, InterruptedException;

    List<User> read() throws PersistenceException, InterruptedException;

    void delete(List<User> vehicles) throws PersistenceException;

    void update(User vehicle) throws PersistenceException;

    User getUser(String username) throws PersistenceException, InterruptedException;

    boolean canLogin(String username, String password) throws NoSuchAlgorithmException, InterruptedException;

    //List<User> search(UserSearchFilter userSearchFilter) throws PersistenceException;
}
