package com.example.store.network;

import android.net.Uri;

import com.example.store.models.Category;
import com.example.store.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {


    @GET("products/?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<List<Product>> getProducts();

    @GET("products/?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<List<Product>> getProducts(@Query("orderby") String orderBy);

    @GET("products/{id}?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<Product> getProduct(@Path("id") long id);

//    @GET()
//    Call<Product> getProduct(@Url String url);

    @GET("products/categories/?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<List<Category>> getCategories(@Query("parent") long parent);
}
