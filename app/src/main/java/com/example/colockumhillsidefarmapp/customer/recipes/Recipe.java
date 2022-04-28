package com.example.colockumhillsidefarmapp.customer.recipes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.Comparator;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private String imageUrl;
    private String shortDesc;
    private String ingredients;
    private String instructions;

    public Recipe() {}
    public Recipe(int id, String name, String imageUrl, String shortDesc, String ingredients, String instructions){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.ingredients = ingredients;
        this.instructions = instructions;

    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        shortDesc = in.readString();
        ingredients = in.readString();
        instructions = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public static Comparator<Recipe> RecipeNameAZComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe recipe, Recipe t1) {
            return recipe.name.compareTo(t1.name);
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(shortDesc);
        parcel.writeString(ingredients);
        parcel.writeString(instructions);
    }
}
