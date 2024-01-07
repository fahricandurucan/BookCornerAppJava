package com.example.bookcornerapp_java;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        firestoreManager = new FirestoreManager();

        // Firestore'dan kategorileri getir
        firestoreManager.getCategories(new OnCategoriesLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> categoryList) {
                // Kategorileri göster
                CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categoryList);
                binding.gridview.setAdapter(categoryAdapter);
            }

            @Override
            public void onCategoriesLoadFailed(String errorMessage) {
                // Kategorileri yüklerken hata durumunu ele alın
                Toast.makeText(CategoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

//        // Kategorileri oluştur
//        List<Category> categoryList = createCategoryList();
//
//        // Kategorileri sırala (opsiyonel, isteğe bağlı)
//        Collections.sort(categoryList, (category1, category2) -> category1.getName().compareTo(category2.getName()));
//
//        // Kategorileri göster
//        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categoryList);
//        binding.gridview.setAdapter(categoryAdapter);

//        Button addCategory = findViewById(R.id.categoryAdd);
//        addCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firestoreManager.addCategory(new Category("6", "Art", R.drawable.art));
//            }
//        });
    }

    // Örnek kategori verileri oluştur
//    private List<Category> createCategoryList() {
//        List<Category> categoryList = new ArrayList<>();
//
//        categoryList.add(new Category("1", "Science", R.drawable.science_fictions));
//        categoryList.add(new Category("2", "History", R.drawable.history));
//        categoryList.add(new Category("3", "Literature", R.drawable.literature));
//        categoryList.add(new Category("4", "Academic", R.drawable.academic));
//        categoryList.add(new Category("5", "Economy", R.drawable.economy));
//        categoryList.add(new Category("6", "Art", R.drawable.art));
//
//        return categoryList;
//    }
}
