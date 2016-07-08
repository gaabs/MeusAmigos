package com.example.gaabs.meusamigos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by gaabs on 07/07/16.
 */
public class CategorySpinnerAdapter extends BaseAdapter {
    Context context;
    String[] titles;
    int[] colors;
    public CategorySpinnerAdapter(Context context, String[] titles, int[] colors){
        this.context = context;
        this.titles = titles;
        this.colors = colors;
    }


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return colors[i];
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
        text.setText(titles[i]);
        text.setTextColor(colors[i]);
        //text.setBackgroundColor(colors[i]);

        return view;
    }
}
