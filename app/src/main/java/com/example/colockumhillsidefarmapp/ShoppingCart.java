package com.example.colockumhillsidefarmapp;

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
        if(!cart.containsKey(product)) {
            cart.put(product, quantity);
        } else {
            cart.put(product, quantity + cart.get(product));
        }
    }

    public void removeProductFromCart(Product product) {
        if(cart.containsKey(product)) {
            cart.remove(product);
        }
    }

}
