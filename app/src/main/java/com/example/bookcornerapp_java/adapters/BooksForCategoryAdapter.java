package com.example.bookcornerapp_java.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookcornerapp_java.ProductDetailActivity;
import com.example.bookcornerapp_java.R;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.FavoriteBook;
import com.example.bookcornerapp_java.model.FavoriteBookManager;

import java.util.List;

public class BooksForCategoryAdapter extends BaseAdapter {

    Context context;
    List<Book> bookList;
    LayoutInflater inflater;

    public BooksForCategoryAdapter(Context context, List<Book> bookList) {
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
            convertView = inflater.inflate(R.layout.book_card_category, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.bookImage);
        TextView textView = convertView.findViewById(R.id.bookName);
        TextView priceTextView = convertView.findViewById(R.id.bookPrice);


        Book book = bookList.get(position);

        int drawableResourceId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        imageView.setImageResource(drawableResourceId);
        textView.setText(book.getName());
        priceTextView.setText("$"+book.getPrice()); // Assuming Book has a method getPrice()

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("selected_book", book);
            context.startActivity(intent);
        });

        return convertView;
    }

}

