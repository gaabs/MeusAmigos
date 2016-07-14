package com.example.gaabs.meusamigos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gaabs.meusamigos.R;
import com.example.gaabs.meusamigos.entities.Category;

import java.util.ArrayList;

/**
 * Created by gaabs on 07/07/16.
 */
public class CategorySpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Category> categoriesList;

    public CategorySpinnerAdapter(Context context,ArrayList<Category> categoriesList){
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoriesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.spinner_text, null);
        TextView text = (TextView) view.findViewById(R.id.spinner_text);

        Category category = categoriesList.get(i);
        text.setText(category.getName());
        text.setBackgroundColor(category.getColor());

        return view;
    }
}
