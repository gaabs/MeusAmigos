package com.example.gaabs.meusamigos.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gaabs.meusamigos.entities.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by gaabs on 14/07/16.
 */
public class CategoriesManager {

    public static ArrayList<Category> getCategories(Context context){
        SharedPreferences categoriesPreferences = context.getSharedPreferences("categories", Context.MODE_PRIVATE);
        Map<String, String> categoriesMap = (Map<String,String>) categoriesPreferences.getAll();

        ArrayList<Category> categoriesList = new ArrayList<>();

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
        return categoriesList;
    }
}
