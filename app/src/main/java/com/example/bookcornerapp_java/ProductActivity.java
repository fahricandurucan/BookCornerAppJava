package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookcornerapp_java.adapters.ProductAdapter;
import com.example.bookcornerapp_java.databinding.ActivityProductBinding;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.FavoriteBookManager;
import com.example.bookcornerapp_java.myInterfaces.OnBooksLoadedListener;
import com.example.bookcornerapp_java.services.FirestoreManager;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    FirestoreManager firestoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestoreManager = new FirestoreManager();
        // Fetch books from Firestore
        firestoreManager.getBooks(new OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> bookList) {
                // Update the adapter with the list of books
                ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, bookList);
                binding.gridviewProducts.setAdapter(productAdapter);
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                // Handle error loading books
                Toast.makeText(ProductActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        ImageView backButton=findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}