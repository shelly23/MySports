package persistence.daos;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBUtils {

    @SuppressLint("Range")
    public static long getNewId(String table, SQLiteDatabase database) {

        final String MY_QUERY = "SELECT MAX(_id) AS _id FROM "+ table;
        Cursor mCursor = database.rawQuery(MY_QUERY, null);
        long id = 0;

        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            if (mCursor.getColumnIndex(BaseColumns._ID) >= 0) {
                id = mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));
            }
        }

        mCursor.close();
        return id+1;
    }

}
