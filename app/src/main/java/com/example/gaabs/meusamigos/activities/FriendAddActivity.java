package com.example.gaabs.meusamigos.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.gaabs.meusamigos.adapters.CategorySpinnerAdapter;
import com.example.gaabs.meusamigos.database.FriendController;
import com.example.gaabs.meusamigos.database.SQLiteManager;
import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Category;
import com.example.gaabs.meusamigos.entities.Friend;
import com.example.gaabs.meusamigos.util.CategoriesManager;

import java.io.IOException;
import java.util.ArrayList;

public class FriendAddActivity extends AppCompatActivity {
    Uri imageUri;
    EditText nameEditText;
    EditText phoneEditText;
    Button friendPictureButton;
    ArrayList<Category> categoriesList;
    Spinner categoriesSpinner;
    CategorySpinnerAdapter categorySpinnerAdapter;
    Button friendAddButton;
    SQLiteManager dbManager;
    FriendController friendController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add);

        nameEditText = (EditText) findViewById(R.id.friend_add_name_editText);
        phoneEditText = (EditText) findViewById(R.id.friend_add_phone_editText);

        friendPictureButton = (Button) findViewById(R.id.friend_choose_picture_button);
        friendPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent,1);
            }
        });

        categoriesList = CategoriesManager.getCategories(this);
        categoriesSpinner = (Spinner) findViewById(R.id.friend_add_category_spinner);
        categorySpinnerAdapter = new CategorySpinnerAdapter(this, categoriesList);
        categoriesSpinner.setAdapter(categorySpinnerAdapter);

        dbManager = new SQLiteManager(this);
        friendController = new FriendController(dbManager);

        friendAddButton = (Button) findViewById(R.id.friend_add_addButton);
        friendAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,phone,category,photo;

                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                category = null;
                if (categoriesSpinner.getSelectedItem() != null)
                    category = ((Category) categoriesSpinner.getSelectedItem()).getName();

                Friend friend = new Friend();
                friend.setName(name);
                friend.setPhone(phone);
                friend.setCategory(category);
                if (imageUri != null) {
                    friend.setPhoto(imageUri.toString());
                }

                friendController.addFriend(friend);
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
