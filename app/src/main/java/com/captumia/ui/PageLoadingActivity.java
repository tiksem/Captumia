package com.captumia.ui;

import android.os.Bundle;
import android.view.View;

import com.captumia.R;
import com.utilsframework.android.network.CancelStrategy;
import com.utilsframework.android.network.LoadingContentVisibilityManager;
import com.utilsframework.android.network.retrofit.CallProvider;

import java.util.List;

/**
 * Created by stikhonenko on 2/23/17.
 */
public abstract class PageLoadingActivity extends RequestManagerActivity {
    private LoadingContentVisibilityManager<List, Throwable> contentVisibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootLayoutId());
        load();
    }

    private void load() {
        contentVisibilityManager = new LoadingContentVisibilityManager<List, Throwable>(this) {
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
            public void onRetry() {
                load();
            }

            @Override
            public int getNoConnectionView() {
                return R.id.no_connection;
            }
        };
        getRequestManager().executeMultipleCalls(getCallProviders(),
                contentVisibilityManager, CancelStrategy.INTERRUPT);
    }

    public abstract List<CallProvider> getCallProviders();

    public View getPageContentView() {
        return contentVisibilityManager.getContentView();
    }

    public abstract int getRootLayoutId();
}
