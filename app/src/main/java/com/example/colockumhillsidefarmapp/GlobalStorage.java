package com.example.colockumhillsidefarmapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GlobalStorage {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static GlobalStorage instance;

    private GlobalStorage() {

    }

    public static GlobalStorage getInstance() {
        if(instance == null){
            instance = new GlobalStorage();
        }

        return instance;
    }

    /* Accessing products */
    public ArrayList<Product> getAllProducts(RecyclerView.Adapter adapter) {
        ArrayList<Product> allProducts = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Product newProduct = currentSnapshot.getValue(Product.class);
                    allProducts.add(newProduct);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return allProducts;
    }

    public void editProduct(Product productToEdit, Product editedProduct) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        DatabaseReference productReference = reference.child(String.valueOf(productToEdit.getId()));
        productReference.setValue(editedProduct);
    }

    public void addProduct (Product productToAdd) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Product> allProducts = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Product newProduct = currentSnapshot.getValue(Product.class);
                    allProducts.add(newProduct);
                }
                int newId = getNewProductId(allProducts);
                productToAdd.setId(newId);
                reference.child(String.valueOf(newId)).setValue(productToAdd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getNewProductId(ArrayList<Product> allProducts) {
        int newId = 0;
        ArrayList<Integer> productIds = new ArrayList<>();
        for (Product currentProduct : allProducts) {
            productIds.add(currentProduct.getId());
        }
        while (true) {
            if(productIds.contains(newId)) {
                newId++;
            } else {
                return newId;
            }
        }
    }

    public void removeProduct(Product product) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.child(String.valueOf(product.getId())).removeValue();
    }

    /* Accessing recipes */
    public ArrayList<Recipe> getAllRecipes(RecyclerView.Adapter adapter) {
        ArrayList<Recipe> allRecipes = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("recipe");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Recipe newRecipe = currentSnapshot.getValue(Recipe.class);
                    allRecipes.add(newRecipe);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return allRecipes;
    }

    public void addRecipe (Recipe recipeToAdd) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("recipe");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Recipe> allRecipes = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Recipe newRecipe = currentSnapshot.getValue(Recipe.class);
                    allRecipes.add(newRecipe);
                }
                int newId = getNewRecipeId(allRecipes);
                recipeToAdd.setId(newId);
                reference.child(String.valueOf(newId)).setValue(recipeToAdd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getNewRecipeId(ArrayList<Recipe> allRecipes) {
        int newId = 0;
        ArrayList<Integer> recipeIds = new ArrayList<>();
        for (Recipe currentRecipe : allRecipes) {
            recipeIds.add(currentRecipe.getId());
        }
        while (true) {
            if(recipeIds.contains(newId)) {
                newId++;
            } else {
                return newId;
            }
        }
    }

    public void removeRecipe(Recipe recipe) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("recipe");
        reference.child(String.valueOf(recipe.getId())).removeValue();
    }

    public void editRecipe(Recipe recipeToEdit, Recipe editedRecipe) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("recipe");
        DatabaseReference productReference = reference.child(String.valueOf(recipeToEdit.getId()));
        productReference.setValue(editedRecipe);
    }
}
