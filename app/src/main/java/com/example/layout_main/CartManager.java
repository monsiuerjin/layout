package com.example.layout_main;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static List<CartItem> cartItems = new ArrayList<>();

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void addToCart(CartItem product) {
        for (CartItem item : cartItems) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(product);
    }

    public static void removeFromCart(CartItem product) {
        cartItems.remove(product);
    }

    public static void updateQuantity(CartItem product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
