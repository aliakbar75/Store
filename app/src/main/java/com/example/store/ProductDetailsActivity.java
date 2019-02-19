package com.example.store;

import android.support.v4.app.Fragment;

public class ProductDetailsActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment(Long categoryId) {
        return ProductDetailsFragment.newInstance();
    }
}
