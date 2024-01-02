package com.example.bookcornerapp_java.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bookcornerapp_java.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// SharedPreferencesHelper.java
public class SharedPreferencesHelper {
    private static final String PREF_NAME = "MyCartPreferences";
    private static final String KEY_CART_ITEMS = "cartItems";

    public static void saveCartItems(Context context, List<Book> cartItems) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(KEY_CART_ITEMS, json);
        editor.apply();
    }

    public static List<Book> getCartItems(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = preferences.getString(KEY_CART_ITEMS, null);

        if (json != null && !json.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Book>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    public static void clearCart(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(KEY_CART_ITEMS);
        editor.apply();
    }


}

