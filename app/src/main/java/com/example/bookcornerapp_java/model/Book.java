package com.example.bookcornerapp_java.model;

import java.io.Serializable;

public class Book implements Serializable {
    String id;
    String name;
    double price;
    String author;
    String publisher;
    String description;
    String image;
    String category;

    public Book() {
        // Boş kurucu metod
    }
    public Book(String id, String name, double price, String author, String publisher,
                String description, String image,String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.image = image;
        this.category = category;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public String getCategory(){return category;}

    public void setId(String documentId) {
        id = documentId;
    }

}
