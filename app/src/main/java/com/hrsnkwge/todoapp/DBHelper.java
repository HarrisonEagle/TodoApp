package com.hrsnkwge.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // データーベース名
    private static final String DATABASE_NAME = "TodoDB.db";
    private static final String TABLE_NAME = "tododb";

    DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //.dbファイルが存在しなければ作成する
        db.execSQL(
               "CREATE TABLE " + TABLE_NAME +" (id INTEGER PRIMARY KEY, title TEXT,content TEXT, time INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_NAME
        );
    }


}

