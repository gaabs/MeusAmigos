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
public class FriendSQLiteHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "mydb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_FRIENDS = "friends";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PHOTO = "photo";
    public static final String[] COLUMNS = {KEY_NAME, KEY_PHONE, KEY_CATEGORY, KEY_PHOTO};


    public FriendSQLiteHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_FRIEND_TABLE = "CREATE TABLE " + TABLE_FRIENDS + " (" +
                KEY_NAME + " STRING, " +
                KEY_PHONE + " STRING PRIMARY KEY, " +
                KEY_CATEGORY + " STRING, " +
                KEY_PHOTO + " STRING )";
        sqLiteDatabase.execSQL(CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(sqLiteDatabase);
    }

    public void addFriend(Friend friend){
        //for logging
        Log.d("addFriend", friend.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_PHONE, friend.getPhone());
        values.put(KEY_CATEGORY, friend.getCategory());
        values.put(KEY_PHOTO, friend.getPhoto());

        // 3. insert
        db.insert(TABLE_FRIENDS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Friend getFriend(String phone){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_FRIENDS, // a. table
                        COLUMNS, // b. column names
                        KEY_PHONE + " = ?", // c. selections
                        new String[] { phone }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build friend object
        Friend friend = new Friend();
        friend.setName(cursor.getString(0));
        friend.setPhone(cursor.getString(1));
        friend.setCategory(cursor.getString(2));
        friend.setPhoto(cursor.getString(3));

        //log
        Log.d("getFriend("+phone+")", friend.toString());

        // 5. return friend
        return friend;
    }

    public ArrayList<Friend> getAllFriends() {
        ArrayList<Friend> friends = new ArrayList<Friend>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FRIENDS + " ORDER BY " + KEY_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build friend and add it to list
        Friend friend = null;
        if (cursor.moveToFirst()) {
            do {
                friend = new Friend();
                friend.setName(cursor.getString(0));
                friend.setPhone(cursor.getString(1));
                friend.setCategory(cursor.getString(2));
                friend.setPhoto(cursor.getString(3));

                // Add friend to friends
                friends.add(friend);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFriends()", friends.toString());

        // return friends
        return friends;
    }

    public int updateFriend(String oldPhone, Friend friend) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_PHONE, friend.getPhone());
        values.put(KEY_CATEGORY, friend.getCategory());
        values.put(KEY_PHOTO, friend.getPhoto());

        // 3. updating row
        int i = db.update(TABLE_FRIENDS, //table
                values, // column/value
                    KEY_PHONE + " = ?", // c. selections
                new String[] { oldPhone }  // d. selections args
        );

        // 4. close
        db.close();

        return i;

    }

    public void deleteFriend(Friend friend) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FRIENDS, //table name
                KEY_PHONE + " = ?", // c. selections
                new String[] { friend.getPhone() }  // d. selections args
        );

        // 3. close
        db.close();

        //log
        Log.d("deleteFriend", friend.toString());

    }

    public void clearFriends(){

        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FRIENDS, "1",null);

        // 3. close
        db.close();

        //log
        Log.d("clearFriends", "limpou");
    }
}
