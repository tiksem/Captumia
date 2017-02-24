package com.captumia.ui.content;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.network.CategoriesLazyLoadingList;
import com.captumia.ui.BaseLazyLoadingFragment;
import com.captumia.ui.adapters.CategoryAdapter;
import com.utils.framework.collections.LazyLoadingList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public class CategoriesFragment extends BaseLazyLoadingFragment<Category> {
    public static final String PARENT = "parent";

    private int parent;

    public static CategoriesFragment createWithCategoriesOfParent(int parent) {
        return Fragments.createFragmentWith1Arg(new CategoriesFragment(), PARENT, parent);
    }

    public static CategoriesFragment createWithRootCategories() {
        return new CategoriesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = Fragments.getInt(this, PARENT, 0);
    }

    @Override
    protected ViewArrayAdapter<Category, ?> createAdapter() {
        return new CategoryAdapter(getContext());
    }

    @Override
    protected LazyLoadingList<Category> getLazyLoadingList(RetrofitRequestManager requestManager,
                                                           String filter) {
        return new CategoriesLazyLoadingList(requestManager, getRestApiClient(), parent);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.categories;
    }

    @Override
    protected void onEmptyResults() {
        PostsByCategoryFragment fragment = PostsByCategoryFragment.create(parent);
        getNavigationInterface().replaceCurrentFragmentWithoutAddingToBackStack(fragment);
    }

    @Override
    protected void onListItemClicked(Category item, int position) {
        // check if it's not root
        int categoryId = item.getId();
        Fragment fragment;
        if (parent != 0) {
            fragment = PostsByCategoryFragment.create(categoryId);
        } else {
            fragment = CategoriesFragment.createWithCategoriesOfParent(categoryId);
        }

        getNavigationInterface().replaceFragment(fragment, 1);
    }
}
