package com.example.bookcornerapp_java.myInterfaces;

import com.example.bookcornerapp_java.model.Book;

import java.util.List;

public interface CartUpdateListener {
    void onCartUpdated(List<Book> updatedCartItems);
}
