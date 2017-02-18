package com.captumia.ui;

import android.os.Bundle;

import com.captumia.R;
import com.captumia.network.CaptumiaRequestManager;
import com.utilsframework.android.navdrawer.NavigationDrawerActivity;
import com.utilsframework.android.navdrawer.NavigationDrawerMenuActivity;

public abstract class BaseMenuActivity extends NavigationDrawerMenuActivity {
    private CaptumiaRequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = new CaptumiaRequestManager();
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

    @Override
    protected int getToolbarLayoutId() {
        return R.layout.toolbar;
    }
}
