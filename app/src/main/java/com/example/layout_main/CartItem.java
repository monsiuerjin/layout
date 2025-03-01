package com.example.layout_main;

public class CartItem {
    private String name;
    private int price; // Chuyển price thành int để dễ tính toán
    private int imageResourceId;
    private int quantity; // Thêm số lượng sản phẩm

    public CartItem(String name, int price, int imageResourceId, int quantity) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.quantity = quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }
}
