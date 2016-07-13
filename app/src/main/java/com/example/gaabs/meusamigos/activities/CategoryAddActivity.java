package com.example.gaabs.meusamigos.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.example.gaabs.meusamigos.adapters.ColorSpinnerAdapter;
import com.example.gaabs.meusamigos.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CategoryAddActivity extends AppCompatActivity {
    Uri categoryImageUri;
    EditText categoryEditText;
    Spinner colorSpinner;
    ColorSpinnerAdapter spinnerAdapter;
    SharedPreferences categoresPreferences;
    SharedPreferences.Editor editor;
    Map<String, ? > categoriesMap;
    Button categoryPictureButton;
    Button categoryAddButton;
    ImageView categoryIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_insert);

        categoryEditText = (EditText) findViewById(R.id.categories_add_name_editText);
        colorSpinner = (Spinner) findViewById(R.id.categories_add_color_spinner);

        String[] colorStrings = {"Vermelho","Verde","Azul"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(colorStrings));
        final int[] colorInts = {Color.rgb(255,102,102), Color.rgb(153,255,153), Color.rgb(153,204,255)};

        spinnerAdapter = new ColorSpinnerAdapter(this,colorStrings,colorInts);
        colorSpinner.setAdapter(spinnerAdapter);

        categoresPreferences = getSharedPreferences("categories",MODE_PRIVATE);
        editor = categoresPreferences.edit();
        categoriesMap = categoresPreferences.getAll();
        Log.i("categoriesAdd", "categories: " + categoriesMap.toString());

        categoryPictureButton = (Button) findViewById(R.id.category_choose_picture_button);
        categoryPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick","Clicou para escolher imagem");
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, 1);
            }


        });

        categoryAddButton = (Button) findViewById(R.id.category_add_button);
        categoryAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("categoryCreateOnClick","Clicou para criar categoria");
                String name = categoryEditText.getText().toString();
                int color = (int) colorSpinner.getSelectedItem();
                if (categoriesMap.containsKey(name) == false){
                    String categoryData = name + "," + color + ",";
                    if (categoryImageUri != null) {
                        categoryData += categoryImageUri.toString();
                    }

                    Log.i("categoryCreateOnClick","Criou nova categoria=" + name + ":" + categoryData);
                    editor.putString(name,categoryData);
                    editor.commit();
                    finish();
                } else{
                    Log.i("categoryCreateOnClick","Nao criou categoria (repetida)");
                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult","Entrou");
        if (resultCode == RESULT_OK){
            categoryIcon = (ImageView) findViewById(R.id.category_icon_imageView);
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                categoryIcon.setImageBitmap(bitmap);
                categoryImageUri = imageUri;
                Log.i("onActivityResult","Escolheu imagem!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}