package com.example.colockumhillsidefarmapp.customer.about_us;

import android.os.Parcel;
import android.os.Parcelable;

public class Info implements Parcelable {
    private int id;
    private String name;
    private String imageUrl;
    private String text;

    public Info() {}
    public Info(int id, String name, String imageUrl, String text){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.text = text;

    }

    protected Info(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        text = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RecipeView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
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
        parcel.writeString(imageUrl);
        parcel.writeString(text);
    }
}
