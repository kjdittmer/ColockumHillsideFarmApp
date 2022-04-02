package com.example.colockumhillsidefarmapp;

import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GlobalStorage {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private static GlobalStorage instance;
    private ArrayList<Product> allProducts;
    private ArrayList<Recipe> recipes;

    private GlobalStorage() {
        if(allProducts == null) {
            allProducts = new ArrayList<>();
        }
        if(recipes == null) {
            recipes = new ArrayList<>();
        }

        initData();
    }

    private void initData() {
        allProducts.add(
                new Product(1, "Pork Chop", 10, "https://atlas-content-cdn.pixelsquid.com/stock-images/pork-chop-raw-chops-ZemwxrE-600.jpg",
                        "A tasty pork chop", "Long description", 4.99, "lb")
        );
        allProducts.add(
                new Product(2, "Chicken Breast", 10, "https://media01.stockfood.com/largepreviews/MzQ3NjQ3MjA2/11214426-A-Single-Raw-Chicken-Breast-on-a-White-Background.jpg",
                        "A yummy chicken breast", "Long description", 5.99, "lb")
        );
        allProducts.add(
                new Product(3, "Beef Tenderloin", 10, "https://st2.depositphotos.com/1625039/11858/i/600/depositphotos_118582494-stock-photo-raw-beef-tenderloin.jpg",
                        "A scrumptious beef tenderloin", "Long description", 4.99, "lb")
        );

        allProducts.add(
                new Product(4, "Eggs", 10, "https://www.humphreysfarm.com/productcart/pc/catalog/5514-lg.jpg",
                        "A nourishing breakfast", "Long description", 2.99, "dozen")
        );
        allProducts.add(
                new Product(5, "Tomato", 10, "https://st.depositphotos.com/1642482/2529/i/600/depositphotos_25296371-stock-photo-red-tomato.jpg",
                        "An acidic treat", "Long description", 3.99, "lb")
        );
        allProducts.add(
                new Product(6, "Apple", 10, "https://cdn.britannica.com/14/77414-004-30B131EC/Apple.jpg",
                        "A juicy delight", "Long description", 3.50, "lb")
        );
        allProducts.add(
                new Product(7, "Banana", 10, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Banana_%281%29.jpg/1200px-Banana_%281%29.jpg",
                        "An energizing snack", "Long description", 2.99, "lb")
        );
    }

    public static GlobalStorage getInstance() {
        if(instance == null){
            instance = new GlobalStorage();
        }
        return instance;
    }

    public ArrayList<Product> getAllProducts() {
        //TODO: get allProducts from DB

        return allProducts;
    }

    public ArrayList<Recipe> getRecipes() {
        //TODO: get recipeList

        return recipes;
    }

    public Product getProductFromProductId(int productId) {
        for(Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }

        return null;
    }

    public void addProductToAllProducts(Product product) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("product");
        reference.child(String.valueOf(product.getId())).setValue(product);
        allProducts.add(product);
    }

    public void addRecipeToRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

    public int getNewId(){
        int maxId = Integer.MIN_VALUE;
        for (Product product : allProducts) {
            maxId = Math.max(maxId, product.getId());
        }
        return maxId + 1;
    }

}
