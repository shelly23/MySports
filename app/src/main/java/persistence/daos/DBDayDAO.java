package persistence.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

import persistence.DBHelper;
import persistence.dtos.Day;
import persistence.dtos.User;
import persistence.exceptions.PersistenceException;
import service.ConnectionService;

public class DBDayDAO implements DayDAO {

    private static final Logger LOG = LoggerFactory.getLogger(DBDayDAO.class);
    private static long id = 0;
    private static final long userId = 0;
    final String DAY_COLUMN_DATE = "actual_date";
    final String DAY_COLUMN_STEPS = "steps";
    final String DAY_COLUMN_ACTIVE = "active";
    final String DAY_COLUMN_ATTACK = "attack";
    final String DAY_COLUMN_USERID = "user_id";
    final String DAY_TABLE = "days";
    private final SQLiteDatabase database;

    public DBDayDAO(ConnectionService connectionService, Context context) throws PersistenceException {
        DBHelper con = connectionService.getConnection(context);
        database = con.getWritableDatabase();
    }

    private ContentValues buildInsert(Date date, long steps, boolean active, boolean attack, long id, long userId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseColumns._ID, id);
        contentValues.put(DAY_COLUMN_DATE, String.valueOf(date));
        contentValues.put(DAY_COLUMN_STEPS, steps);
        contentValues.put(DAY_COLUMN_ACTIVE, active);
        contentValues.put(DAY_COLUMN_ATTACK, attack);
        contentValues.put(DAY_COLUMN_USERID, userId);
        return contentValues;
    }

    @Override
    public Day create(Day day) throws PersistenceException {
        id = DBUtils.getNewId(DAY_TABLE, database);

        ContentValues values = buildInsert(day.getCurrent_date(), day.getSteps(), day.isActive(), day.isAttack(), id, day.getUser_id());

        database.insert(DAY_TABLE, null, values);

        LOG.debug("Created DB-Entry {}", day.getCurrent_date());
        day.setId(id);
        return day;
    }

    @Override
    public List<Day> read() throws PersistenceException {
        return null;
    }

    @Override
    public void delete(List<Day> days) throws PersistenceException {

    }

    @Override
    public void update(Day day) throws PersistenceException {

        ContentValues contentValues = buildInsert(day.getCurrent_date(), day.getSteps(), day.isActive(), day.isAttack(), day.getId(), day.getUser_id());

        String selection = DAY_COLUMN_DATE + " = ? AND " + DAY_COLUMN_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(day.getCurrent_date()), String.valueOf(day.getUser_id())};

        database.update(DAY_TABLE, contentValues, selection, selectionArgs);
    }

    @Override
    public Day getDay(long user_id, Date date) throws PersistenceException {
        String[] projection = {
                BaseColumns._ID,
                DAY_COLUMN_DATE,
                DAY_COLUMN_STEPS,
                DAY_COLUMN_ACTIVE,
                DAY_COLUMN_ATTACK,
                DAY_COLUMN_USERID
        };

        String selection = DAY_COLUMN_DATE + " = ? AND " + DAY_COLUMN_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(date), String.valueOf(user_id)};

        Cursor cursor = database.query(
                DAY_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            return new Day(cursor.getInt(cursor.getColumnIndexOrThrow(DAY_COLUMN_STEPS)),
                    Date.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DAY_COLUMN_DATE))),
                    parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(DAY_COLUMN_ACTIVE))),
                    parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(DAY_COLUMN_ATTACK))),
                    cursor.getLong(cursor.getColumnIndexOrThrow(DAY_COLUMN_USERID)));

        }
        cursor.close();
        return null;
    }

    private boolean parseBoolean(String toBeParsed) {
        return toBeParsed.equals("1");
    }

    @Override
    public boolean exists(long user_id, Date date) throws PersistenceException {
        String[] projection = {
                BaseColumns._ID,
                DAY_COLUMN_DATE,
                DAY_COLUMN_STEPS,
                DAY_COLUMN_ACTIVE,
                DAY_COLUMN_ATTACK,
                DAY_COLUMN_USERID
        };

        String selection = DAY_COLUMN_DATE + " = ? AND " + DAY_COLUMN_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(date), String.valueOf(user_id)};

        Cursor cursor = database.query(
                DAY_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            return (cursor.getString(cursor.getColumnIndexOrThrow(DAY_COLUMN_DATE)).equals(String.valueOf(date)) &&
                    cursor.getString(cursor.getColumnIndexOrThrow(DAY_COLUMN_USERID)).equals(String.valueOf(user_id)));
        }
        cursor.close();
        return false;
    }
}
