package com.example.store.network;

import com.example.store.models.Category;
import com.example.store.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("products/?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<List<Product>> getProducts();

    @GET("products/categories/?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<List<Category>> getCategories();
}
