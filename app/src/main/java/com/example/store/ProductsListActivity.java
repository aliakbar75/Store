package com.example.store;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProductsListActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";
    private static final String EXTRA_LIST_TYPE = "list_type";

    public static Intent newIntent(Context context, Long categoryId, int listType){
        Intent intent = new Intent(context, ProductsListActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID,categoryId);
        intent.putExtra(EXTRA_LIST_TYPE,listType);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        Long categoryId = getIntent().getLongExtra(EXTRA_CATEGORY_ID,0);
        int listType = getIntent().getIntExtra(EXTRA_LIST_TYPE,0);
        return ProductsListFragment.newInstance(categoryId,listType);
    }
}
