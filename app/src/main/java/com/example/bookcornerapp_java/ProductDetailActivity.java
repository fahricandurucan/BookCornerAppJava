package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.sharedPreferences.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailActivity extends AppCompatActivity {

    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_book")) {
            Book selectedBook = (Book) intent.getSerializableExtra("selected_book");

            // Burada seçilen kitap bilgilerini kullanabilirsiniz
            // Örnek olarak:
            TextView bookNameTextView = findViewById(R.id.bookNameTextView);
            TextView bookPriceTextView = findViewById(R.id.bookPriceTextView);
            TextView bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
            TextView bookPublisherText = findViewById(R.id.bookPublisherText);
            ImageView bookImage = findViewById(R.id.bookImage);

            bookNameTextView.setText(selectedBook.getName());
            bookPriceTextView.setText("$" + selectedBook.getPrice());
            bookAuthorTextView.setText(selectedBook.getAuthor());
            bookPublisherText.setText(selectedBook.getPublisher());
//            bookImage.setImageResource(selectedBook.getImage());
            int drawableResourceId = getResources().getIdentifier(selectedBook.getImage(), "drawable", getPackageName());
            bookImage.setImageResource(drawableResourceId);


            Button addToCartBtn = (Button) findViewById(R.id.addToCartBtn);

            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCart(selectedBook);
                    Toast.makeText(ProductDetailActivity.this, selectedBook.getName()+" kitabı sepetinize eklendi", Toast.LENGTH_SHORT).show();
                }
            });

            ImageView backButton =  findViewById(R.id.backBtn);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        }

    }



        private void addToCart(Book book) {
            // Helper sınıfını kullanarak sepete eklenen ürünleri kaydet
            List<Book> cartItems = SharedPreferencesHelper.getCartItems(this);
            cartItems.add(book);
            SharedPreferencesHelper.saveCartItems(this, cartItems);

            Toast.makeText(this, book.getName() + " kitabı sepetinize eklendi", Toast.LENGTH_SHORT).show();
        }

//        // SharedPreferences kullanarak sepete eklenen ürünleri kaydet
//        SharedPreferences sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("cart", null);
//
//        if (json != null) {
//            // Eğer önce bir liste kaydedilmişse, onu al
//            Type type = new TypeToken<List<Book>>() {}.getType();
//            List<Book> cart = gson.fromJson(json, type);
//            if (cart == null) {
//                cart = new ArrayList<>();
//            }
//            // Yeni ürünü listeye ekle
//            cart.add(book);
//            // Listeyi tekrar JSON formatına çevir ve SharedPreferences'a kaydet
//            json = gson.toJson(cart);
//            editor.putString("cart", json);
//        } else {
//            // İlk ürünü ekliyorsak yeni bir liste oluştur ve kaydet
//            List<Book> cart = new ArrayList<>();
//            cart.add(book);
//            json = gson.toJson(cart);
//            editor.putString("cart", json);
//        }
//
//        // Değişiklikleri kaydet
//        editor.apply();

}