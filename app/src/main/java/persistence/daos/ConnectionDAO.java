package persistence.daos;

import java.util.List;

import persistence.dtos.Connection;
import persistence.exceptions.PersistenceException;

public interface ConnectionDAO {

    List<Connection> read() throws PersistenceException, InterruptedException;

    Connection getConnection(long id) throws PersistenceException, InterruptedException;

    void update(Connection connection) throws PersistenceException;

    List<Connection> getForUser(long userId, boolean pending) throws InterruptedException;

    void set(long id, boolean accept) throws InterruptedException;

    void delete(long id) throws InterruptedException;
}
