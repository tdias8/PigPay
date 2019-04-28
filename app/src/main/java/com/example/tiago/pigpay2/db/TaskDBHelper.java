package com.example.tiago.pigpay2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class TaskDBHelper extends SQLiteOpenHelper {
    // SQLiteDatabase db;
    private static TaskDBHelper dbhelper;
    private SQLiteDatabase mDatabase;
    private int mOpenCounter;

    public static TaskDBHelper getInstance(Context context){
        if (dbhelper==null){
            dbhelper = new TaskDBHelper(context);
        }
        return dbhelper;
    }
    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TaskContract.TABLE +
                "( " + TaskContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskContract.Columns.DATA + " TEXT ," +
                TaskContract.Columns.DESCRICAO + " TEXT , " +
                TaskContract.Columns.TIPO + " TEXT , " +
                TaskContract.Columns.VALOR + " TEXT " +
                ")";
        db.execSQL(sqlQuery);
    }

    public synchronized Cursor select(String query) throws SQLException {
        return openDatabase().rawQuery(query, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);

    }


    public synchronized void insert(String table, ContentValues values, Uri CONTENT_URI) throws SQLException {
        openDatabase().insert(table, null, values);
    }


    private synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            mDatabase = getWritableDatabase();
        }
        return mDatabase;
    }

}
