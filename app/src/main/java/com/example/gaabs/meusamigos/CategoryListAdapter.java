package com.example.gaabs.meusamigos;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gaabs on 08/07/16.
 */
public class CategoryListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Category> categoriesList;

    public CategoryListAdapter(Context context,ArrayList<Category> categoriesList){
        this.context = context;
        this.categoriesList = categoriesList;
    }
    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoriesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.category_row_layout, null);
        TextView name = (TextView) view.findViewById(R.id.category_name_textView);
        ImageView photo = (ImageView) view.findViewById(R.id.category_photo_imageView);
        Category category = categoriesList.get(i);

        //Log.i("CategoryAdapter", category.toString());

        name.setText(category.getName());

        if (category.getPhoto() != null){
            Uri photoUri = Uri.parse(category.getPhoto());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e){
                Log.e("FriendListAdapter",e.getMessage());
            }
        }

        int color = category.getColor();
        view.setBackgroundColor(color);

        return view;
    }
}
