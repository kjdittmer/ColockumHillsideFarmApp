package com.example.colockumhillsidefarmapp;

//import com.example.colockumhillsidefarmapp.ui.Storage;

import java.util.HashMap;

public class Wishlist {

    private static Wishlist instance;
    private HashMap<Product, Integer> wishlist;

    private Wishlist() {
        if(wishlist == null) {
            wishlist = new HashMap<>();
        }

    }

    public static Wishlist getInstance() {
        if(instance == null){
            instance = new Wishlist();
        }
        return instance;
    }

    public HashMap<Product, Integer> getWishlist() {

        return wishlist;
    }

    public void addProductToWishlist(Product product, int quantity) {
        if(!wishlist.containsKey(product)) {
            wishlist.put(product, quantity);
        } else {
            wishlist.put(product, quantity + wishlist.get(product));
        }
    }

}
