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
import com.example.gaabs.meusamigos.util.CategoriesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CategoryListActivity extends AppCompatActivity {
    ListView categoryListView;
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

        categoriesList = CategoriesManager.getCategories(this);
        Log.i("categoriesList", "categories: " + categoriesList.toString());
        categoriesAdapter = new CategoryListAdapter(this, categoriesList);
        categoryListView.setAdapter(categoriesAdapter);
    }
}
