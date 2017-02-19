package com.captumia.ui;

import android.support.v4.app.Fragment;

import com.captumia.R;
import com.utilsframework.android.navdrawer.NoTabsFragmentFactory;

import java.util.HashMap;
import java.util.Map;

public class MainMenuFragmentsFactory extends NoTabsFragmentFactory {
    private static final Map<Integer, Integer> MENU_ID_CATEGORY_ID_MAP = new HashMap<>();

    static {
        MENU_ID_CATEGORY_ID_MAP.put(R.id.accessories, 72);
        MENU_ID_CATEGORY_ID_MAP.put(R.id.fashion, 61);
        MENU_ID_CATEGORY_ID_MAP.put(R.id.makeup_artist, 71);
        MENU_ID_CATEGORY_ID_MAP.put(R.id.photographers, 44);
        MENU_ID_CATEGORY_ID_MAP.put(R.id.places, 63);
    }

    @Override
    public Fragment createFragmentBySelectedItem(int selectedItem) {
        if (selectedItem == R.id.logo) {
            return new HomeFragment();
        }

        int categoryId = MENU_ID_CATEGORY_ID_MAP.get(selectedItem);
        return PostsByCategoryFragment.create(categoryId);
    }
}
