package com.example.gaabs.meusamigos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gaabs on 08/07/16.
 */
public class FriendListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Friend> friendList;

    public FriendListAdapter(Context context, ArrayList<Friend> friendList){
        this.context = context;
        this.friendList = friendList;
    }
    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int i) {
        return friendList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.friend_row_layout, null);
        TextView text = (TextView) view.findViewById(R.id.friend_text);

        Log.i("FriendAdapter", friendList.get(i).toString());
        text.setText(friendList.get(i).toString());
        //int color = Integer.parseInt(data[1]);
        //Uri iconUri = Uri.parse(data[2]);
        //view.setBackgroundColor(color);

        return view;
    }
}
