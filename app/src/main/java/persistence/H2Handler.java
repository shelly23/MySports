package persistence;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import persistence.exceptions.PersistenceException;

/**
 * Singleton to handle the connection to the database;
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class H2Handler {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection con = null;

    /**
     * Method to get the a reference connection to the database. If no connection is established yet, a new connection
     * gets established. When establishing a new connection, afterwards the creation file 'database.sql' is executed to
     * initiate the database.
     *
     * @return a reference to the database connection
     * @throws SQLException if an database access error occurs
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (con == null) {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:file:D:/Desktop/MySports/mysports;INIT=RUNSCRIPT FROM 'classpath:sql/database.sql'", "sa", "");
            LOG.info("Database connection established");
        }
        return con;
    }

    /**
     * Method to close the connection to the database if it is not null.
     *
     * @throws PersistenceException if an database access error occurs
     */
    public static void closeConnection() throws PersistenceException {
        if (con != null) {
            try {
                LOG.info("Database connection closed");
                con.close();
            } catch (SQLException e) {
                LOG.error("Failed to close database connection, cause: {}", e.getMessage());
                throw new PersistenceException(e.getMessage());
            }
        }
    }
}
