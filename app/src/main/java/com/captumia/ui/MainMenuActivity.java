package com.captumia.ui;

import android.os.Bundle;

import com.captumia.R;
import com.utilsframework.android.navdrawer.FragmentFactory;

public class MainMenuActivity extends BaseMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getMenuId() {
        return R.menu.main_menu;
    }

    @Override
    protected int getCurrentSelectedNavigationItemId() {
        return R.id.photographers;
    }

    @Override
    protected FragmentFactory createFragmentFactory() {
        return new MainMenuFragmentsFactory();
    }
}
