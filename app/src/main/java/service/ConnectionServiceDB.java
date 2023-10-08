package service;

import android.content.Context;
import android.os.Build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.DBHelper;
import persistence.H2Handler;
import persistence.exceptions.PersistenceException;

/**
 * Service for persistence-connection handling in case of a database.
 */
public class ConnectionServiceDB implements ConnectionService {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionServiceDB.class);
    private DBHelper dbHelper;

    @Override
    public DBHelper getConnection(Context context) throws PersistenceException {
        return new DBHelper(context);
        /**try {
         Connection con = null;
         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
         con = H2Handler.getConnection();
         }
         LOG.debug("Database connection established");
         return con;
         } catch (ClassNotFoundException e) {
         LOG.error("Could not load H2 JDBC driver.");
         throw new PersistenceException(e.getMessage());
         } catch (SQLException e) {
         throw new PersistenceException(e.getMessage());
         }**/
    }

    @Override
    public void closeConnection() throws PersistenceException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            H2Handler.closeConnection();
        }
        LOG.debug("Database connection closed");
    }
}
