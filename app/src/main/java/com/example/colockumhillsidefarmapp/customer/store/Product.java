package com.example.colockumhillsidefarmapp.customer.store;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Product implements Parcelable {
    private int id;
    private String name;
    private int quantity;
    private String imageUrl;
    private String shortDesc;
    private String longDesc;
    private double price;
    private String packageQuantity;

    public Product() {}

    public Product(int id, String name, int quantity, String imageUrl, String shortDesc, String longDesc, double price, String packageQuantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.price = price;
        this.packageQuantity = packageQuantity;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        quantity = in.readInt();
        imageUrl = in.readString();
        shortDesc = in.readString();
        longDesc = in.readString();
        price = in.readDouble();
        packageQuantity = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public static Comparator<Product> ProductNameAZComparator = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return product.name.compareTo(t1.name);
        }
    };

    public static Comparator<Product> ProductNameZAComparator = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return t1.name.compareTo(product.name);
        }
    };

    public static Comparator<Product> ProductPriceAscendingComparator = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            int price1 = (product.price > 0) ? (int) Math.ceil(product.price) : (int) Math.floor(product.price);
            int price2 = (t1.price > 0) ? (int) Math.ceil(t1.price) : (int) Math.floor(t1.price);
            return price1 - price2;
        }
    };

    public static Comparator<Product> ProductPriceDescendingComparator = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            int price1 = (product.price > 0) ? (int) Math.ceil(product.price) : (int) Math.floor(product.price);
            int price2 = (t1.price > 0) ? (int) Math.ceil(t1.price) : (int) Math.floor(t1.price);
            return price2 - price1;
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(String packageQuantity) { this.packageQuantity = packageQuantity; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", price=" + price +
                ", packageQuantity='" + packageQuantity + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(quantity);
        parcel.writeString(imageUrl);
        parcel.writeString(shortDesc);
        parcel.writeString(longDesc);
        parcel.writeDouble(price);
        parcel.writeString(packageQuantity);
    }
}