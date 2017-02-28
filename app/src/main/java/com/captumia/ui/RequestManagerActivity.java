package com.captumia.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.captumia.app.CaptumiaApplication;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public class RequestManagerActivity extends AppCompatActivity {
    private RetrofitRequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = CaptumiaApplication.getInstance().getRequestManagerFactory().
                createRequestManager();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
