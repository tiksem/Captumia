package com.captumia.ui;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.network.CategoriesLazyLoadingList;
import com.captumia.ui.adapters.CategoryAdapter;
import com.utils.framework.collections.LazyLoadingList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public class CategoriesFragment extends BaseLazyLoadingFragment<Category> {
    @Override
    protected ViewArrayAdapter<Category, ?> createAdapter() {
        return new CategoryAdapter(getContext());
    }

    @Override
    protected LazyLoadingList<Category> getLazyLoadingList(RetrofitRequestManager requestManager,
                                                           String filter) {
        return new CategoriesLazyLoadingList(requestManager, getRestApiClient());
    }

    @Override
    protected int getRootLayout() {
        return R.layout.categories;
    }

    @Override
    protected void onListItemClicked(Category item, int position) {
        PostsByCategoryFragment fragment = PostsByCategoryFragment.create(item.getId());
        getNavigationInterface().replaceFragment(fragment, 1);
    }
}
