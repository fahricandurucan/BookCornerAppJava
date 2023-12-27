package com.example.bookcornerapp_java.model;

import java.io.Serializable;

public class Category implements Serializable {
    int id;
    String name;
    int image;

    public Category(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
