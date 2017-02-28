package com.captumia.ui;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.fragments.PageLoadingFragment;
import com.utilsframework.android.navdrawer.NavigationActivityInterface;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public abstract class BasePageLoadingFragment extends PageLoadingFragment {
    @Override
    public int getContentLayoutId() {
        return R.id.content;
    }

    @Override
    public int getLoadingLayoutId() {
        return R.id.loading;
    }

    @Override
    public int getRetryButtonId() {
        return R.id.retry;
    }

    @Override
    public int getNoConnectionView() {
        return R.id.no_connection;
    }

    @Override
    protected RetrofitRequestManager obtainRequestManager() {
        return CaptumiaApplication.getInstance().getRequestManagerFactory().createRequestManager();
    }

    public NavigationActivityInterface getNavigationInterface() {
        return (NavigationActivityInterface) getActivity();
    }

    public final RestApiClient getRestApiClient() {
        return CaptumiaApplication.getInstance().getRestApiClient();
    }
}
