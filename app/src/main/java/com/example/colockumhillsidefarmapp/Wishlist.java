package com.example.colockumhillsidefarmapp;

//import com.example.colockumhillsidefarmapp.ui.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class Wishlist {

    private static Wishlist instance;
    private ArrayList<Product> wishlist;

    private Wishlist() {
        if(wishlist == null) {
            wishlist = new ArrayList<>();
        }

    }

    public static Wishlist getInstance() {
        if(instance == null){
            instance = new Wishlist();
        }
        return instance;
    }

    public ArrayList<Product> getWishlist() {

        return wishlist;
    }

    public void addProductToWishlist(Product product) {
        if(!wishlist.contains(product)) {
            wishlist.add(product);
        }
    }

    public void removeProductFromWishList(Product product) {
        if(!wishlist.contains(product)) {
            wishlist.remove(product);
        }
    }

}
