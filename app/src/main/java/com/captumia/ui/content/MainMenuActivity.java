package com.captumia.ui.content;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.events.LoginEvent;
import com.captumia.events.LogoutEvent;
import com.captumia.ui.BaseMenuActivity;
import com.captumia.ui.forms.loginregister.BusinessRegisterActivity;
import com.captumia.ui.forms.loginregister.LoginActivity;
import com.captumia.ui.MainMenuFragmentsFactory;
import com.utilsframework.android.navdrawer.FragmentFactory;
import com.utilsframework.android.navdrawer.NavigationViewMenuAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainMenuActivity extends BaseMenuActivity {

    private View loginButton;
    private View signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View header = View.inflate(this, R.layout.menu_header, null);
        getNavigationView().addHeaderView(header);
        registerHeaderItemAsSelectable(R.id.logo);

        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });

        signUpButton = findViewById(R.id.signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClicked();
            }
        });

        EventBus.getDefault().register(this);
        setUserItemsVisibility(CaptumiaApplication.getInstance().getNetworkHandler().isLoggedIn());
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
        setUserItemsVisibility(false);
        closeDrawer();
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        setUserItemsVisibility(true);
    }

    private void setUserItemsVisibility(boolean visible) {
        Menu menu = getNavigationView().getMenu();
        menu.findItem(R.id.log_out).setVisible(visible);
        menu.findItem(R.id.add_service).setVisible(visible);
        loginButton.setVisibility(visible ? View.GONE : View.VISIBLE);
        signUpButton.setVisibility(visible ? View.GONE : View.VISIBLE);
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
