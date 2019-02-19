package com.example.store;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class ProductDetailsActivity extends SingleFragmentActivity{

    private static final String EXTRA_PRODUCT_ID = "product_id";

    public static Intent newIntent(Context context, Long productId){
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID,productId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Long productId = getIntent().getLongExtra(EXTRA_PRODUCT_ID,0);
        return ProductDetailsFragment.newInstance(productId);
    }
}
