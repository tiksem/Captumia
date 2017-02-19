package com.captumia.ui;

import android.os.Bundle;
import android.view.View;

import com.captumia.R;
import com.utilsframework.android.navdrawer.FragmentFactory;

public class MainMenuActivity extends BaseMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View header = View.inflate(this, R.layout.menu_header, null);
        getNavigationView().addHeaderView(header);
        registerHeaderItemAsSelectable(R.id.logo);
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
}
