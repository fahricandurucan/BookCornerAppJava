package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookcornerapp_java.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] categoryName = {"Science","History","Literature","Academic","Economy","Art"};
        int[] categoryImages = {R.drawable.science_fictions,R.drawable.history,
                R.drawable.literature,R.drawable.academic,R.drawable.economy,R.drawable.art};

        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this,categoryName,categoryImages);
        binding.gridview.setAdapter(categoryAdapter);



    }
}