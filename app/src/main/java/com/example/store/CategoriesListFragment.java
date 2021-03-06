package com.example.store;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.models.Category;
import com.example.store.models.Product;
import com.example.store.network.Api;
import com.example.store.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesListFragment extends Fragment {

    private static final String ARG_PARENT_ID = "parent_id";
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    private ProductAdapter mProductAdapter;
    private Long mParentId;

    public static CategoriesListFragment newInstance(Long parentId) {

        Bundle args = new Bundle();
        args.putLong(ARG_PARENT_ID,parentId);
        CategoriesListFragment fragment = new CategoriesListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CategoriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentId = getArguments().getLong(ARG_PARENT_ID);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categories_list, container, false);
        mRecyclerView = view.findViewById(R.id.categories_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mParentId == 15){
            RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()){
                        List<Product> products = response.body();
                        List<Product> categoryProducts = new ArrayList<>();

                        for (int i=0; i<products.size(); i++ ){
                            if (products.get(i).getProductCategories().get(0).getId() == 15){
                                categoryProducts.add(products.get(i));
                                Log.d("hhhhhhhhhhhh",products.get(i).getName());
                            }

                        }
                        mProductAdapter  = new ProductAdapter(categoryProducts);
                        mRecyclerView.setAdapter(mProductAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });
        }else {
            RetrofitInstance.getInstance().create(Api.class).getCategories(mParentId).enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if (response.isSuccessful()){
                        List<Category> categories = response.body();
                        mCategoryAdapter  = new CategoryAdapter(categories);
                        mRecyclerView.setAdapter(mCategoryAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {

                }
            });
        }



        return view;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private ImageView mCategoryImageView;
        private Category mCategory;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.category_name_text_view);
            mCategoryImageView = itemView.findViewById(R.id.category_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductsListActivity.newIntent(getActivity(),mCategory.getId(),0);
                    startActivity(intent);
                }
            });
        }

        public void bind(Category category) {
            mCategory = category;
            mNameTextView.setText(category.getName());
            if(category.getImage()!= null){
                Picasso.get().load(category.getImage().getPath()).into(mCategoryImageView);
            }
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private List<Category> mCategories ;

        public CategoryAdapter(List<Category> categories) {
            mCategories = categories;
        }

        public void setCategories(List<Category> categories) {
            mCategories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_category, parent, false);
            CategoryHolder categoryHolder = new CategoryHolder(view);
            return categoryHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
            Category category = mCategories.get(position);
            holder.bind(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
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
