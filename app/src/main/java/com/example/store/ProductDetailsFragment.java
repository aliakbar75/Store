package com.example.store;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.store.models.Image;
import com.example.store.models.Product;
import com.example.store.network.Api;
import com.example.store.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private ViewPager mViewPager;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductDescription;
    private Toolbar mToolbar;

    private List<Product> mProducts;
    private Product mProduct;
    private Long mProductId;


    private static final String ARG_PRODUCT_ID = "product_id";

    public static ProductDetailsFragment newInstance(Long productId) {

        Bundle args = new Bundle();
        args.putLong(ARG_PRODUCT_ID,productId);

        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getLong(ARG_PRODUCT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        mViewPager = view.findViewById(R.id.product_images_view_pager);
        mProductName = view.findViewById(R.id.product_name);
        mProductPrice = view.findViewById(R.id.product_price);
        mProductDescription = view.findViewById(R.id.product_description);
        mToolbar = view.findViewById(R.id.toolbar);

//        ((ProductDetailsActivity)getActivity()).setSupportActionBar(mToolbar);


        RetrofitInstance.getInstance().create(Api.class).getProduct(mProductId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()){
                    mProduct = response.body();
                    mProductName.setText(mProduct.getName());
                    mProductPrice.setText("قیمت :    " + mProduct.getPrice());
                    mProductDescription.setText(mProduct.getDescription());

                    mViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
                        @Override
                        public Fragment getItem(int i) {
                            String imagePath = mProduct.getImages().get(i).getPath();
                            return ProductImagesFragment.newInstance(imagePath);
                        }

                        @Override
                        public int getCount() {
                            return mProduct.getImages().size();
                        }
                    });


                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }

        });



        return view;
    }

}
