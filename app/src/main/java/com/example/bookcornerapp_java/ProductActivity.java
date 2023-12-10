package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookcornerapp_java.databinding.ActivityCategoryBinding;
import com.example.bookcornerapp_java.databinding.ActivityProductBinding;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] categoryName = {"War and Peace","The Little Prience","" +
                "Animal Farm",
                "1984"};
        int[] categoryImages = {R.drawable.item3,R.drawable.book1,
                R.drawable.item7,R.drawable.item6};

        ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this,categoryName,categoryImages);
        binding.gridviewProducts.setAdapter(productAdapter);








    }
}