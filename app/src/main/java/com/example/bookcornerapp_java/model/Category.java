package com.example.bookcornerapp_java.model;

import java.io.Serializable;

public class Category implements Serializable {
    String id;
    String name;
    int image;

    public Category() {
        // Boş kurucu metod
    }
    public Category(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public void setId(String documentId) {
        id = documentId;
    }
}
