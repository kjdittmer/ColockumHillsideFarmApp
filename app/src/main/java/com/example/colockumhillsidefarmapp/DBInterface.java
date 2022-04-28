package com.example.colockumhillsidefarmapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.customer.about_us.Info;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartActivity;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartItem;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.user.CustomerLoginActivity;
import com.example.colockumhillsidefarmapp.user.NewCustomerActivity;
import com.example.colockumhillsidefarmapp.user.User;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class DBInterface {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static DBInterface instance;

    private FirebaseAuth mAuth;

    public static String CURRENT_USER;

    private DBInterface() {

    }

    public static DBInterface getInstance() {
        if(instance == null){
            instance = new DBInterface();
        }
        return instance;
    }

    /* Accessing Users */
    public void registerUser (String fullName, String age, String email,
                              String password, String reenterPassword,
                              ProgressBar progressBar, Context context) {
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(fullName, age, email,
                                    new ArrayList<Product>(),
                                    new ArrayList<Recipe>(),
                                    new ArrayList<Product>(),
                                    new ArrayList<ShoppingCartItem>(),
                                    new ArrayList<Transaction>()
                            );
                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "You have been registered!",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                        context.startActivity(new Intent(context, CustomerLoginActivity.class));
                                    } else {
                                        Toast.makeText(context, "Failed to register! Try again.",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(context, "Failed to register! Try again.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loginCustomer (String email, String password, ProgressBar progressBar, Context context) {
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    CURRENT_USER = email;
                    context.startActivity(new Intent(context, CustomerDashboardActivity.class));
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context,
                            "Failed to login!",
                            Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void loginVendor (String email, String password, ProgressBar progressBar, Context context) {
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    CURRENT_USER = email;
                    context.startActivity(new Intent(context, VendorDashboardActivity.class));
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context,
                            "Failed to login!",
                            Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
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
                Collections.sort(allProducts, Product.ProductNameAZComparator);
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

    public void decreaseProductQuantity(ArrayList<ShoppingCartItem> shoppingCart) {
        for (ShoppingCartItem shoppingCartItem : shoppingCart) {
            Product newProduct = shoppingCartItem.getProduct();
            int quantitySold = shoppingCartItem.getQuantity();
            //get product
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("product");
            reference.child(String.valueOf(newProduct.getId())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Product oldProduct = snapshot.getValue(Product.class);
                    int newQuantity = oldProduct.getQuantity() - quantitySold;
                    if (newQuantity <= 0) {
                        reference.child(String.valueOf(oldProduct.getId())).removeValue();
                    } else {
                        oldProduct.setQuantity(newQuantity);
                        reference.child(String.valueOf(oldProduct.getId())).setValue(oldProduct);
                    }
                    clearShoppingCart();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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
                Collections.sort(allRecipes, Recipe.RecipeNameAZComparator);
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

    public void addTransactions (ArrayList<ShoppingCartItem> shoppingCartItems) {

        for (ShoppingCartItem shoppingCartItem : shoppingCartItems){
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("user").child(userId).child("email");
            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String user = snapshot.getValue(String.class);
                    Product product = shoppingCartItem.getProduct();
                    int quantity = shoppingCartItem.getQuantity();
                    double price = product.getPrice() * quantity;
                    Date time = Calendar.getInstance().getTime();

                    Transaction transactionToAdd = new Transaction(product, quantity, price, time, user);

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
                Collections.sort(allTransactions, Transaction.TransactionDateDescendingComparator);
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
                Collections.sort(allTransactions, Transaction.TransactionDateDescendingComparator);
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
                Collections.sort(allProducts, Product.ProductNameAZComparator);
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
                Collections.sort(allProducts, Product.ProductNameAZComparator);
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
                Collections.sort(allRecipes, Recipe.RecipeNameAZComparator);
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
                Collections.sort(shoppingCart, ShoppingCartItem.ShoppingCartItemNameAZComparator);
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

    public void clearShoppingCart ( ) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child(userId).child("shoppingCart");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public ArrayList<Info> getAllInfo(RecyclerView.Adapter adapter) {
        ArrayList<Info> allInfo = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("info");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Info newInfo = currentSnapshot.getValue(Info.class);
                    allInfo.add(newInfo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return allInfo;
    }
    public void addInfo (Info infoToAdd) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("info");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Info> allInfo = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Info newInfo = currentSnapshot.getValue(Info.class);
                    allInfo.add(newInfo);
                }
                int newId = getNewInfoId(allInfo);
                infoToAdd.setId(newId);
                reference.child(String.valueOf(newId)).setValue(infoToAdd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getNewInfoId(ArrayList<Info> allInfo) {
        int newId = 0;
        ArrayList<Integer> infoIds = new ArrayList<>();
        for (Info currentInfo : allInfo) {
            infoIds.add(currentInfo.getId());
        }
        while (true) {
            if(infoIds.contains(newId)) {
                newId++;
            } else {
                return newId;
            }
        }
    }
    public void removeInfo(Info info) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("info");
        reference.child(String.valueOf(info.getId())).removeValue();
    }

    public void editInfo(Info infoToEdit, Info editedInfo) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("info");
        DatabaseReference productReference = reference.child(String.valueOf(infoToEdit.getId()));
        productReference.setValue(editedInfo);
    }
}
