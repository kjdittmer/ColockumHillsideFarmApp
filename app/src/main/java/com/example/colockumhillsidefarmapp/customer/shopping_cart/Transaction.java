package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import java.util.Date;
import java.util.HashMap;

public class Transaction {

    private HashMap<String, Integer> productsPurchased;
    private double price;
    private Date time;
    private String user;

    public Transaction(HashMap<String, Integer> productsPurchased, double price, Date time, String user) {
        this.productsPurchased = productsPurchased;
        this.price = price;
        this.time = time;
        this.user = user;
    }

    public HashMap<String, Integer> getProductsPurchased() {
        return productsPurchased;
    }

    public void setProductsPurchased(HashMap<String, Integer> productsPurchased) {
        this.productsPurchased = productsPurchased;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
