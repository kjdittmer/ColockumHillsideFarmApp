package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.Date;

public class Transaction implements Parcelable {

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

    protected Transaction(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        quantity = in.readInt();
        cost = in.readDouble();
        user = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeInt(quantity);
        parcel.writeDouble(cost);
        parcel.writeString(user);
    }
}
