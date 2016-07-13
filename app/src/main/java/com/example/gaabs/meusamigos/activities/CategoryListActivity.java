package com.example.gaabs.meusamigos.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.gaabs.meusamigos.adapters.CategoryListAdapter;
import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CategoryListActivity extends AppCompatActivity {
    ListView categoryListView;
    SharedPreferences categoriesPreferences;
    Map<String, String> categoriesMap;
    ArrayList<Category> categoriesList;
    CategoryListAdapter categoriesAdapter;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        addButton = (Button) findViewById(R.id.categories_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryListActivity.this, CategoryAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryListView = (ListView) findViewById(R.id.categories_listView);

        categoriesPreferences = getSharedPreferences("categories", MODE_PRIVATE);
        categoriesMap = (Map<String,String>) categoriesPreferences.getAll();

        categoriesList = new ArrayList<>();
        String categoryData[];
        String name,photo;
        int color;
        for(Map.Entry<String,String> category : categoriesMap.entrySet()){
            categoryData = category.getValue().split(",",-1);
            name = categoryData[0];
            color = Integer.parseInt(categoryData[1]);
            photo = categoryData[2];

            categoriesList.add(new Category(name,color,photo));
        }
        Collections.sort(categoriesList);

        Log.i("categoriesList", "categories: " + categoriesList.toString());
        categoriesAdapter = new CategoryListAdapter(this, categoriesList);
        categoryListView.setAdapter(categoriesAdapter);
    }
}
