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
public class FriendController{
    private SQLiteManager dbManager;

    public FriendController(SQLiteManager dbManager){
        this.dbManager = dbManager;
    }

    public void addFriend(Friend friend){
        //for logging
        Log.d("addFriend", friend.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = dbManager.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(dbManager.FRIENDS_NAME, friend.getName());
        values.put(dbManager.FRIENDS_PHONE, friend.getPhone());
        values.put(dbManager.FRIENDS_CATEGORY, friend.getCategory());
        values.put(dbManager.FRIENDS_PHOTO, friend.getPhoto());

        // 3. insert
        db.insert(dbManager.TABLE_FRIENDS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Friend getFriend(String phone){

        // 1. get reference to readable DB
        SQLiteDatabase db = dbManager.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(dbManager.TABLE_FRIENDS, // a. table
                        dbManager.FRIENDS_COLUMNS, // b. column names
                        dbManager.FRIENDS_PHONE + " = ?", // c. selections
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
        String query = "SELECT  * FROM " + dbManager.TABLE_FRIENDS + " ORDER BY " + dbManager.FRIENDS_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = dbManager.getWritableDatabase();
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
        SQLiteDatabase db = dbManager.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(dbManager.FRIENDS_NAME, friend.getName());
        values.put(dbManager.FRIENDS_PHONE, friend.getPhone());
        values.put(dbManager.FRIENDS_CATEGORY, friend.getCategory());
        values.put(dbManager.FRIENDS_PHOTO, friend.getPhoto());

        // 3. updating row
        int i = db.update(dbManager.TABLE_FRIENDS, //table
                values, // column/value
                dbManager.FRIENDS_PHONE + " = ?", // c. selections
                new String[] { oldPhone }  // d. selections args
        );

        // 4. close
        db.close();

        return i;

    }

    public void deleteFriend(Friend friend) {

        // 1. get reference to writable DB
        SQLiteDatabase db = dbManager.getWritableDatabase();

        // 2. delete
        db.delete(dbManager.TABLE_FRIENDS, //table name
                dbManager.FRIENDS_PHONE + " = ?", // c. selections
                new String[] { friend.getPhone() }  // d. selections args
        );

        // 3. close
        db.close();

        //log
        Log.d("deleteFriend", friend.toString());

    }

    public void clearFriends(){

        SQLiteDatabase db = dbManager.getWritableDatabase();

        // 2. delete
        db.delete(dbManager.TABLE_FRIENDS, "1",null);

        // 3. close
        db.close();

        //log
        Log.d("clearFriends", "limpou");
    }
}
