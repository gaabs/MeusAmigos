package com.example.gaabs.meusamigos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FriendAddActivity extends AppCompatActivity {
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_insert);

        final EditText nameEditText = (EditText) findViewById(R.id.friend_add_name_editText);
        final EditText phoneEditText = (EditText) findViewById(R.id.friend_add_phone_editText);

        Button friendPictureButton = (Button) findViewById(R.id.friend_choose_picture_button);
        friendPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent,1);
            }
        });

        SharedPreferences categoriesPreferences = getSharedPreferences("categories", MODE_PRIVATE);
        Map<String,String> categoriesMap = (Map<String,String>) categoriesPreferences.getAll();

        ArrayList<Category> categoriesList = new ArrayList<>();
        for(Map.Entry<String,String> category : categoriesMap.entrySet()){
            String categoryData[] = category.getValue().split(",",-1);
            String name,photo;
            int color;
            name = categoryData[0];
            color = Integer.parseInt(categoryData[1]);
            photo = categoryData[2];

            categoriesList.add(new Category(name,color,photo));
        }
        Collections.sort(categoriesList);

        final Spinner categoriesSpinner = (Spinner) findViewById(R.id.friend_add_category_spinner);
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(this, categoriesList);
        categoriesSpinner.setAdapter(categorySpinnerAdapter);

        Button friendAddButton = (Button) findViewById(R.id.friend_add_addButton);
        friendAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,phone,category,photo;
                FriendSQLiteHelper friendSQLiteHelper = new FriendSQLiteHelper(FriendAddActivity.this);

                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                category = null;
                if ((Category) categoriesSpinner.getSelectedItem() != null)
                    category = ((Category) categoriesSpinner.getSelectedItem()).getName();

                Friend friend = new Friend();
                friend.setName(name);
                friend.setPhone(phone);
                friend.setCategory(category);
                if (imageUri != null) {
                    friend.setPhoto(imageUri.toString());
                }

                friendSQLiteHelper.addFriend(friend);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ImageView friendPhoto = (ImageView) findViewById(R.id.friend_photo_imageView);
                friendPhoto.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
