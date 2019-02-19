package com.example.store;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProductsListActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";

    public static Intent newIntent(Context context, Long categoryId){
        Intent intent = new Intent(context, ProductsListActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID,categoryId);
        return intent;
    }

    @Override
    public Fragment createFragment(Long categoryId) {
        return ProductsListFragment.newInstance(categoryId);
    }
}
