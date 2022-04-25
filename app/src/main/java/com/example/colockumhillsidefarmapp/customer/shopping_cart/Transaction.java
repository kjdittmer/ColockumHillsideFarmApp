package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.Date;

public class Transaction {

    private Product product;
    private int quantity;
    private double cost;
    private Date time;
    private String user;

    public Transaction () {
    }

    public Transaction(Product product, int quantity, double cost, Date time, String user) {
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
        this.time = time;
        this.user = user;
    }

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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
