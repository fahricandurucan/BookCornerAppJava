package com.example.bookcornerapp_java.model;

import java.io.Serializable;

public class Book implements Serializable {
    String id;
    String name;
    double price;
    String author;
    String publisher;
    String description;
    int image;


    public Book() {
        // Boş kurucu metod
    }
    public Book(String id, String name, double price, String author, String publisher, String description, int image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public void setId(String documentId) {
        id = documentId;
    }
}
