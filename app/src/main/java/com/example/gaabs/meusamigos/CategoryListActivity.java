package com.example.gaabs.meusamigos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list);

        Button addButton = (Button) findViewById(R.id.categories_add_button);
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
        ListView categoryListView = (ListView) findViewById(R.id.categories_listView);

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

            for(int i = 0; i < categoryData.length; i++) {
                Log.i("Category part", String.format("i:%d v:%s",i,categoryData[i]));
            }

            categoriesList.add(new Category(name,color,null));

        }
        Collections.sort(categoriesList);

        Log.i("categoriesList", "categories: " + categoriesList.toString());
        //ArrayAdapter categoriesAdapter = new ArrayAdapter(this, R.layout.category_row_layout, categoriesList);
        CategoryListAdapter categoriesAdapter = new CategoryListAdapter(this, categoriesList);
        categoryListView.setAdapter(categoriesAdapter);
    }
}
