package com.example.gaabs.meusamigos.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class FriendListRecycleAdapter extends RecyclerView.Adapter<FriendListRecycleAdapter.FriendHolder> {
    static Context context;
    static ArrayList<Friend> friendList;
    ArrayList<String> categoriesNames;
    ArrayList<Category> categoriesList;

    public FriendListRecycleAdapter(Context context, ArrayList<Friend> friendList){
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

    public static class FriendHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        LinearLayout innerCard;
        ImageView photo;
        TextView name;

        TextView phone;
        int color;

        public FriendHolder(View view){
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardview);
            innerCard = (LinearLayout) view.findViewById(R.id.inner_card);
            photo = (ImageView) view.findViewById(R.id.friend_photo_imageView);
            name = (TextView) view.findViewById(R.id.friend_name_textView);
            phone = (TextView) view.findViewById(R.id.friend_phone_textView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Friend friend = (Friend) FriendListRecycleAdapter.friendList.get(getAdapterPosition());
                    Intent intent = new Intent(FriendListRecycleAdapter.context, FriendEditActivity.class);

                    intent.putExtra("name",friend.getName());
                    intent.putExtra("photo",friend.getPhoto());
                    intent.putExtra("phone",friend.getPhone());
                    intent.putExtra("category",friend.getCategory());

                    FriendListRecycleAdapter.context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_card_row_layout, parent, false);
        FriendHolder friendHolder = new FriendHolder(view);
        return friendHolder;
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        Friend friend = friendList.get(position);
        if (friend.getPhoto() != null){
            Uri photoUri = Uri.parse(friend.getPhoto());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                holder.photo.setImageBitmap(bitmap);
            } catch (IOException e){
                Log.e("FriendListAdapter",e.getMessage());
            }
        }
        holder.name.setText(friend.getName());
        holder.phone.setText(friend.getPhone());

        //view.setBackgroundColor(friend.c);


        int color = 0;
        if (categoriesNames.contains(friend.getCategory()) ) {
            int pos = categoriesNames.indexOf(friend.getCategory());
            Category category = categoriesList.get(pos);
            color = category.getColor();
            Log.i("FriendListAdapter","color: " + color);
        }

        //Uri iconUri = Uri.parse(data[2]);
        //view.setBackgroundColor(color);

//        holder.cardView.setBackgroundColor(color);
        holder.innerCard.setBackgroundColor(color);

        //return view;
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public Friend getFriend(int i){
        return friendList.get(i);
    }

}
