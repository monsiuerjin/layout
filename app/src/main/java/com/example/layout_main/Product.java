package com.example.layout_main;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private double price;
    private double oldPrice;
    private String description;
    private int imageResourceId;

    // Constructor mặc định (nếu cần)
    public Product() {
    }

    public Product(String name, double price, double oldPrice,String description ,int imageResourceId) {
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        oldPrice = in.readDouble();
        description = in.readString();
        imageResourceId = in.readInt();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeDouble(oldPrice);
        dest.writeString(description);
        dest.writeInt(imageResourceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
    public String getDescription() {
        return description;
    }
}
