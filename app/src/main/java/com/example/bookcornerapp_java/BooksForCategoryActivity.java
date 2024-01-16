package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookcornerapp_java.adapters.BooksForCategoryAdapter;
import com.example.bookcornerapp_java.adapters.ProductAdapter;
import com.example.bookcornerapp_java.databinding.ActivityProductBinding;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.myInterfaces.OnBooksLoadedListener;
import com.example.bookcornerapp_java.services.FirestoreManager;

import java.util.ArrayList;
import java.util.List;

public class BooksForCategoryActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    FirestoreManager firestoreManager;
    String selectedCategory;

    TextView categoryTextview;

    List<Book> allBooks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(BooksForCategoryActivity.this,R.color.green));

        selectedCategory = getIntent().getStringExtra("selectedCategory");

        TextView textView = findViewById(R.id.textView18);
        textView.setText("⭐"+selectedCategory+"⭐");

        categoryTextview = (TextView) findViewById(R.id.categoryTextview);

        firestoreManager = new FirestoreManager();

        firestoreManager.getBooksByCategory(selectedCategory, new OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> bookList) {
                allBooks = bookList;
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                Toast.makeText(BooksForCategoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // get book by category
        fetchBooksByCategory(selectedCategory);

        ImageView backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // search part
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
        // Tüm kitaplar arasında arama yapa
        List<Book> searchResults = new ArrayList<>();
        for (Book book : allBooks) {
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
            binding.noResultsText.setVisibility(View.GONE);
            BooksForCategoryAdapter booksForCategoryAdapter = new BooksForCategoryAdapter(BooksForCategoryActivity.this,searchResults);
            binding.gridviewProducts.setAdapter(booksForCategoryAdapter);
        }
    }

    private void fetchBooksByCategory(String category) {
        firestoreManager.getBooksByCategory(category, new OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> bookList) {
                BooksForCategoryAdapter adapter = new BooksForCategoryAdapter(BooksForCategoryActivity.this, bookList);
                binding.gridviewProducts.setAdapter(adapter);
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                Toast.makeText(BooksForCategoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
