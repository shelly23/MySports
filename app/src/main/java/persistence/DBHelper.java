package persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "mysports.db";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_ENTRIES_USER);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_ENTRIES_DAYS);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA0);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA1);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA2);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA3);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA4);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA5);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA6);
        sqLiteDatabase.execSQL(DBCommands.SQL_CREATE_MOCKDATA7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
