package com.example.bookcornerapp_java.adapters;

import static com.google.common.reflect.Reflection.getPackageName;

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
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.sharedPreferences.SharedPreferencesHelper;

import java.util.List;

// CartAdapter.java
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Book> cartItems;
    private Context context; // Context değişkeni

    public CartAdapter(List<Book> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Book cartItem = cartItems.get(position);
//        holder.cartItemImageView.setImageResource(cartItem.getImage());
        // Assuming your image is in the "drawable" folder with the filename "your_image_name"
        int drawableResourceId = context.getResources().getIdentifier(cartItem.getImage(), "drawable", context.getPackageName());
        holder.cartItemImageView.setImageResource(drawableResourceId);


        holder.cartItemNameTextView.setText(cartItem.getName());
        holder.cartItemPriceTextView.setText("$" + cartItem.getPrice());

        holder.cartItemDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the book from the cart
                SharedPreferencesHelper.removeBookFromCart(v.getContext(), cartItem);
                Log.d("CartAdapter", "Delete icon clicked for book: " + cartItem.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    public void updateCartItems(List<Book> updatedCartItems) {
        this.cartItems = updatedCartItems;
        notifyDataSetChanged();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImageView;
        TextView cartItemNameTextView;
        TextView cartItemPriceTextView;
        ImageView cartItemDeleteIcon;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            // Kart öğelerini tanımla
            cartItemDeleteIcon=itemView.findViewById(R.id.deleteIcon);
            cartItemImageView = itemView.findViewById(R.id.cartItemImageView);
            cartItemNameTextView = itemView.findViewById(R.id.cartItemNameTextView);
            cartItemPriceTextView = itemView.findViewById(R.id.cartItemPriceTextView);

        }

    }

}

