package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookcornerapp_java.adapters.ProductAdapter;
import com.example.bookcornerapp_java.databinding.ActivityProductBinding;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.FavoriteBookManager;
import com.example.bookcornerapp_java.myInterfaces.OnBooksLoadedListener;
import com.example.bookcornerapp_java.services.FirestoreManager;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ActivityProductBinding binding;
    FirestoreManager firestoreManager;
    List<Book> allBooks; // we hold all bokks

    TextView noResultsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(ProductActivity.this,R.color.green));


        firestoreManager = new FirestoreManager();
        allBooks = new ArrayList<>();
        noResultsText = findViewById(R.id.noResultsText);

        TextView textView = findViewById(R.id.textView18);
        textView.setText("All Books");

        // we get books from Firestore
        firestoreManager.getBooks(new OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> bookList) {
                allBooks.addAll(bookList);
                // Update the adapter
                ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, bookList);
                binding.gridviewProducts.setAdapter(productAdapter);
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
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

        // part of search
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();
                performSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

    private void performSearch(String searchText) {
        // search book
        List<Book> searchResults = new ArrayList<>();
        for (Book book : allBooks) {
            // Kitap isminde arama yap
            if (book.getName().toLowerCase().contains(searchText.toLowerCase())) {
                searchResults.add(book);
            }
        }

        if(searchResults.isEmpty()){
            binding.gridviewProducts.setVisibility(View.GONE);
            binding.noResultsText.setVisibility(View.VISIBLE);
        }
        else{
            binding.gridviewProducts.setVisibility(View.VISIBLE);
            binding.noResultsText.setVisibility(View.GONE);    // we update the adapter with the search results
            ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, searchResults);
            binding.gridviewProducts.setAdapter(productAdapter);
        }
    }

}