package com.example.colockumhillsidefarmapp;

//import com.example.colockumhillsidefarmapp.ui.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Favorites {

    private static final String FAVORITES_PRODUCTS = "favorites_products";
    private static final String FAVORITES_RECIPES = "favorites_recipes";
    private SharedPreferences sharedPreferences;
    private static Favorites instance;
    private ArrayList<Product> favoritesProducts;
    private ArrayList<Recipe> favoritesRecipes;

    private Favorites(Context context) {
        sharedPreferences = context.getSharedPreferences("persistent_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(getFavoritesProducts() == null) {
            editor.putString(FAVORITES_PRODUCTS, gson.toJson(new ArrayList<Product>()));
            editor.commit();
        }
        if(getFavoritesRecipes() == null) {
            editor.putString(FAVORITES_RECIPES, gson.toJson(new ArrayList<Recipe>()));
            editor.commit();
        }
    }

    public static Favorites getInstance(Context context) {
        if(instance == null){
            instance = new Favorites(context);
        }
        return instance;
    }

    public ArrayList<Product> getFavoritesProducts() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
        ArrayList<Product> favoritesProducts = gson.fromJson(sharedPreferences.getString(FAVORITES_PRODUCTS, null), type);
        return favoritesProducts;
    }

    public ArrayList<Recipe> getFavoritesRecipes() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Recipe>>(){}.getType();
        ArrayList<Recipe> favoritesRecipes = gson.fromJson(sharedPreferences.getString(FAVORITES_RECIPES, null), type);
        return favoritesRecipes;
    }

    public void addProductToFavoritesProducts(Product product) {
        ArrayList<Product> favoritesProducts = getFavoritesProducts();
        if (favoritesProducts != null) {
            for (Product p : favoritesProducts) {
                if (p.getId() == product.getId()) {
                    return;
                }
            }
            favoritesProducts.add(product);
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(FAVORITES_PRODUCTS);
            editor.putString(FAVORITES_PRODUCTS, gson.toJson(favoritesProducts));
            editor.commit();
        }
    }

    public void addRecipeToFavoritesRecipes(Recipe recipe) {
        ArrayList<Recipe> favoritesRecipes = getFavoritesRecipes();
        if (favoritesRecipes != null) {
            for (Recipe r : favoritesRecipes) {
                if (r.getId() == recipe.getId()) {
                    return;
                }
            }
            favoritesRecipes.add(recipe);
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(FAVORITES_RECIPES);
            editor.putString(FAVORITES_RECIPES, gson.toJson(favoritesRecipes));
            editor.commit();
        }
    }

    public void removeProductFromFavoritesProducts(Product product) {
        ArrayList<Product> favoritesProducts = getFavoritesProducts();
        if (favoritesProducts != null) {
            for (Product p : favoritesProducts) {
                if (p.getId() == product.getId()) {
                    favoritesProducts.remove(p);
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(FAVORITES_PRODUCTS);
                    editor.putString(FAVORITES_PRODUCTS, gson.toJson(favoritesProducts));
                    editor.commit();
                    return;
                }
            }
        }
    }

    public void removeRecipeFromFavoritesRecipes(Recipe recipe) {
        ArrayList<Recipe> favoritesRecipes = getFavoritesRecipes();
        if (favoritesRecipes != null) {
            for (Recipe r : favoritesRecipes) {
                if (r.getId() == recipe.getId()) {
                    favoritesRecipes.remove(r);
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(FAVORITES_RECIPES);
                    editor.putString(FAVORITES_RECIPES, gson.toJson(favoritesRecipes));
                    editor.commit();
                    return;
                }
            }
        }
    }

}

