package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookcornerapp_java.adapters.BooksForCategoryAdapter;
import com.example.bookcornerapp_java.databinding.ActivityProductBinding;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.myInterfaces.OnBooksLoadedListener;
import com.example.bookcornerapp_java.services.FirestoreManager;

import java.util.List;

public class BooksForCategoryActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    FirestoreManager firestoreManager;
    String selectedCategory;

    TextView categoryTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Kategoriyi al
        selectedCategory = getIntent().getStringExtra("selectedCategory");

        categoryTextview = (TextView) findViewById(R.id.categoryTextview);
//        categoryTextview.setText(selectedCategory);



        Log.d("categoryText.setText(selectedCategory.to);",selectedCategory);


        firestoreManager = new FirestoreManager();

        // Kategoriyi kullanarak Firestore'dan kitapları getir
        fetchBooksByCategory(selectedCategory);

        ImageView backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fetchBooksByCategory(String category) {
        // Firestore'dan seçilen kategoriye ait kitapları getir
        firestoreManager.getBooksByCategory(category, new OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> bookList) {
                // Kitapları göster
                BooksForCategoryAdapter adapter = new BooksForCategoryAdapter(BooksForCategoryActivity.this, bookList);
                binding.gridviewProducts.setAdapter(adapter);
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                // Kitapları yüklerken hata durumunu ele alın
                Toast.makeText(BooksForCategoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
