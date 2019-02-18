package com.example.store.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root {

    @SerializedName("products")
    private List<Product> mProducts;

    public Root(List<Product> products) {
        mProducts = products;
    }

    public List<Product> getProducts() {
        return mProducts;
    }
}
