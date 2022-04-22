package com.example.colockumhillsidefarmapp.user;

import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.ArrayList;

public class User {

    private String fullName, age, email;
    private ArrayList<Product> wishlist;
    private ArrayList<Recipe> favoriteRecipes;
    private ArrayList<Product> favoriteProducts;
    private ArrayList<Product> shoppingCart;

    public User() {

    }

    public User(String fullName,
                String age,
                String email,
                ArrayList<Product> wishlist,
                ArrayList<Recipe> favoriteRecipes,
                ArrayList<Product> favoriteProducts,
                ArrayList<Product> shoppingCart
    ) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.wishlist = wishlist;
        this.favoriteProducts = favoriteProducts;
        this.favoriteRecipes = favoriteRecipes;
        this.shoppingCart = shoppingCart;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Product> getWishlist() {
        return wishlist;
    }

    public void setWishlist(ArrayList<Product> wishlist) {
        this.wishlist = wishlist;
    }

    public ArrayList<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(ArrayList<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public ArrayList<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(ArrayList<Product> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
