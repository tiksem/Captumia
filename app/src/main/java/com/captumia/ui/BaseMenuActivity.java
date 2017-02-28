package com.captumia.ui;

import android.os.Bundle;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.navdrawer.NavigationDrawerMenuActivity;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public abstract class BaseMenuActivity extends NavigationDrawerMenuActivity {
    private RetrofitRequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = CaptumiaApplication.getInstance().getRequestManagerFactory().
                createRequestManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doCleanup();
    }

    protected void doCleanup() {
        if (requestManager != null) {
            requestManager.cancelAll();
            requestManager = null;
        }
    }

    @Override
    public void finish() {
        doCleanup();
        super.finish();
    }

    public RetrofitRequestManager getRequestManager() {
        return requestManager;
    }

    public RestApiClient getRestApiClient() {
        return CaptumiaApplication.getInstance().getRestApiClient();
    }

    @Override
    protected int getToolbarLayoutId() {
        return R.layout.toolbar;
    }
}
