package com.captumia.network;

import com.utilsframework.android.network.RetrofitLazyLoadingList;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public abstract class BaseLazyLoadingList<T> extends RetrofitLazyLoadingList<T> {
    private static final int DEFAULT_ITEMS_PER_PAGE = 10;

    private RestApiClient restApiClient;

    public BaseLazyLoadingList(RetrofitRequestManager requestManager, RestApiClient restApiClient) {
        super(requestManager, DEFAULT_ITEMS_PER_PAGE);
        this.restApiClient = restApiClient;
    }

    public RestApiClient getRestApiClient() {
        return restApiClient;
    }
}
