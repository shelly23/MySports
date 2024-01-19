package service;

import android.content.Context;

import java.util.List;

import persistence.DBHelper;
import persistence.dtos.Connection;
import persistence.dtos.User;
import persistence.exceptions.PersistenceException;

/**
 * Service layer for connection-handling.
 */
public interface ConnectionService {


    List<Connection> getUsersConnections(long userId, boolean pending) throws InterruptedException;

    void set(long id, boolean accept) throws InterruptedException;

    void delete(long id) throws InterruptedException;

}
