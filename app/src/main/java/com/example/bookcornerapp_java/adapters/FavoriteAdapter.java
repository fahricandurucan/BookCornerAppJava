package com.example.bookcornerapp_java.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookcornerapp_java.R;
import com.example.bookcornerapp_java.model.FavoriteBook;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteBook> favoriteBookList;
    private Context context; // Context değişkeni


    // Adapter constructor
    public FavoriteAdapter(List<FavoriteBook> favoriteBookList,Context context) {
        this.favoriteBookList = favoriteBookList;
        this.context = context;
    }

    // Inner ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookImage;
        public TextView bookName;
        public TextView bookPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.favorite_book_image);
            bookName = itemView.findViewById(R.id.favorite_book_name);
            bookPrice = itemView.findViewById(R.id.favorite_book_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        FavoriteBook favoriteBook = favoriteBookList.get(position);
        Log.d("favori",favoriteBook.getName());
        // Set item views based on your views and data model
//        holder.bookImage.setImageResource(favoriteBook.getImage());
        int drawableResourceId = context.getResources().getIdentifier(favoriteBook.getImage(), "drawable", context.getPackageName());
        holder.bookImage.setImageResource(drawableResourceId);

        holder.bookName.setText(favoriteBook.getName());
        holder.bookPrice.setText("$" + favoriteBook.getPrice());
    }

    @Override
    public int getItemCount() {
        return favoriteBookList.size();
    }
}
