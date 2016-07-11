package com.example.gaabs.meusamigos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FriendListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

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

        FriendSQLiteHelper friendSQLiteHelper = new FriendSQLiteHelper(this);
        ArrayList<Friend> friendList = friendSQLiteHelper.getAllFriends();
        FriendListAdapter friendListAdapter = new FriendListAdapter(this, friendList);

        ListView friendListView = (ListView) findViewById(R.id.friends_listView);
        friendListView.setAdapter(friendListAdapter);
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend friend = (Friend) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(FriendListActivity.this, FriendEditActivity.class);

                intent.putExtra("name",friend.getName());
                intent.putExtra("photo",friend.getPhoto());
                intent.putExtra("phone",friend.getPhone());
                intent.putExtra("category",friend.getCategory());

                startActivity(intent);
            }
        });
    }
}
