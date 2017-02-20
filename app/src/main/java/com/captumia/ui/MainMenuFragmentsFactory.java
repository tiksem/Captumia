package com.captumia.ui;

import android.support.v4.app.Fragment;

import com.captumia.R;
import com.utilsframework.android.navdrawer.NoTabsFragmentFactory;

import java.util.HashMap;
import java.util.Map;

public class MainMenuFragmentsFactory extends NoTabsFragmentFactory {
    @Override
    public Fragment createFragmentBySelectedItem(int selectedItem) {
        switch (selectedItem) {
            case R.id.logo:
                return new HomeFragment();
            case R.id.categories:
                return new CategoriesFragment();
            default:
                throw new UnsupportedOperationException("unsupported item");
        }
    }
}
