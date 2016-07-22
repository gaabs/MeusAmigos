package com.example.gaabs.meusamigos.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.Toast;

import com.example.gaabs.meusamigos.adapters.ColorSpinnerAdapter;
import com.example.gaabs.meusamigos.R;

import java.io.IOException;
import java.util.Map;

public class CategoryAddActivity extends AppCompatActivity {
    private EditText categoryEditText;
    private Spinner colorSpinner;
    private Button categoryPictureButton;
    private Button categoryAddButton;
    private ImageView categoryIcon;

    private Uri categoryImageUri;
    private SharedPreferences categoriesPreferences;
    private SharedPreferences.Editor editor;
    private Map<String, ? > categoriesMap;

    private static int REQUEST_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        categoryEditText = (EditText) findViewById(R.id.categories_add_name_editText);
        colorSpinner = (Spinner) findViewById(R.id.categories_add_color_spinner);

        initColorSpinner();
        initCategories();

        categoryPictureButton = (Button) findViewById(R.id.category_choose_picture_button);
        categoryPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick","Clicou para escolher imagem");
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, REQUEST_PHOTO);
            }

        });

        categoryAddButton = (Button) findViewById(R.id.category_add_button);
        categoryAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Esse tamanho eh oq ou deve ser feito um extract?
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

                    // TODO: esse acesso aos Preferences eh Ok ou deve ser modularizado, como no FriendController?

                    finish();
                } else{
                    Toast.makeText(CategoryAddActivity.this, "Ja existe categoria com esse nome!", Toast.LENGTH_SHORT).show();
                    Log.i("categoryCreateOnClick","Nao criou categoria (repetida)");
                }
            }


        });
    }

    private void initColorSpinner(){
        Resources resources;
        String[] colorStrings;
        int[] colorInts;

        resources = getResources();
        colorStrings = resources.getStringArray(R.array.categoriesNames);
        colorInts = resources.getIntArray(R.array.categoriesColors);

        ColorSpinnerAdapter spinnerAdapter = new ColorSpinnerAdapter(this,colorStrings,colorInts);
        colorSpinner.setAdapter(spinnerAdapter);
    }

    private void initCategories(){
        categoriesPreferences = getSharedPreferences("categories",MODE_PRIVATE);
        editor = categoriesPreferences.edit();
        categoriesMap = categoriesPreferences.getAll();
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
