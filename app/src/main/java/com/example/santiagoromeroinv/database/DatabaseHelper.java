package com.example.santiagoromeroinv.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Romero-Pc on 22/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "lab2apprundatabase.sqlite"; // DB file name
    private static int DB_SCHEME_VERSION = 1; // Scheme version

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDbManager.CREATE_TABLE);
        db.execSQL(LoginDbManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ UserDbManager.TABLE_NAME);
        onCreate(db);
    }
}
