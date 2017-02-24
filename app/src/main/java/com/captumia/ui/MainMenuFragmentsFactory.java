package com.captumia.ui;

import android.support.v4.app.Fragment;

import com.captumia.R;
import com.captumia.ui.content.CategoriesFragment;
import com.captumia.ui.content.HomeFragment;
import com.utilsframework.android.navdrawer.NoTabsFragmentFactory;

public class MainMenuFragmentsFactory extends NoTabsFragmentFactory {
    @Override
    public Fragment createFragmentBySelectedItem(int selectedItem) {
        switch (selectedItem) {
            case R.id.logo:
                return new HomeFragment();
            case R.id.categories:
                return CategoriesFragment.createWithRootCategories();
            default:
                throw new UnsupportedOperationException("unsupported item");
        }
    }
}
