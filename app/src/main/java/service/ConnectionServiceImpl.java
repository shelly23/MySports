package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import persistence.daos.ConnectionDAO;
import persistence.dtos.Connection;

/**
 * Service for persistence-connection handling in case of a database.
 */
public class ConnectionServiceImpl implements ConnectionService {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private final ConnectionDAO FBpersistenceConnection;

    public ConnectionServiceImpl(ConnectionDAO FBpersistenceConnection) {
        this.FBpersistenceConnection = FBpersistenceConnection;
    }

    @Override
    public List<Connection> getUsersConnections(long userId, boolean pending) throws InterruptedException {
        LOG.debug("Read all connections");
        return FBpersistenceConnection.getForUser(userId, pending);
    }

    public void set(long id, boolean accept) throws InterruptedException {
        LOG.debug(accept ? "Accept" : "Reject" + " connection " + id);
        FBpersistenceConnection.set(id, accept);
    }

    public void delete(long id) throws InterruptedException {
        LOG.debug("Delete connection " + id);
        FBpersistenceConnection.delete(id);
    }
}
