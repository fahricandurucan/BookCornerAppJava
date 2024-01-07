package com.example.bookcornerapp_java;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookcornerapp_java.model.Book;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    Context context;
    List<Book> bookList;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.product_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageProduct);
        TextView textView = convertView.findViewById(R.id.productName);

        Book book = bookList.get(position);
        // Set data from the Book object
        imageView.setImageResource(book.getImage()); // Assuming Book has a method getImageResource()
        textView.setText(book.getName()); // Assuming Book has a method getProductName()

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            // Pass data to ProductDetailActivity using intent
            intent.putExtra("productId", book.getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
