package com.captumia.ui.content;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import com.captumia.CaptumiaApplication;
import com.captumia.R;
import com.captumia.ui.forms.AddPostActivity;
import com.utilsframework.android.navdrawer.NavigationViewMenuAdapter;

public class MainMenuAdapter extends NavigationViewMenuAdapter {
    public static final int ITEM_SELECTION_DURATION = 500;

    private Context context;

    public MainMenuAdapter(Activity activity,
                           int navigationViewId,
                           int menuResourceId) {
        super(activity, navigationViewId, menuResourceId);
        context = activity;
        MenuItem logoutItem = getNavigationMenuView().getMenu().findItem(R.id.log_out);
        boolean loggedIn = CaptumiaApplication.getInstance().getAuthHandler().isLoggedIn();
        logoutItem.setVisible(loggedIn);

    }

    @Override
    protected void onItemSelected(int itemId) {
        if (itemId == R.id.add_service) {
            imitateTap(itemId);
            AddPostActivity.start(context);
        } else if(itemId == R.id.log_out) {
            imitateTap(itemId);
            CaptumiaApplication.getInstance().logout();
        } else {
            super.onItemSelected(itemId);
        }
    }

    private void imitateTap(int itemId) {
        Menu menu = getNavigationMenuView().getMenu();
        final MenuItem menuItem = menu.findItem(itemId);
        menuItem.setChecked(true);
        getNavigationMenuView().postDelayed(new Runnable() {
            @Override
            public void run() {
                menuItem.setChecked(false);
            }
        }, ITEM_SELECTION_DURATION);
    }
}
