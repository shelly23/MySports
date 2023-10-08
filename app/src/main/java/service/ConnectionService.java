package service;

import android.content.Context;

import persistence.DBHelper;
import persistence.exceptions.PersistenceException;

/**
 * Service layer for connection-handling.
 */
public interface ConnectionService {

    DBHelper getConnection(Context context) throws PersistenceException;

    /**
     * Method to close the connection to persistence.
     *
     * @throws PersistenceException if it is not possible to close the connection
     */
    void closeConnection() throws PersistenceException;
}
