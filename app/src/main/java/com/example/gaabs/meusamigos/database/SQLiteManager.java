package com.example.gaabs.meusamigos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gaabs.meusamigos.entities.Friend;

import java.util.ArrayList;

/**
 * Created by gaabs on 08/07/16.
 */
public class SQLiteManager extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "mydb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_FRIENDS = "friends";
    public static final String FRIENDS_NAME = "name";
    public static final String FRIENDS_PHONE = "phone";
    public static final String FRIENDS_CATEGORY = "category";
    public static final String FRIENDS_PHOTO = "photo";
    public static final String[] FRIENDS_COLUMNS = {FRIENDS_NAME, FRIENDS_PHONE, FRIENDS_CATEGORY, FRIENDS_PHOTO};


    public SQLiteManager(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_FRIEND_TABLE = "CREATE TABLE " + TABLE_FRIENDS + " (" +
                FRIENDS_NAME + " STRING, " +
                FRIENDS_PHONE + " STRING PRIMARY KEY, " +
                FRIENDS_CATEGORY + " STRING, " +
                FRIENDS_PHOTO + " STRING )";
        sqLiteDatabase.execSQL(CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(sqLiteDatabase);
    }
}
