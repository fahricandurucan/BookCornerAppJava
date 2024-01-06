package com.example.bookcornerapp_java;

import com.example.bookcornerapp_java.model.Book;

import java.util.List;

public interface OnBooksLoadedListener {
    void onBooksLoaded(List<Book> bookList);
    void onBooksLoadFailed(String errorMessage);
}
