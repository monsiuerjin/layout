package com.example.layout_main;

public class Product {
    private String name;
    private double price;
    private double oldPrice;
    private int imageResourceId; // ID ảnh từ drawable

    public Product(String name, double price, double oldPrice, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
