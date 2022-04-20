package com.example.colockumhillsidefarmapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.user.CustomerLoginActivity;
import com.example.colockumhillsidefarmapp.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class GlobalStorage {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static GlobalStorage instance;

    private FirebaseAuth mAuth;

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

    /* Accessing users */
    public void signInCustomer(String email, String password, Context mContext) {
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            User.getInstance().setCustomer(user.getEmail());
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
    }
}
