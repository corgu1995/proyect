package com.example.santiagoromeroinv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Romero-Pc on 22/03/2016.
 */
public class UserDbManager {

    public static final String TABLE_NAME = "user";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + ACCOUNT + " text primary key,"
            + PASSWORD + " text not null);";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public UserDbManager(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generateContentValues(String account, String password) {
        ContentValues valores = new ContentValues();
        valores.put(ACCOUNT, account);
        valores.put(PASSWORD, password);
        return valores;
    }

    public void insertUser(String account, String password) {
        if (!userExists(account)) {
            db.insert(TABLE_NAME, null, generateContentValues(account,password));
        }
    }

    public boolean userExists(String account) {
        boolean flag=false;
        Cursor cursor = null;
        String userAccount = "";
        try{
            cursor = db.rawQuery("SELECT account FROM user WHERE account=?", new String[] {account + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                userAccount = cursor.getString(cursor.getColumnIndex("account"));
            }
            if (!userAccount.equals("")){flag=true;}
            return flag;
        }finally {
            cursor.close();
        }
    }

    public String getUserAccount(String account) {
        Cursor cursor = null;
        String userAccount = "";
        try{
            cursor = db.rawQuery("SELECT account FROM user WHERE account=?", new String[] {account + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                userAccount = cursor.getString(cursor.getColumnIndex("account"));
            }
            return userAccount;
        }finally {
            cursor.close();
        }
    }

    public String getUserPassword(String account) {
        Cursor cursor = null;
        String userPassword = "";
        try{
            cursor = db.rawQuery("SELECT password FROM user WHERE account=?", new String[] {account + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                userPassword = cursor.getString(cursor.getColumnIndex("password"));
            }
            return userPassword;
        }finally {
            cursor.close();
        }
    }

}