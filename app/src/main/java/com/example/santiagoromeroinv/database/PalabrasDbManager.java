package com.example.santiagoromeroinv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago.romero on 9/06/16.
 */
public class PalabrasDbManager {


    public static final String TABLE_NAME = "word";
    public static final String PALABRA = "palabra";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + PALABRA + " text  primary key);";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public PalabrasDbManager(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generateContentValues(String word) {
        ContentValues valores = new ContentValues();
        valores.put(PALABRA, word);
        return valores;
    }

    public void insertPalabra(String word) {
        db.insert(TABLE_NAME, null, generateContentValues(word));
    }


    public List getAllWords(){
        List palabras = new ArrayList();
        Cursor cursor = null;
        String palabra = "";
        try{
            cursor = db.rawQuery("SELECT palabra FROM word",null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                palabra = cursor.getString(cursor.getColumnIndex("palabra"));
                palabras.add(palabra);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return palabras;
    }

    public int getCount(){
        int count = 0;
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT * FROM word",null);
            count = cursor.getCount();
        }finally {
            cursor.close();
        }
        return count;
    }
}
