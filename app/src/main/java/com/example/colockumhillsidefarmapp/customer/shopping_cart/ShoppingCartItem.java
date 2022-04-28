package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.Comparator;

public class ShoppingCartItem {

    private Product product;
    private int quantity;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static Comparator<ShoppingCartItem> ShoppingCartItemNameAZComparator = new Comparator<ShoppingCartItem>() {
        @Override
        public int compare(ShoppingCartItem shoppingCartItem, ShoppingCartItem t1) {
            return shoppingCartItem.product.getName().compareTo(t1.product.getName());
        }
    };

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
