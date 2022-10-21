package persistence.daos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.BaseColumns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import persistence.DBHelper;
import persistence.dtos.User;
import persistence.exceptions.PersistenceException;
import service.ConnectionService;

public class DBUserDAO implements UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(DBUserDAO.class);
    private static long id = 0;
    final String USER_COLUMN_PRENAME = "prename";
    final String USER_COLUMN_SURNAME = "surname";
    final String USER_COLUMN_USERNAME = "username";
    final String USER_COLUMN_PASSWORD = "password";
    final String USER_TABLE = "users";
    private final DBHelper con;
    private SQLiteDatabase database;

    public DBUserDAO(ConnectionService connectionService, Context context) throws PersistenceException {
        con = connectionService.getConnection(context);
        database = con.getWritableDatabase();
    }

    private ContentValues buildInsert(String prename, String surname, String username, String password, long id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseColumns._ID, id);
        contentValues.put(USER_COLUMN_PRENAME, prename);
        contentValues.put(USER_COLUMN_SURNAME, surname);
        contentValues.put(USER_COLUMN_USERNAME, username);
        contentValues.put(USER_COLUMN_PASSWORD, password);
        return contentValues;
    }

    @SuppressLint("Range")
    @Override
    public void create(User user) throws NoSuchAlgorithmException {

        final String MY_QUERY = "SELECT MAX(_id) AS _id FROM "+ USER_TABLE;
        Cursor mCursor = database.rawQuery(MY_QUERY, null);

        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            if (mCursor.getColumnIndex(MY_QUERY) >= 0) {
                id = mCursor.getInt(mCursor.getColumnIndex(MY_QUERY));
            }
        }

        id++;

        ContentValues values = buildInsert(user.getPrename(), user.getSurname(), user.getUsername(), hashPassword(user.getPassword()), id);
        database.insert(USER_TABLE, null, values);

        LOG.debug("Created DB-Entry {}", user.getUsername());
        user.setId(id);
    }

    @Override
    public List<User> read() throws PersistenceException {
        String[] projection = {
                BaseColumns._ID,
                USER_COLUMN_PRENAME,
                USER_COLUMN_SURNAME,
                USER_COLUMN_USERNAME,
                USER_COLUMN_PASSWORD,
        };

        Cursor cursor = database.query(
                USER_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return createUsers(cursor);
    }

    @Override
    public void update(User user) throws PersistenceException {
        /*try {
            this.update.setString(1, user.getPrename());
            this.update.setString(2, user.getSurname());
            this.update.setString(3, user.getUsername());
            this.update.setString(4, user.getPassword());
            this.update.setLong(5, user.getId());
            this.update.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Failed to update user {}, cause: {}", user.getUsername(), e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
        LOG.debug("Updated entry {}", user.getUsername());*/
    }

    @Override
    public void delete(List<User> users) throws PersistenceException {
        /*try {
            for (User u : users) {
                delete.setLong(1, u.getId());
                delete.execute();
                LOG.debug("Deleted user {}", u.getUsername());
            }
        } catch (SQLException e) {
            LOG.error("Failed to delete users, cause: {}", e.getMessage());
            throw new PersistenceException(e.getMessage());
        }*/
    }

    @Override
    public User getUser(String username) throws PersistenceException {
        String[] projection = {
                BaseColumns._ID,
                USER_COLUMN_PRENAME,
                USER_COLUMN_SURNAME,
                USER_COLUMN_USERNAME,
                USER_COLUMN_PASSWORD,
        };

        String selection = USER_COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = database.query(
                USER_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            return new User (cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_PRENAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_SURNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_PASSWORD)));

        }
        return null;
    }

    public boolean canLogin(String username, String password) throws NoSuchAlgorithmException {
        String[] projection = {
                BaseColumns._ID,
                USER_COLUMN_PRENAME,
                USER_COLUMN_SURNAME,
                USER_COLUMN_USERNAME,
                USER_COLUMN_PASSWORD,
        };

        String selection = USER_COLUMN_USERNAME + " = ? AND " + USER_COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, hashPassword(password)};

        Cursor cursor = database.query(
                USER_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            return (cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_USERNAME)).equals(username) &&
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_PASSWORD)).equals(password));
        }
        return false;
    }

    private List<User> createUsers(Cursor cursor) throws PersistenceException {
        List<User> result = new ArrayList<User>();

        while (cursor.moveToNext()) {
            User u = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_PRENAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_SURNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_COLUMN_PASSWORD)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
            result.add(u);
        }
        return result;
    }

    /**@Override public List<Vehicle> search(VehicleSearchFilter vehicleSearchFilter) throws PersistenceException {
    List<Vehicle> result;
    boolean plusPeriod = false;
    try {
    PreparedStatement search;
    if (vehicleSearchFilter.getEnddate() != null && vehicleSearchFilter.getStartdate() != null) {
    search = con.prepareStatement(searchVehicles + searchVehiclesPeriod);
    plusPeriod = true;
    } else {
    search = con.prepareStatement(searchVehicles + ";");
    }


    search.setString(1, vehicleSearchFilter.getTitle() == null ? "%" : "%" + vehicleSearchFilter.getTitle() + "%");
    search.setString(2, vehicleSearchFilter.getSeats() == 0 ? "%" : String.valueOf(vehicleSearchFilter.getSeats()));
    search.setString(3, vehicleSearchFilter.getDrive() == null ? "%" : vehicleSearchFilter.getDrive().toString());
    search.setLong(4, vehicleSearchFilter.getMaxprice() == 0 ? getMinMaxPrice()[0] : vehicleSearchFilter.getMaxprice());
    search.setLong(5, vehicleSearchFilter.getMinprice());
    search.setString(6, vehicleSearchFilter.getLicenses().equals("") ? "%" : "%" + vehicleSearchFilter.getLicenses() + "%");

    if (plusPeriod) {
    Timestamp start = Timestamp.valueOf(vehicleSearchFilter.getStartdate());
    Timestamp end = Timestamp.valueOf(vehicleSearchFilter.getEnddate());
    search.setTimestamp(7, start);
    search.setTimestamp(8, end);
    search.setTimestamp(9, start);
    search.setTimestamp(10, end);
    search.setTimestamp(11, start);
    search.setTimestamp(12, end);
    search.setTimestamp(13, start);
    search.setTimestamp(14, end);
    }

    result = createVehicle(search.executeQuery());

    } catch (SQLException e) {
    throw new PersistenceException(e.getMessage());
    }

    LOG.debug("Finished search, found {} entries", result.size());
    return result;
    }**/

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for(byte b : mdArray) {
            int v = b & 0xff;
            if(v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }

        return sb.toString();

    }


}
