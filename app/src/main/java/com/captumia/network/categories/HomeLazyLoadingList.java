package com.captumia.network.categories;

import com.captumia.data.Category;
import com.captumia.network.RestApiClient;
import com.utils.framework.KeyProvider;
import com.utilsframework.android.network.RetrofitOnePageLazyLoadingList;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class HomeLazyLoadingList extends RetrofitOnePageLazyLoadingList<Category> {
    private RestApiClient restApiClient;

    public HomeLazyLoadingList(RetrofitRequestManager requestManager, RestApiClient restApiClient) {
        super(requestManager);
        this.restApiClient = restApiClient;
    }

    @Override
    protected Call<List<Category>> createLoadPageCall() {
        return restApiClient.getTopRootCategories();
    }

    @Override
    protected KeyProvider<Object, Category> getKeyProvider() {
        return new CategoryKeyProvider();
    }
}
