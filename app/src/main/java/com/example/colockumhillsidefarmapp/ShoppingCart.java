package com.example.colockumhillsidefarmapp;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    private static ShoppingCart instance;
    private ArrayList<Product> allProducts;
    private HashMap<Product, Integer> cart;
    private HashMap<Product, Integer> wishlist;

    private ShoppingCart() {
        if(allProducts == null) {
            allProducts = new ArrayList<>();
        }
        if(cart == null) {
            cart = new HashMap<>();
        }

        if(wishlist == null) {
            wishlist = new HashMap<>();
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

    public static ShoppingCart getInstance() {
        if(instance == null){
            instance = new ShoppingCart();
        }
        return instance;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }

    public HashMap<Product, Integer> getWishlist() {
        return wishlist;
    }

    public Product getProduct(int productId) {
        for(Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }

        return null;
    }

    public void addProductToCart(Product product, int quantity) {
        if(!cart.containsKey(product)) {
            cart.put(product, quantity);
        } else {
            cart.put(product, quantity + cart.get(product));
        }
    }

    public void removeProductFromCart(Product product) {
        if(cart.containsKey(product)) {
            cart.remove(product);
        }
    }

    public void addProductToWishlist(Product product, int quantity) {
        if(!wishlist.containsKey(product)) {
            wishlist.put(product, quantity);
        } else {
            wishlist.put(product, quantity + wishlist.get(product));
        }
    }

}
