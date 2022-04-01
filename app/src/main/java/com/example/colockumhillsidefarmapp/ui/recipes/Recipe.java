package com.example.colockumhillsidefarmapp.ui.recipes;

import android.view.View;

import androidx.appcompat.view.menu.MenuView;

import com.example.colockumhillsidefarmapp.R;

public class Recipe {
    private int id;
    private String name;
    private String shortDesc;
    private String longDesc;
    private String imageUrl;

    public Recipe(int id, String name, String shortDesc, String longDesc, String imageUrl){
        this.id = id;
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.imageUrl = imageUrl;

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

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "RecipeView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                '}';
    }

}
