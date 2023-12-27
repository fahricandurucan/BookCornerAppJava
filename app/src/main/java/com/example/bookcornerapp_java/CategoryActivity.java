package com.example.bookcornerapp_java;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookcornerapp_java.databinding.ActivityCategoryBinding;
import com.example.bookcornerapp_java.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Kategorileri oluştur
        List<Category> categoryList = createCategoryList();

        // Kategorileri sırala (opsiyonel, isteğe bağlı)
        Collections.sort(categoryList, (category1, category2) -> category1.getName().compareTo(category2.getName()));

        // Kategorileri göster
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categoryList);
        binding.gridview.setAdapter(categoryAdapter);
    }

    // Örnek kategori verileri oluştur
    private List<Category> createCategoryList() {
        List<Category> categoryList = new ArrayList<>();

        categoryList.add(new Category(1, "Science", R.drawable.science_fictions));
        categoryList.add(new Category(2, "History", R.drawable.history));
        categoryList.add(new Category(3, "Literature", R.drawable.literature));
        categoryList.add(new Category(4, "Academic", R.drawable.academic));
        categoryList.add(new Category(5, "Economy", R.drawable.economy));
        categoryList.add(new Category(6, "Art", R.drawable.art));

        return categoryList;
    }
}
