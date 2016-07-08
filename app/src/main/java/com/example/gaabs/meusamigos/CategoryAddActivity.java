package com.example.gaabs.meusamigos;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CategoryAddActivity extends AppCompatActivity {

    private Uri categoryImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_insert);

        final EditText categoryEditText = (EditText) findViewById(R.id.categories_add_name_editText);

        final Spinner colorSpinner = (Spinner) findViewById(R.id.categories_add_color_spinner);

        String[] colorStrings = {"Azul","Vermelho","Verde"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(colorStrings));
        final int[] colorInts = {Color.BLUE, Color.RED, Color.GREEN};

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text,arrayList);
        ColorSpinnerAdapter spinnerAdapter = new ColorSpinnerAdapter(this,colorStrings,colorInts);
        colorSpinner.setAdapter(spinnerAdapter);

        SharedPreferences categoresPreferences = getSharedPreferences("categories",MODE_PRIVATE);
        final SharedPreferences.Editor editor = categoresPreferences.edit();
        final Map<String, ? > categoriesMap = categoresPreferences.getAll();
        Log.i("categoriesAdd", "categories: " + categoriesMap.toString());


        Button categoryPictureButton = (Button) findViewById(R.id.category_choose_picture_button);
        categoryPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick","Clicou para escolher imagem");
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, 1);
            }


        });

        Button categoryAddButton = (Button) findViewById(R.id.category_add_button);
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
            ImageView categoryIcon = (ImageView) findViewById(R.id.category_icon_imageView);
            Uri imageUri = data.getData();
            categoryImageUri = imageUri;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                categoryIcon.setImageBitmap(bitmap);
                Log.i("onActivityResult","Escolheu imagem!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
