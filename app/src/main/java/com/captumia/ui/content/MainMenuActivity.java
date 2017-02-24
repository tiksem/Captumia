package com.captumia.ui.content;

import android.os.Bundle;
import android.view.View;

import com.captumia.R;
import com.captumia.events.LoginEvent;
import com.captumia.events.LogoutEvent;
import com.captumia.ui.BaseMenuActivity;
import com.captumia.ui.forms.BusinessRegisterActivity;
import com.captumia.ui.forms.LoginActivity;
import com.captumia.ui.MainMenuFragmentsFactory;
import com.utilsframework.android.navdrawer.FragmentFactory;
import com.utilsframework.android.navdrawer.NavigationViewMenuAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainMenuActivity extends BaseMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View header = View.inflate(this, R.layout.menu_header, null);
        getNavigationView().addHeaderView(header);
        registerHeaderItemAsSelectable(R.id.logo);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClicked();
            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    protected void doCleanup() {
        super.doCleanup();
        EventBus.getDefault().unregister(this);
    }

    private void onSignUpClicked() {
        closeDrawer();
        BusinessRegisterActivity.start(this);
    }

    private void onLoginClicked() {
        closeDrawer();
        LoginActivity.start(this);
    }

    @Subscribe
    public void onLogout(LogoutEvent event) {
        setLogoutItemVisibility(false);
        closeDrawer();
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        setLogoutItemVisibility(true);
    }

    private void setLogoutItemVisibility(boolean visible) {
        getNavigationView().getMenu().findItem(R.id.log_out).setVisible(visible);
    }

    @Override
    protected int getMenuId() {
        return R.menu.main_menu;
    }

    @Override
    protected int getCurrentSelectedNavigationItemId() {
        return R.id.logo;
    }

    @Override
    protected FragmentFactory createFragmentFactory() {
        return new MainMenuFragmentsFactory();
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.main_menu_activity;
    }

    @Override
    protected NavigationViewMenuAdapter createNavigationViewMenuAdapter(int navigationViewId) {
        return new MainMenuAdapter(this, navigationViewId, getMenuId());
    }
}
