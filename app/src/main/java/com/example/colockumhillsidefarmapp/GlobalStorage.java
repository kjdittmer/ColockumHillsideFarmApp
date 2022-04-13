package com.example.colockumhillsidefarmapp;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;
import com.example.colockumhillsidefarmapp.ui.vendor.EditStoreProductRecViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GlobalStorage {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static GlobalStorage instance;

    private ArrayList<Recipe> recipes;
    private ArrayList<Product> localCopyOfAllProducts;

    private GlobalStorage() {
        if(recipes == null) {
            recipes = new ArrayList<>();
        }

        updateLocalCopyOfAllProducts();
    }

    public static GlobalStorage getInstance() {
        if(instance == null){
            instance = new GlobalStorage();
        }

        return instance;
    }

    public void updateLocalCopyOfAllProducts () {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                localCopyOfAllProducts = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Product newProduct = currentSnapshot.getValue(Product.class);
                    localCopyOfAllProducts.add(newProduct);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<Product> getAllProducts(RecyclerView.Adapter adapter) {
        updateLocalCopyOfAllProducts();

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

    public ArrayList<Recipe> getRecipes() {
        //TODO: get recipeList

        return recipes;
    }

    public Product getProductFromProductId(int productId) {
        for(Product product : localCopyOfAllProducts){
            if (product.getId() == productId){
                return product;
            }
        }

        updateLocalCopyOfAllProducts();
        return null;
    }

    public void addRecipeToRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

    public void editProduct(Product productToEdit, Product editedProduct) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("product");
        DatabaseReference productReference = reference.child(String.valueOf(productToEdit.getId()));
        productReference.setValue(editedProduct);
    }

    public void addProduct (Product productToAdd) {
        //get new ID
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
                int newId = getNewId(allProducts);
                productToAdd.setId(newId);
                reference.child(String.valueOf(newId)).setValue(productToAdd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getNewId (ArrayList<Product> allProducts) {
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
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("product");
        reference.child(String.valueOf(product.getId())).removeValue();
    }

    public void addRecipeToAllRecipes(Recipe recipe){
        int newId = 0;
        readRecipeData(new AddRecipeCallback(newId, recipe));
    }

    private void readRecipeData(FirebaseRecipeCallback firebaseRecipeCallback) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("recipe");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Recipe> allRecipes = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Recipe newRecipe = currentSnapshot.getValue(Recipe.class);
                    allRecipes.add(newRecipe);
                }

                firebaseRecipeCallback.onCallBack(allRecipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public Recipe getRecipeFromRecipeId(int recipeId) {
        for(Recipe recipe : recipes){
            if (recipe.getId() == recipeId){
                return recipe;
            }
        }

        updateLocalCopyOfAllProducts();
        return null;
    }


    private interface FirebaseRecipeCallback{
        void onCallBack(ArrayList<Recipe> allRecipes);
    }

    private class AddRecipeCallback implements FirebaseRecipeCallback {

        private int newId;
        private Recipe recipeToAdd;

        public AddRecipeCallback(int newId, Recipe recipeToAdd) {
            this.newId = newId;
            this.recipeToAdd = recipeToAdd;
        }

        @Override
        public void onCallBack(ArrayList<Recipe> allRecipes) {
            ArrayList<Integer> recipeIds = new ArrayList<>();
            for (Recipe currentRecipe : allRecipes) {
                recipeIds.add(currentRecipe.getId());
            }
            Log.d("recipe ids", recipeIds.toString());
            while (true) {
                Log.d("while loop", "yes");
                if(recipeIds.contains(newId)) {
                    Log.d("new id", "incremented");
                    newId++;
                } else {
                    recipeToAdd.setId(newId);
                    Log.d("else", "yes");
                    Log.d("newId", String.valueOf(newId));
                    break;
                }
            }

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("recipe");
            Log.d("adding a product", "now");
            reference.child(String.valueOf(recipeToAdd.getId())).setValue(recipeToAdd);
        }
    }

}
