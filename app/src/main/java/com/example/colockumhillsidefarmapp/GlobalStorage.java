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

    private Product nextProductToAdd;

    private GlobalStorage() {
        if(recipes == null) {
            recipes = new ArrayList<>();
        }

        //initData();
    }

//    private void initData() {
//        allProducts.add(
//                new Product(1, "Pork Chop", 10, "https://atlas-content-cdn.pixelsquid.com/stock-images/pork-chop-raw-chops-ZemwxrE-600.jpg",
//                        "A tasty pork chop", "Long description", 4.99, "lb")
//        );
//        allProducts.add(
//                new Product(2, "Chicken Breast", 10, "https://media01.stockfood.com/largepreviews/MzQ3NjQ3MjA2/11214426-A-Single-Raw-Chicken-Breast-on-a-White-Background.jpg",
//                        "A yummy chicken breast", "Long description", 5.99, "lb")
//        );
//        allProducts.add(
//                new Product(3, "Beef Tenderloin", 10, "https://st2.depositphotos.com/1625039/11858/i/600/depositphotos_118582494-stock-photo-raw-beef-tenderloin.jpg",
//                        "A scrumptious beef tenderloin", "Long description", 4.99, "lb")
//        );
//
//        allProducts.add(
//                new Product(4, "Eggs", 10, "https://www.humphreysfarm.com/productcart/pc/catalog/5514-lg.jpg",
//                        "A nourishing breakfast", "Long description", 2.99, "dozen")
//        );
//        allProducts.add(
//                new Product(5, "Tomato", 10, "https://st.depositphotos.com/1642482/2529/i/600/depositphotos_25296371-stock-photo-red-tomato.jpg",
//                        "An acidic treat", "Long description", 3.99, "lb")
//        );
//        allProducts.add(
//                new Product(6, "Apple", 10, "https://cdn.britannica.com/14/77414-004-30B131EC/Apple.jpg",
//                        "A juicy delight", "Long description", 3.50, "lb")
//        );
//        allProducts.add(
//                new Product(7, "Banana", 10, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Banana_%281%29.jpg/1200px-Banana_%281%29.jpg",
//                        "An energizing snack", "Long description", 2.99, "lb")
//        );
//    }

    public static GlobalStorage getInstance() {
        if(instance == null){
            instance = new GlobalStorage();
        }
        return instance;
    }


    public ArrayList<Product> getAllProductsForStore(StoreProductRecViewAdapter adapter) {
        //TODO: get allProducts from DB
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

    public ArrayList<Product> getAllProducts() {
        //TODO: get allProducts from DB
        ArrayList<Product> allProducts = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    Product newProduct = currentSnapshot.getValue(Product.class);
                    allProducts.add(newProduct);
                    Log.d("product", newProduct.toString());
                }
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
        ArrayList<Product> allProducts = getAllProducts();
        for(Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }

        return null;
    }

    public void addRecipeToRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

    public void addProductToAllProducts(Product product){
        nextProductToAdd = product;
        int newId = 0;
        readData(new GetIdCallback(newId));
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

        public GetIdCallback(int newId) {
            this.newId = newId;
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
                    nextProductToAdd.setId(newId);
                    Log.d("else", "yes");
                    Log.d("newId", String.valueOf(newId));
                    break;
                }
            }

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("product");
            Log.d("adding a product", "now");
            reference.child(String.valueOf(nextProductToAdd.getId())).setValue(nextProductToAdd);
        }
    }

}
