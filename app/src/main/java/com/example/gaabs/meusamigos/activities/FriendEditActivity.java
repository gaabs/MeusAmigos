package com.example.gaabs.meusamigos.activities;

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

import com.example.gaabs.meusamigos.adapters.CategorySpinnerAdapter;
import com.example.gaabs.meusamigos.database.FriendSQLiteHelper;
import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Category;
import com.example.gaabs.meusamigos.entities.Friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class FriendEditActivity extends AppCompatActivity {
    String oldPhone;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_edit);

        oldPhone = getIntent().getExtras().getString("phone");
        FriendSQLiteHelper sql = new FriendSQLiteHelper(this);
        Friend friend = sql.getFriend(oldPhone);

        if (friend.getPhoto() != null) {
            imageUri = Uri.parse(friend.getPhoto());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ImageView friendPhoto = (ImageView) findViewById(R.id.friend_photo_imageView);
                friendPhoto.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        final EditText nameEditText = (EditText) findViewById(R.id.friend_edit_name_editText);
        nameEditText.setText(friend.getName());
        final EditText phoneEditText = (EditText) findViewById(R.id.friend_edit_phone_editText);
        phoneEditText.setText(friend.getPhone());

        Button friendPictureButton = (Button) findViewById(R.id.friend_choose_picture_button);

        friendPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        final Spinner categoriesSpinner = (Spinner) findViewById(R.id.friend_edit_category_spinner);
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(this, categoriesList);
        categoriesSpinner.setAdapter(categorySpinnerAdapter);

        categoriesSpinner.setSelection(getIndex(categoriesList,friend.getCategory()));

        Button friendSaveButton = (Button) findViewById(R.id.friend_edit_saveButton);
        friendSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,phone,category,photo;
                FriendSQLiteHelper friendSQLiteHelper = new FriendSQLiteHelper(FriendEditActivity.this);

                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                category = ((Category) categoriesSpinner.getSelectedItem()).getName();

                Friend friend = new Friend();
                friend.setName(name);
                friend.setPhone(phone);
                friend.setCategory(category);
                if (imageUri != null) {
                    friend.setPhoto(imageUri.toString());
                }

                friendSQLiteHelper.updateFriend(oldPhone,friend);
                //TODO nao altera ainda
                Log.i("FriendEdit","editou");
                finish();
            }
        });

        Button friendRemoveButton = (Button) findViewById(R.id.friend_edit_removeButton);
        friendRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone;
                FriendSQLiteHelper friendSQLiteHelper = new FriendSQLiteHelper(FriendEditActivity.this);

                phone = (getIntent().getExtras().getString("phone"));

                Friend friend = new Friend();
                friend.setPhone(phone);

                friendSQLiteHelper.deleteFriend(friend);
                //TODO nao altera ainda
                Log.i("FriendEdit","deletou");
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

    private int getIndex(ArrayList<Category> categoriesList,String categoryName){
        int pos = 0;

        for (int i = 0; i < categoriesList.size(); i++){
            if (categoriesList.get(i).getName().equals(categoryName)){
                pos = i;
            }
        }

        return pos;
    }

}
