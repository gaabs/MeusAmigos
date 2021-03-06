package com.example.gaabs.meusamigos.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.gaabs.meusamigos.adapters.FriendListRecycleAdapter;
import com.example.gaabs.meusamigos.database.FriendController;
import com.example.gaabs.meusamigos.database.SQLiteManager;
import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Friend;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    SQLiteManager dbManager;
    FriendController friendController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendController = new FriendController(this);

        Button importButton = (Button) findViewById(R.id.friend_import_button);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(FriendListActivity.this);
                progressDialog.setMessage("Importando...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                String name, phone, photo;
                Friend friend;

                while (cursor.moveToNext()) {
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)), null, null);
                        pCur.moveToNext();
                        phone = pCur.getString(pCur.getColumnIndex("DATA1"));
                        pCur.close();
                    } else
                        phone = null;

                    photo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                    friend = new Friend();
                    friend.setName(name);
                    friend.setPhone(phone);
                    friend.setPhoto(photo);
                    friendController.addFriend(friend);
                }
                cursor.close();
                refreshList();
                progressDialog.cancel();
            }
        });

        Button clearButton = (Button) findViewById(R.id.friend_clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendController.clearFriends();
                refreshList();
            }
        });

        Button addButton = (Button) findViewById(R.id.friend_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendListActivity.this, FriendAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshList();
    }

    private void refreshList() {
        ArrayList<Friend> friendList = friendController.getAllFriends();
        FriendListRecycleAdapter friendListAdapter = new FriendListRecycleAdapter(this, friendList);

        RecyclerView friendListView = (RecyclerView) findViewById(R.id.friends_listView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        friendListView.setLayoutManager(layoutManager);

        friendListView.setAdapter(friendListAdapter);
        friendListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.i("FriendListOnTouch", "tocou");

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
//        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Friend friend = (Friend) adapterView.getItemAtPosition(i);
//                Intent intent = new Intent(FriendListActivity.this, FriendEditActivity.class);
//
//                intent.putExtra("name",friend.getName());
//                intent.putExtra("photo",friend.getPhoto());
//                intent.putExtra("phone",friend.getPhone());
//                intent.putExtra("category",friend.getCategory());
//
//                startActivity(intent);
//            }
//        });
    }
}
