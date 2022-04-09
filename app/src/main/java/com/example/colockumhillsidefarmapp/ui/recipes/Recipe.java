package com.example.colockumhillsidefarmapp.ui.recipes;

import android.view.View;

import androidx.appcompat.view.menu.MenuView;

import com.example.colockumhillsidefarmapp.R;

public class Recipe {
    private int id;
    private String name;
    private String imageUrl;
    private String shortDesc;
    private String ingredients;
    private String instructions;


    public Recipe(int id, String name, String imageUrl, String shortDesc, String ingredients, String instructions){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.ingredients = ingredients;
        this.instructions = instructions;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {return instructions;}

    public void setInstructions(String instructions){ this.instructions = instructions;}

    @Override
    public String toString() {
        return "RecipeView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", Ingredients='" + ingredients + '\'' +
                ", Instructions='" + instructions + '\'' +
                '}';
    }

}
