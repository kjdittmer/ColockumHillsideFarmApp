package com.example.colockumhillsidefarmapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    private static ShoppingCart instance;
    private HashMap<Product, Integer> cart;

    private ShoppingCart() {
        if(cart == null) {
            cart = new HashMap<>();
        }

    }

    public static ShoppingCart getInstance() {
        if(instance == null){
            instance = new ShoppingCart();
        }
        return instance;
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }

    public void addProductToCart(Product product, int quantity) {
        Log.d("Product:", product.toString());
        Log.d("cart", cart.toString());
        int productId = product.getId();
        for (Product currentProduct : cart.keySet()) {
            if (productId == currentProduct.getId()) {
                cart.put(currentProduct, cart.get(currentProduct) + quantity);
                return;
            }
        }
        cart.put(product, quantity);
    }

    public void removeProductFromCart(Product product) {
        if(cart.containsKey(product)) {
            cart.remove(product);
        }
    }

}
