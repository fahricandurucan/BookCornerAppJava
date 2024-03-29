package com.example.bookcornerapp_java.model;

public class FavoriteBook {
    private String id;
    private String name;
    private double price;
    private String publisher;
    private String description;
    private String image;

    public FavoriteBook(String id, String name, double price, String publisher, String description, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
