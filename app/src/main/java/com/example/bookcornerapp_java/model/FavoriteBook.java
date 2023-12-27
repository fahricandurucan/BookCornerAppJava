package com.example.bookcornerapp_java.model;

public class FavoriteBook {
    private int id;
    private String name;
    private double price;
    private String publisher;
    private String description;
    private int image;

    public FavoriteBook(int id, String name, double price, String publisher, String description, int image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.publisher = publisher;
        this.description = description;
        this.image = image;
    }

    public int getId() {
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

    public int getImage() {
        return image;
    }
}
