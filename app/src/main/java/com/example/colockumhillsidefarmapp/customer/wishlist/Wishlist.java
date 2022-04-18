package com.example.colockumhillsidefarmapp.customer.wishlist;

//import com.example.colockumhillsidefarmapp.ui.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Wishlist {

    private static final String WISHLIST = "wishlist";
    private SharedPreferences sharedPreferences;
    private static Wishlist instance;

    private Wishlist(Context context) {
        sharedPreferences = context.getSharedPreferences("persistent_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(getWishlist() == null) {
            editor.putString(WISHLIST, gson.toJson(new ArrayList<Product>()));
            editor.commit();
        }
    }

    public static Wishlist getInstance(Context context) {
        if(instance == null){
            instance = new Wishlist(context);
        }
        return instance;
    }

    public ArrayList<Product> getWishlist() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
        ArrayList<Product> wishlist = gson.fromJson(sharedPreferences.getString(WISHLIST, null), type);
        return wishlist;
    }

    public void addProductToWishlist(Product product) {
        ArrayList<Product> wishlist = getWishlist();
        if (wishlist != null) {
            for (Product p : wishlist) {
                if (p.getId() == product.getId()) {
                    return;
                }
            }
            wishlist.add(product);
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(WISHLIST);
            editor.putString(WISHLIST, gson.toJson(wishlist));
            editor.commit();
        }
    }

    public void removeProductFromWishList(Product product) {
        ArrayList<Product> wishlist = getWishlist();
        if (wishlist != null) {
            for (Product p : wishlist) {
                if (p.getId() == product.getId()) {
                    wishlist.remove(p);
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(WISHLIST);
                    editor.putString(WISHLIST, gson.toJson(wishlist));
                    editor.commit();
                    return;
                }
            }
        }
    }

}
