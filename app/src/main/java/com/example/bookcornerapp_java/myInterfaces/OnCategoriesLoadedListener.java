package com.example.bookcornerapp_java.myInterfaces;

import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.Category;

import java.util.List;

public interface OnCategoriesLoadedListener {

    void onCategoriesLoaded(List<Category> bookList);
    void onCategoriesLoadFailed(String errorMessage);
}
