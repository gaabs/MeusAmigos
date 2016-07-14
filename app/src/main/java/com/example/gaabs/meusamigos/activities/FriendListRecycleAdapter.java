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
import com.example.gaabs.meusamigos.util.CategoriesManager;

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

        categoriesList = CategoriesManager.getCategories(context);
        categoriesNames = new ArrayList<>();
        for(Category category : categoriesList){
            categoriesNames.add(category.getName());
        }
    }

    public static class FriendHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        LinearLayout innerCard;
        ImageView photo;
        TextView name;
        TextView phone;

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
                    Friend friend = FriendListRecycleAdapter.friendList.get(getAdapterPosition());
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_card, parent, false);
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

        int color = 0;
        if (categoriesNames.contains(friend.getCategory()) ) {
            int pos = categoriesNames.indexOf(friend.getCategory());
            Category category = categoriesList.get(pos);
            color = category.getColor();
            Log.i("FriendListAdapter","color: " + color);
        }
        holder.innerCard.setBackgroundColor(color);

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
