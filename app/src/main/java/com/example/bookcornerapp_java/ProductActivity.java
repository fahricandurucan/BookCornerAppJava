package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bookcornerapp_java.databinding.ActivityCategoryBinding;
import com.example.bookcornerapp_java.databinding.ActivityProductBinding;
import com.example.bookcornerapp_java.model.Book;
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

//
//        String[] categoryName = {"War and Peace","The Little Prience","" +
//                "Animal Farm",
//                "1984"};
//        int[] categoryImages = {R.drawable.item3,R.drawable.book1,
//                R.drawable.item7,R.drawable.item6};
//
//        ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this,categoryName,categoryImages);
//        binding.gridviewProducts.setAdapter(productAdapter);

    }
}