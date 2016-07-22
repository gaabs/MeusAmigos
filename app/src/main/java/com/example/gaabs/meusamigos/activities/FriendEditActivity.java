package com.example.gaabs.meusamigos.activities;

import android.content.Intent;
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
import com.example.gaabs.meusamigos.database.FriendController;
import com.example.gaabs.meusamigos.database.SQLiteManager;
import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Category;
import com.example.gaabs.meusamigos.entities.Friend;
import com.example.gaabs.meusamigos.util.CategoriesManager;

import java.io.IOException;
import java.util.ArrayList;

public class FriendEditActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button friendPictureButton;
    private Button friendSaveButton;
    private Button friendRemoveButton;
    private Spinner categoriesSpinner;

    private FriendController friendController;
    private String oldPhone;
    private Uri imageUri;
    private ArrayList<Category> categoriesList;

    private static int REQUEST_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: necessario extract?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_edit);

        friendController = new FriendController(this);

        nameEditText = (EditText) findViewById(R.id.friend_edit_name_editText);
        phoneEditText = (EditText) findViewById(R.id.friend_edit_phone_editText);

        friendPictureButton = (Button) findViewById(R.id.friend_choose_picture_button);
        friendPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent,REQUEST_PHOTO);
            }
        });

        categoriesSpinner = (Spinner) findViewById(R.id.friend_edit_category_spinner);
        categoriesList = CategoriesManager.getCategories(this);
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(this, categoriesList);
        categoriesSpinner.setAdapter(categorySpinnerAdapter);

        friendSaveButton = (Button) findViewById(R.id.friend_edit_saveButton);
        friendSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFriend();
                Log.i("FriendEdit","editou");
                finish();
            }
        });

        friendRemoveButton = (Button) findViewById(R.id.friend_edit_removeButton);
        friendRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFriend();
                Log.i("FriendEdit","deletou");
                finish();
            }
        });

        oldPhone = getIntent().getExtras().getString("phone");
        fillWithFriendData();
    }

    private void fillWithFriendData(){
        Friend friend = friendController.getFriend(oldPhone);

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

        nameEditText.setText(friend.getName());
        phoneEditText.setText(friend.getPhone());
        categoriesSpinner.setSelection(getIndex(categoriesList,friend.getCategory()));
    }

    private void updateFriend(){
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String category = ((Category) categoriesSpinner.getSelectedItem()).getName();

        Friend friend = new Friend();
        friend.setName(name);
        friend.setPhone(phone);
        friend.setCategory(category);
        if (imageUri != null) {
            friend.setPhoto(imageUri.toString());
        }

        friendController.updateFriend(oldPhone,friend);
    }

    private void removeFriend(){
        Friend friend = new Friend();
        friend.setPhone(oldPhone);

        friendController.deleteFriend(friend);
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

    private int getIndex(ArrayList<Category> categoriesList, String categoryName){
        int pos = 0;

        for (int i = 0; i < categoriesList.size(); i++){
            if (categoriesList.get(i).getName().equals(categoryName)){
                pos = i;
            }
        }

        return pos;
    }

}
