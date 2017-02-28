package com.captumia.ui;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.navdrawer.NavigationActivityInterface;
import com.utilsframework.android.fragments.LazyLoadingListFragment;
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
    protected int getRootLayout() {
        return R.layout.lazy_loading_list_fragment;
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
        return CaptumiaApplication.getInstance().getRequestManagerFactory().createRequestManager();
    }

    public final RestApiClient getRestApiClient() {
        return CaptumiaApplication.getInstance().getRestApiClient();
    }

    public NavigationActivityInterface getNavigationInterface() {
        return (NavigationActivityInterface) getActivity();
    }
}
