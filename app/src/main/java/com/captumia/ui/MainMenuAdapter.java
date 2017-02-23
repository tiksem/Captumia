package com.captumia.ui;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import com.captumia.R;
import com.utilsframework.android.navdrawer.NavigationViewMenuAdapter;

public class MainMenuAdapter extends NavigationViewMenuAdapter {
    public static final int ITEM_SELECTION_DURATION = 500;

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
            Menu menu = getNavigationMenuView().getMenu();
            final MenuItem menuItem = menu.findItem(itemId);
            menuItem.setChecked(true);
            getNavigationMenuView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    menuItem.setChecked(false);
                }
            }, ITEM_SELECTION_DURATION);
            AddPostActivity.start(context);
        } else {
            super.onItemSelected(itemId);
        }
    }
}
