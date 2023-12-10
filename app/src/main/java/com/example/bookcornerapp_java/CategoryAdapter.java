package com.example.bookcornerapp_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    String[] categoryName;
    int[] image;

    LayoutInflater inflater;

    public CategoryAdapter(Context context, String[] categoryName, int[] image) {
        this.context = context;
        this.categoryName = categoryName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return categoryName.length;
    }

    @Override
    public Object getItem(int i) {
        return categoryName[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.grid_item,null);

        }

        ImageView imageView = view.findViewById(R.id.imageProduct);
        TextView textView = view.findViewById(R.id.productName);

        imageView.setImageResource(image[i]);
        textView.setText(categoryName[i])   ;



        return view;
    }
}
