package com.captumia.ui;

import android.os.Bundle;
import android.view.View;

import com.captumia.CaptumiaApplication;
import com.captumia.R;
import com.utilsframework.android.navdrawer.FragmentFactory;

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
    }

    private void onSignUpClicked() {

    }

    private void onLoginClicked() {
        LoginActivity.start(this);
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
}
