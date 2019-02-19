package com.example.store.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("price")
    private String mPrice;

    @SerializedName("images")
    private List<Image> mImages;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("date_created")
    private String mCreatedDate;

    @SerializedName("total_sales")
    private int mTotalSales;

    @SerializedName("average_rating")
    private String mAverageRating;

    @SerializedName("categories")
    private List<ProductCategory> mProductCategories;

    public Product(Long id,
                   String name,
                   String price,
                   List<Image> images,
                   String description,
                   String createdDate,
                   int totalSales,
                   String averageRating,
                   List<ProductCategory> productCategories) {
        mId = id;
        mName = name;
        mPrice = price;
        mImages = images;
        mDescription = description;
        mCreatedDate = createdDate;
        mTotalSales = totalSales;
        mAverageRating = averageRating;
        mProductCategories = productCategories;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public int getTotalSales() {
        return mTotalSales;
    }

    public String getAverageRating() {
        return mAverageRating;
    }

    public List<ProductCategory> getProductCategories() {
        return mProductCategories;
    }
}
