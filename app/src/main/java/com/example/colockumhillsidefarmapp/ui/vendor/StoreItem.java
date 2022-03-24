package com.example.colockumhillsidefarmapp.ui.vendor;


public class StoreItem {

    private String name;
    private String imageUrl;
    private String shortDesc;
    private String longDesc;
    private String packageQuantity;
    private int quantity;
    private double price;

    public StoreItem(){

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public String getShortDesc(){
        return shortDesc;
    }
    public void setShortDesc(String shortDesc){
        this.shortDesc = shortDesc;
    }
    public String getLongDesc(){
        return longDesc;
    }
    public void setLongDesc(String longDesc){
        this.longDesc = longDesc;
    }
    public String getPackageQuantity(){
        return packageQuantity;
    }
    public void setPackageQuantity(String packageQuantity){
        this.packageQuantity=packageQuantity;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
}
