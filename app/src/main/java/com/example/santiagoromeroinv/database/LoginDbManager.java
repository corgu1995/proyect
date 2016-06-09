package com.example.santiagoromeroinv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by santiago.romero on 29/03/16.
 */
public class LoginDbManager {

    public static final String TABLE_NAME = "loggedUser";
    public static final String ACCOUNT = "account";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + ACCOUNT + " text primary key);";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public LoginDbManager(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generateContentValues(String account) {
        ContentValues valores = new ContentValues();
        valores.put(ACCOUNT, account);
        return valores;
    }

    public void insertUserAccount(String account) {
        if (!isUserLogged()) {
            db.insert(TABLE_NAME, null, generateContentValues(account));
        }
    }


    public String getUserAccount() {
        Cursor cursor = null;
        String userAccount = "";
        try{
            cursor = db.rawQuery("SELECT account FROM loggedUser ",null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                userAccount = cursor.getString(cursor.getColumnIndex("account"));
            }
            return userAccount;
        }finally {
            cursor.close();
        }
    }

    public boolean isUserLogged() {
        Cursor cursor = null;
        boolean userLogged;
        int count;
        try{
            cursor = db.rawQuery("SELECT COUNT(*) FROM loggedUser", null);

                cursor.moveToFirst();
                count = cursor.getInt(0);
                if (count==0){
                    userLogged = false;
                }
                else{
                    userLogged = true;
                }

        } finally {
            cursor.close();
        }
        return userLogged;
    }

    public void deleteData() {
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
