package com.captumia.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.captumia.network.AppRequestManager;

public class RequestManagerActivity extends AppCompatActivity {
    private AppRequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = new AppRequestManager();
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
}
