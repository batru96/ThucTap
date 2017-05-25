package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hoangkhoa on 5/24/17.
 */

public class Database extends SQLiteOpenHelper {

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(query);
    }

    // select
    public Cursor getData(String query) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(query, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
