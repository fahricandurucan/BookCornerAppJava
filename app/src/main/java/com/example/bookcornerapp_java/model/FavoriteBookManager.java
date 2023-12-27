package com.example.bookcornerapp_java.model;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBookManager {
    private static List<FavoriteBook> favoriteBooks = new ArrayList<>();

    public static List<FavoriteBook> getFavoriteBooks() {
        return favoriteBooks;
    }

    public static void addFavoriteBook(FavoriteBook favoriteBook) {
        favoriteBooks.add(favoriteBook);
    }

    public static void removeFavoriteBook(FavoriteBook favoriteBook) {
        favoriteBooks.remove(favoriteBook);
    }

    public static boolean isBookFavorited(int bookId) {
        for (FavoriteBook favoriteBook : favoriteBooks) {
            if (favoriteBook.getId() == bookId) {
                return true;
            }
        }
        return false;
    }
}
