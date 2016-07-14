package com.example.gaabs.meusamigos.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Category;
import com.example.gaabs.meusamigos.entities.Friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by gaabs on 08/07/16.
 */
public class FriendListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Friend> friendList;
    ArrayList<Category> categoriesList;
    ArrayList<String> categoriesNames;

    public FriendListAdapter(Context context, ArrayList<Friend> friendList){
        this.context = context;
        this.friendList = friendList;

        SharedPreferences categoriesPreferences = context.getSharedPreferences("categories", Context.MODE_PRIVATE);
        Map<String,String> categoriesMap = (Map<String,String>) categoriesPreferences.getAll();

        categoriesList = new ArrayList<>();
        categoriesNames = new ArrayList<>();
        for(Map.Entry<String,String> category : categoriesMap.entrySet()){
            String categoryData[] = category.getValue().split(",",-1);
            String name,photo;
            int color;
            name = categoryData[0];
            color = Integer.parseInt(categoryData[1]);
            photo = categoryData[2];

            for(int i = 0; i < categoryData.length; i++) {
                Log.i("Category part", String.format("i:%d v:%s",i,categoryData[i]));
            }

            categoriesList.add(new Category(name,color,photo));
            categoriesNames.add(name);
        }
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
        view = inflater.inflate(R.layout.item_friend, null);
        ImageView photo = (ImageView) view.findViewById(R.id.friend_photo_imageView);
        TextView name = (TextView) view.findViewById(R.id.friend_name_textView);
        TextView phone = (TextView) view.findViewById(R.id.friend_phone_textView);

        //Log.i("FriendAdapter", friendList.get(i).toString());

        Friend friend = friendList.get(i);
        //Category category = friend.getCategory();
        if (friend.getPhoto() != null){
            Uri photoUri = Uri.parse(friend.getPhoto());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e){
                Log.e("FriendListAdapter",e.getMessage());
            }
        }
        name.setText(friend.getName());
        phone.setText(friend.getPhone());

        //view.setBackgroundColor(friend.c);


        int color = 0;
        if (categoriesNames.contains(friend.getCategory()) ) {
            int pos = categoriesNames.indexOf(friend.getCategory());
            Category category = categoriesList.get(pos);
            color = category.getColor();
            Log.i("FriendListAdapter","color: " + color);
        }

        //Uri iconUri = Uri.parse(data[2]);
        view.setBackgroundColor(color);

        return view;
    }
}
