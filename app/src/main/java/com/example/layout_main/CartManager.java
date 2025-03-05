package com.example.layout_main;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String CART_PREF = "CartPrefs";
    private static final String CART_KEY = "CartItems";
    private static List<CartItem> cartItems = new ArrayList<>();
    private static final Gson gson = new Gson();

    public static void loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CART_PREF, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        cartItems = json != null ? deserializeCart(json) : new ArrayList<>();
    }

    public static void saveCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CART_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CART_KEY, serializeCart(cartItems));
        editor.apply();
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void addToCart(Context context, CartItem product) {
        for (CartItem item : cartItems) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                saveCart(context);
                return;
            }
        }
        cartItems.add(product);
        saveCart(context);
    }

    public static void removeFromCart(Context context, CartItem product) {
        cartItems.remove(product);
        saveCart(context);
    }

    public static void updateQuantity(Context context, CartItem product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(quantity);
                saveCart(context);
                return;
            }
        }
    }

    public static void clearCart(Context context) {
        cartItems.clear();
        saveCart(context);
    }

    // Chuyển danh sách CartItem thành chuỗi JSON
    public static String serializeCart(List<CartItem> cartItems) {
        return gson.toJson(cartItems);
    }

    // Chuyển chuỗi JSON thành danh sách CartItem
    public static List<CartItem> deserializeCart(String json) {
        Type listType = new TypeToken<List<CartItem>>() {}.getType();
        return gson.fromJson(json, listType);
    }
}
