package com.example.colockumhillsidefarmapp;

//import com.example.colockumhillsidefarmapp.ui.Storage;

import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class Favorites {

    private static Favorites instance;
    private ArrayList<Product> favoritesProducts;
    private ArrayList<Recipe> favoritesRecipes;

    private Favorites() {
        if(favoritesProducts == null) {
            favoritesProducts = new ArrayList<>();
        }
        if(favoritesRecipes == null) {
            favoritesRecipes = new ArrayList<>();
        }
    }

    public static Favorites getInstance() {
        if(instance == null){
            instance = new Favorites();
        }
        return instance;
    }

    public ArrayList<Product> getFavoritesProducts() {
        return favoritesProducts;
    }

    public ArrayList<Recipe> getFavoritesRecipes() {
        return favoritesRecipes;
    }

    public void addProductToFavoritesProducts(Product product) {
        if(!favoritesProducts.contains(product)) {
            favoritesProducts.add(product);
        }
    }

    public void addRecipeToFavoritesRecipes(Recipe recipe) {
        if(!favoritesRecipes.contains(recipe)) {
            favoritesRecipes.add(recipe);
        }
    }

    public void removeProductFromFavoritesProducts(Product product) {
        if(favoritesProducts.contains(product)) {
            favoritesProducts.remove(product);
        }
    }

    public void removeRecipeFromFavoritesRecipes(Recipe recipe) {
        if(favoritesRecipes.contains(recipe)) {
            favoritesRecipes.remove(recipe);
        }
    }

}

