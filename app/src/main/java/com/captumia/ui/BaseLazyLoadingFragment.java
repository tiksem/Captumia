package com.captumia.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.events.LoginEvent;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.navdrawer.FragmentsNavigationInterface;
import com.utilsframework.android.fragments.LazyLoadingListFragment;
import com.utilsframework.android.network.retrofit.LoginRequiredException;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseLazyLoadingFragment<T> extends LazyLoadingListFragment<T> {

    private TextView noConnectionTextView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noConnectionTextView = (TextView) view.findViewById(R.id.no_connection_text);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

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

    public FragmentsNavigationInterface getNavigationInterface() {
        return (FragmentsNavigationInterface) getActivity();
    }

    @Override
    protected void handleNavigationListError(int errorCount, Throwable e) {
        if (e instanceof LoginRequiredException) {
            noConnectionTextView.setText(R.string.login_required);
        } else {
            noConnectionTextView.setText(R.string.no_internet_connection);
        }

        super.handleNavigationListError(errorCount, e);
    }

    @Override
    protected int getErrorToastMessage(Throwable e) {
        if (e instanceof LoginRequiredException) {
            return R.string.login_required;
        } else {
            return super.getErrorToastMessage(e);
        }
    }

    @Subscribe
    public void onLogin(LoginEvent event) {

    }
}
