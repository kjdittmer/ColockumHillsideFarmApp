package com.example.colockumhillsidefarmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {

    private static final String SHOPPING_CART = "shopping_cart";

    private class ShoppingCartItem {
        private Product product;
        private int quantity;

        public ShoppingCartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    private static ShoppingCart instance;
    //private HashMap<Product, Integer> cart;
    private SharedPreferences sharedPreferences;

    private ShoppingCart(Context context) {
        sharedPreferences = context.getSharedPreferences("persistent_date", Context.MODE_PRIVATE);

        if(getCart() == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            editor.putString(SHOPPING_CART, gson.toJson(new HashMap<String, Integer>()));
            editor.commit();

            //cart = new HashMap<>();
        }

    }

    public static ShoppingCart getInstance(Context context) {
        if(instance == null){
            instance = new ShoppingCart(context);
        }
        return instance;
    }

    public HashMap<Product, Integer> getCart() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
        HashMap<String, Integer> shoppingCartInStrings = gson.fromJson(sharedPreferences.getString(SHOPPING_CART, null), type);
        HashMap<Product, Integer> shoppingCart = new HashMap<>();
        for (String s : shoppingCartInStrings.keySet()) {
            Product product = gson.fromJson(s, Product.class);
            int quantity = shoppingCartInStrings.get(s);
            shoppingCart.put(product, quantity);
        }
        return shoppingCart;
    }

    public void addProductToCart(Product product, int quantity) {
        HashMap<Product, Integer> shoppingCart = getCart();
        if (shoppingCart != null) {
            shoppingCart.put(product, quantity);
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();

            HashMap<String, Integer> shoppingCartInStrings = new HashMap<>();
            for (Product p : shoppingCart.keySet()) {
                String productString = gson.toJson(product);
                shoppingCartInStrings.put(productString, shoppingCart.get(p));
            }

            editor.remove(SHOPPING_CART);
            editor.putString(SHOPPING_CART, gson.toJson(shoppingCartInStrings));
            editor.commit();
        }

//        int productId = product.getId();
//        for (Product currentProduct : cart.keySet()) {
//            if (productId == currentProduct.getId()) {
//                cart.put(currentProduct, cart.get(currentProduct) + quantity);
//                return;
//            }
//        }
//        cart.put(product, quantity);
    }

    public void setProductQuantity(Product product, int quantity) {
//        int productId = product.getId();
//        for (Product currentProduct : cart.keySet()) {
//            if (productId == currentProduct.getId()) {
//                cart.put(currentProduct, quantity);
//                return;
//            }
//        }
    }

    public void removeProductFromCart(Product product) {
        HashMap<Product, Integer> shoppingCart = getCart();
        if (shoppingCart != null) {
            for (Product currentProduct : shoppingCart.keySet()) {
                if (currentProduct.getId() == product.getId()) {
                    shoppingCart.remove(currentProduct);
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    HashMap<String, Integer> shoppingCartInStrings = new HashMap<>();
                    for (Product p : shoppingCart.keySet()) {
                        String productString = gson.toJson(product);
                        shoppingCartInStrings.put(productString, shoppingCart.get(p));
                    }
                    editor.remove(SHOPPING_CART);
                    editor.putString(SHOPPING_CART, gson.toJson(shoppingCartInStrings));
                    editor.commit();

                }
            }
        }

//        if(cart.containsKey(product)) {
//            cart.remove(product);
//        }
    }

}
