package com.example.bookcornerapp_java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bookcornerapp_java.adapters.CategoryAdapter;
import com.example.bookcornerapp_java.databinding.ActivityCategoryBinding;
import com.example.bookcornerapp_java.model.Category;
import com.example.bookcornerapp_java.myInterfaces.OnCategoriesLoadedListener;
import com.example.bookcornerapp_java.services.FirestoreManager;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    FirestoreManager firestoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(CategoryActivity.this,R.color.green));


        firestoreManager = new FirestoreManager();

        // get categories from firebase
        firestoreManager.getCategories(new OnCategoriesLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> categoryList) {
                CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categoryList);
                binding.gridview.setAdapter(categoryAdapter);
            }

            @Override
            public void onCategoriesLoadFailed(String errorMessage) {
                Toast.makeText(CategoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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
