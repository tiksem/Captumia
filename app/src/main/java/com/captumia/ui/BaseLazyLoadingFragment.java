package com.captumia.ui;

import com.captumia.CaptumiaApplication;
import com.captumia.R;
import com.captumia.network.AppRequestManager;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.navigation.LazyLoadingListFragment;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public abstract class BaseLazyLoadingFragment<T> extends LazyLoadingListFragment<T> {
    @Override
    protected int getListResourceId() {
        return R.id.list;
    }

    @Override
    protected int getLoadingResourceId() {
        return R.id.loading;
    }

    @Override
    protected int getRetryLoadingButtonId() {
        return R.id.retry;
    }

    @Override
    protected void onListItemClicked(T item, int position) {

    }

    @Override
    protected int getNoInternetConnectionViewId() {
        return R.id.no_connection;
    }

    @Override
    protected RetrofitRequestManager obtainRequestManager() {
        return new AppRequestManager();
    }

    public final RestApiClient getRestApiClient() {
        return CaptumiaApplication.getInstance().getRestApiClient();
    }
}
