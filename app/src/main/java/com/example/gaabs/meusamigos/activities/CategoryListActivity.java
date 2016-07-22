package com.example.gaabs.meusamigos.activities;

import android.content.Intent;
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

public class CategoryListActivity extends AppCompatActivity {
    private ListView categoryListView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

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

        ArrayList<Category> categoriesList = CategoriesManager.getCategories(this);
        CategoryListAdapter categoriesAdapter = new CategoryListAdapter(this, categoriesList);
        categoryListView.setAdapter(categoriesAdapter);
    }
}
