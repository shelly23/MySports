package service;

import java.util.List;

import persistence.dtos.Connection;

/**
 * Service layer for connection-handling.
 */
public interface ConnectionService {


    List<Connection> getUsersConnections(long userId, boolean pending) throws InterruptedException;

    void set(long id, boolean accept) throws InterruptedException;

    void delete(long id) throws InterruptedException;

}
