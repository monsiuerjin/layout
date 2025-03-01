package com.example.layout_main;

import com.example.layout_main.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static List<Product> cartItems = new ArrayList<>();

    public static List<Product> getCartItems() {
        return cartItems != null ? cartItems : new ArrayList<>();
    }

    public static void addToCart(Product product) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(product);
    }
}

