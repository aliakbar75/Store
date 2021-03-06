package com.example.store;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.models.Product;
import com.example.store.network.Api;
import com.example.store.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsListFragment extends Fragment {

    private static final String ARG_PARENT_ID = "parent_id";
    private static final String ARG_LIST_TYPE = "list_type";
    private RecyclerView mRecyclerView;
    private ProductAdapter mProductAdapter;
    private Long mCategoryId;
    private Toolbar mToolbar;
    private int mListType;

    public static ProductsListFragment newInstance(Long categoryId, int listType) {

        Bundle args = new Bundle();
        args.putLong(ARG_PARENT_ID,categoryId);
        args.putInt(ARG_LIST_TYPE,listType);
        ProductsListFragment fragment = new ProductsListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getLong(ARG_PARENT_ID);
        mListType = getArguments().getInt(ARG_LIST_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        mRecyclerView = view.findViewById(R.id.products_recycler_view);
        mToolbar = view.findViewById(R.id.toolbar);
        ((ProductsListActivity)getActivity()).setSupportActionBar(mToolbar);
        ((ProductsListActivity)getActivity()).getSupportActionBar().setTitle("لیست محصولات");


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mCategoryId == 0){
            switch (mListType){
                case 1:
                    Log.d("hhhhhhhhh","called");
                    RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if(response.isSuccessful()){
                                List<Product> products = response.body();

                                mProductAdapter  = new ProductAdapter(products);
                                mRecyclerView.setAdapter(mProductAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {

                        }
                    });

                case 2:

                    RetrofitInstance.getInstance().create(Api.class).getProducts("popularity").enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if(response.isSuccessful()){
                                List<Product> products = response.body();

                                mProductAdapter  = new ProductAdapter(products);
                                mRecyclerView.setAdapter(mProductAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {

                        }
                    });

                case 3:

                    RetrofitInstance.getInstance().create(Api.class).getProducts("rating").enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if(response.isSuccessful()){
                                List<Product> products = response.body();

                                mProductAdapter  = new ProductAdapter(products);
                                mRecyclerView.setAdapter(mProductAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {

                        }
                    });
            }

        }else {
            RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if(response.isSuccessful()){
                        List<Product> products = response.body();
                        List<Product> categoryProducts = new ArrayList<>();

                        for (int i=0; i<products.size(); i++ ){
                            if (products.get(i).getProductCategories().size()>1){
                                if (products.get(i).getProductCategories().get(0).getId() == mCategoryId ||
                                        products.get(i).getProductCategories().get(1).getId() == mCategoryId){
                                    categoryProducts.add(products.get(i));
                                    Log.d("hhhhhhhhhhhh",products.get(i).getName());
                                }
                            }

                        }

                        mProductAdapter  = new ProductAdapter(categoryProducts);
                        mRecyclerView.setAdapter(mProductAdapter);

                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.d("hhhhhhhhh","fail");
                }
            });
        }


        return view;
    }

    private class ProductHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private ImageView mProductImageView;
        private Product mProduct;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.product_name_text_view);
            mProductImageView = itemView.findViewById(R.id.product_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductDetailsActivity.newIntent(getActivity(),mProduct.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(Product product) {
            mProduct = product;
            mNameTextView.setText(product.getName());
            if(product.getImages()!= null && product.getImages().size()>0){
                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImageView);
            }
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        public void setProducts(List<Product> products) {
            mProducts = products;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_product, parent, false);
            ProductHolder productHolder = new ProductHolder(view);
            return productHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

}
