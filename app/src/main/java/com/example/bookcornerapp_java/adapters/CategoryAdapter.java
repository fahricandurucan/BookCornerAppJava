package com.example.bookcornerapp_java.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookcornerapp_java.BooksForCategoryActivity;
import com.example.bookcornerapp_java.R;
import com.example.bookcornerapp_java.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    List<Category> categoryList;
    LayoutInflater inflater;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageProduct);
        TextView textView = convertView.findViewById(R.id.productName);

        Category category = categoryList.get(position);

//        imageView.setImageResource(category.getImage());
        int drawableResourceId = context.getResources().getIdentifier(category.getImage(), "drawable", context.getPackageName());
        imageView.setImageResource(drawableResourceId);

        textView.setText(category.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category clickedCategory = categoryList.get(position);

                Intent intent = new Intent(context, BooksForCategoryActivity.class);
                intent.putExtra("selectedCategory", clickedCategory.getName());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
