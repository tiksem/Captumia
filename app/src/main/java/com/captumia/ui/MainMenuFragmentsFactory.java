package com.captumia.ui;

import android.support.v4.app.Fragment;

import com.utilsframework.android.navdrawer.FragmentFactory;
import com.utilsframework.android.navdrawer.TabsAdapter;

public class MainMenuFragmentsFactory implements FragmentFactory {
    @Override
    public Fragment createFragmentBySelectedItem(int selectedItemId, int tabIndex,
                                                 int navigationLevel) {
        return new Fragment();
    }

    @Override
    public void initTab(int currentSelectedItem,
                        int tabIndex, int navigationLevel,
                        TabsAdapter.Tab tab) {

    }

    @Override
    public int getTabsCount(int selectedItemId, int navigationLevel) {
        return 1;
    }
}
