package com.example.colockumhillsidefarmapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Storage {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static Storage instance;

    private FirebaseAuth mAuth;

    private Storage() {

    }

    public static Storage getInstance() {
        if(instance == null){
            instance = new Storage();
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

    /* Accessing transactions */
    public void addTransaction (HashMap<String, Integer> cartWithStringKeys, double totalCost, Date date) {
        //first get current user id
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user = snapshot.getValue(String.class);
                Transaction transactionToAdd = new Transaction(cartWithStringKeys, totalCost, Calendar.getInstance().getTime(), user);

                //add transaction to all transactions
                String transactionId = FirebaseDatabase.getInstance().getReference("transaction").push().getKey();
                FirebaseDatabase.getInstance().getReference("transaction")
                        .child(transactionId).setValue(transactionToAdd);

                //add transaction to specific user's transactions
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("user")
                        .child(userId).child("transactions").child(transactionId).setValue(transactionToAdd);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
