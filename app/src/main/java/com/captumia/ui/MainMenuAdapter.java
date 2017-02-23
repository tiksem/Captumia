package com.captumia.ui;

import android.app.Activity;
import android.content.Context;

import com.captumia.R;
import com.utilsframework.android.navdrawer.NavigationViewMenuAdapter;

public class MainMenuAdapter extends NavigationViewMenuAdapter {
    private Context context;

    public MainMenuAdapter(Activity activity,
                           int navigationViewId,
                           int menuResourceId) {
        super(activity, navigationViewId, menuResourceId);
        context = activity;
    }

    @Override
    protected void onItemSelected(int itemId) {
        if (itemId == R.id.add_service) {
            AddPostActivity.start(context);
        } else {
            super.onItemSelected(itemId);
        }
    }
}
