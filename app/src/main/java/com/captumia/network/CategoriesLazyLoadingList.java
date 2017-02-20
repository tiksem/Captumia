package com.captumia.network;

import com.captumia.data.Category;
import com.utils.framework.KeyProvider;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class CategoriesLazyLoadingList extends BaseLazyLoadingList<Category> {
    private int parent;

    public CategoriesLazyLoadingList(RetrofitRequestManager requestManager,
                                     RestApiClient restApiClient, int parent) {
        super(requestManager, restApiClient);
        this.parent = parent;
    }

    @Override
    protected Call<List<Category>> createLoadPageCall(int itemsPerPage) {
        return getRestApiClient().getCategories(parent, getLoadedPagesCount() + 1, itemsPerPage);
    }

    @Override
    protected KeyProvider<Object, Category> getKeyProvider() {
        return new CategoryKeyProvider();
    }
}
