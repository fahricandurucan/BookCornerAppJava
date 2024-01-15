package com.example.bookcornerapp_java.adapters;

import static com.google.common.reflect.Reflection.getPackageName;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookcornerapp_java.CartFragment;
import com.example.bookcornerapp_java.R;
import com.example.bookcornerapp_java.WaitingCartFragment;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.sharedPreferences.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// CartAdapter.java
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Book> cartItems;

    private Context context; // Context değişkeni

    private  List<Book> tempList;

    CartFragment cartFragment;

    public CartAdapter(List<Book> cartItems, Context context,CartFragment cartFragment) {
        this.cartItems = cartItems;
        this.context = context;
        this.cartFragment = cartFragment;
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

//        holder.cartItemAmountText.setText(String.valueOf(amount));

        holder.cartItemDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the book from the cart
                SharedPreferencesHelper.removeBookFromCart(v.getContext(), cartItem);
                Log.d("CartAdapter", "Delete icon clicked for book: " + cartItem.getName());
                Toast.makeText(v.getContext(), cartItem.getName()+" removed your cart", Toast.LENGTH_SHORT).show();

                goToHomeFragment();
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

        TextView cartItemAmountText;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            // Kart öğelerini tanımla
            cartItemDeleteIcon=itemView.findViewById(R.id.deleteIcon);
            cartItemImageView = itemView.findViewById(R.id.cartItemImageView);
            cartItemNameTextView = itemView.findViewById(R.id.cartItemNameTextView);
            cartItemPriceTextView = itemView.findViewById(R.id.cartItemPriceTextView);
            cartItemAmountText = itemView.findViewById(R.id.amountText);

        }

    }

    private void goToHomeFragment() {
        // Belirli bir süre beklemek için Handler kullanımı
        // WaitingFragment'i göster
        cartFragment.requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new WaitingCartFragment(R.drawable.delete)) // fragment_container yerine uygun container ID'sini kullanın
                .addToBackStack(null)
                .commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Burada belirli bir süre bekledikten sonra yapılacak işlemleri ekleyebilirsiniz
                // Örneğin, başka bir işlemi gerçekleştirebilir veya ekranı güncelleyebilirsiniz
                cartFragment.requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new CartFragment()) // fragment_container yerine uygun container ID'sini kullanın
                        .commit();
            }
        }, 3000); // 2000 milisaniye (2 saniye) bekleyecek şekilde ayarlandı, ihtiyaca göre değiştirilebilir

    }

}

