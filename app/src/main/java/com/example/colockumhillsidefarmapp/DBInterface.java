package com.example.colockumhillsidefarmapp;

import android.app.Activity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCart;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartActivity;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartItem;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBInterface {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static DBInterface instance;

    private FirebaseAuth mAuth;

    private DBInterface() {

    }

    public static DBInterface getInstance() {
        if(instance == null){
            instance = new DBInterface();
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
    public void addTransaction (Product product, int quantity, double cost, Date date) {
        //first get current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("user").child(userId).child("email");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user = snapshot.getValue(String.class);
                Transaction transactionToAdd = new Transaction(product, quantity, cost*quantity, date, user);

                //add transaction to all transactions
                String transactionId = FirebaseDatabase.getInstance().getReference("transaction").push().getKey();
                FirebaseDatabase.getInstance().getReference("transaction")
                        .child(transactionId).setValue(transactionToAdd);

                //add transaction to specific user's transactions
                //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("user")
                        .child(userId).child("transactions").child(transactionId).setValue(transactionToAdd);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public ArrayList<Transaction> getAllTransactions(RecyclerView.Adapter adapter) {
        ArrayList<Transaction> allTransactions = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Transaction newTransaction = currentSnapshot.getValue(Transaction.class);
                    allTransactions.add(newTransaction);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return allTransactions;
    }

    public ArrayList<Transaction> getCustomerTransactions(RecyclerView.Adapter adapter) {
        ArrayList<Transaction> allTransactions = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("transactions");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Transaction newTransaction = currentSnapshot.getValue(Transaction.class);
                    allTransactions.add(newTransaction);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return allTransactions;
    }

    /* Accessing wishlist*/
    public ArrayList<Product> getWishlist(RecyclerView.Adapter adapter) {
        ArrayList<Product> allProducts = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wishlist");
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

    public void addProductToWishlist (Product product) {
        //first get current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //add transaction to specific user's transactions
        FirebaseDatabase.getInstance().getReference("user")
                .child(userId).child("wishlist").child(String.valueOf(product.getId())).setValue(product);
    }

    public void removeProductFromWishlist (Product product) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(userId).child("wishlist");
        reference.child(String.valueOf(product.getId())).removeValue();
    }

    /* Accessing favorite products */
    public ArrayList<Product> getFavoriteProducts(RecyclerView.Adapter adapter) {
        ArrayList<Product> allProducts = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favoriteProducts");
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

    public void addProductToFavoriteProducts (Product product) {
        //first get current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //add transaction to specific user's transactions
        FirebaseDatabase.getInstance().getReference("user")
                .child(userId).child("favoriteProducts").child(String.valueOf(product.getId())).setValue(product);
    }

    public void removeProductFromFavoriteProducts (Product product) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(userId).child("favoriteProducts");
        reference.child(String.valueOf(product.getId())).removeValue();
    }

    /* Accessing favorite recipes */
    public ArrayList<Recipe> getFavoriteRecipes(RecyclerView.Adapter adapter) {
        ArrayList<Recipe> allRecipes = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favoriteRecipes");
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

    public void addRecipeToFavoriteRecipes (Recipe recipe) {
        //first get current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //add transaction to specific user's transactions
        FirebaseDatabase.getInstance().getReference("user")
                .child(userId).child("favoriteRecipes").child(String.valueOf(recipe.getId())).setValue(recipe);
    }

    public void removeRecipeFromFavoriteRecipes (Recipe recipe) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(userId).child("favoriteRecipes");
        reference.child(String.valueOf(recipe.getId())).removeValue();
    }

    /* Accessing shopping cart */
    public ArrayList<ShoppingCartItem> getShoppingCart(RecyclerView.Adapter adapter) {
        ArrayList<ShoppingCartItem> shoppingCart = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("shoppingCart");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    ShoppingCartItem newShoppingCartItem = currentSnapshot.getValue(ShoppingCartItem.class);
                    shoppingCart.add(newShoppingCartItem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return shoppingCart;
    }

    public void addProductToShoppingCart (ShoppingCartItem shoppingCartItem) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("user")
                .child(userId).child("shoppingCart").child(String.valueOf(shoppingCartItem.getProduct().getId())).setValue(shoppingCartItem);
    }

    public void removeProductFromShoppingCart (ShoppingCartItem shoppingCartItem) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(userId).child("shoppingCart");
        reference.child(String.valueOf(shoppingCartItem.getProduct().getId())).removeValue();
    }

    public void clearShoppingCart () {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(userId).child("shoppingCart");
        reference.removeValue();
    }

    public void updateQuantity (ShoppingCartItem shoppingCartItem, int newQuantity, ShoppingCartActivity currentActivity) {
        ShoppingCartItem updatedItem = new ShoppingCartItem(shoppingCartItem.getProduct(), newQuantity);
        addProductToShoppingCart(updatedItem);
        currentActivity.reload();
    }

    public void setTotalCost(TextView textView) {
        double totalCost = 0;
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("shoppingCart");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double totalCost = 0;
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    ShoppingCartItem newShoppingCartItem = currentSnapshot.getValue(ShoppingCartItem.class);
                    totalCost += newShoppingCartItem.getQuantity() * newShoppingCartItem.getProduct().getPrice();
                }
                DecimalFormat df = new DecimalFormat("0.00");
                textView.setText("Total: $" + df.format(totalCost));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
