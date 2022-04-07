package com.example.colockumhillsidefarmapp;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GlobalStorage {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static GlobalStorage instance;

    private ArrayList<Recipe> recipes;
    private ArrayList<Product> localCopyOfAllProducts;

    private ArrayList<Product> editingProducts;

    private GlobalStorage() {
        if(recipes == null) {
            recipes = new ArrayList<>();
        }
        if(editingProducts == null) {
            editingProducts = new ArrayList<>();
            editingProducts.add(new Product(10, "Fish", 12,
                    "https://i.pinimg.com/736x/59/25/60/59256023c47736ad703b7542aec8030f.jpg",
                    "short", "long", 3.99, "lb"));
            editingProducts.add(new Product(11, "Fish", 12,
                    "https://i.pinimg.com/736x/59/25/60/59256023c47736ad703b7542aec8030f.jpg",
                    "short", "long", 3.99, "lb"));
        }

        updateLocalCopyOfAllProducts();

    }

    public static GlobalStorage getInstance() {
        if(instance == null){
            instance = new GlobalStorage();
        }

        return instance;
    }

    public ArrayList<Product> getEditingProducts() {
        return editingProducts;
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

    public ArrayList<Product> getAllProductsForStore(StoreProductRecViewAdapter adapter) {
        updateLocalCopyOfAllProducts();

        ArrayList<Product> allProducts = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.addValueEventListener(new ValueEventListener() {
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

    public Product getProductFromProductIdEditProducts(int productId) {
        for(Product product : editingProducts){
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

    public void addProductToAllProducts(Product product){
        int newId = 0;
        readData(new GetIdCallback(newId, product));
    }

    private void readData(FirebaseCallback firebaseCallback) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("product");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Product> allProducts = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Product newProduct = currentSnapshot.getValue(Product.class);
                    allProducts.add(newProduct);
                }

                firebaseCallback.onCallBack(allProducts);
                Log.d("calling callback", "yes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface FirebaseCallback {
        void onCallBack(ArrayList<Product> allProducts);
    }

    private class GetIdCallback implements FirebaseCallback {

        private int newId;
        private Product productToAdd;

        public GetIdCallback(int newId, Product productToAdd) {
            this.newId = newId;
            this.productToAdd = productToAdd;
        }

        @Override
        public void onCallBack(ArrayList<Product> allProducts) {
            ArrayList<Integer> productIds = new ArrayList<>();
            for (Product currentProduct : allProducts) {
                productIds.add(currentProduct.getId());
            }
            Log.d("product ids", productIds.toString());
            while (true) {
                Log.d("while loop", "yes");
                if(productIds.contains(newId)) {
                    Log.d("new id", "incremented");
                    newId++;
                } else {
                    productToAdd.setId(newId);
                    Log.d("else", "yes");
                    Log.d("newId", String.valueOf(newId));
                    break;
                }
            }

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("product");
            Log.d("adding a product", "now");
            reference.child(String.valueOf(productToAdd.getId())).setValue(productToAdd);
        }
    }

}
