package com.captumia.ui.forms;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.utilsframework.android.fragments.OneFragmentActivity;

public class AddServiceActivity extends OneFragmentActivity
        implements AddServiceActivityInterface {
    private int selectedPackage;
    private boolean isUserPackage;

    @Override
    protected Fragment createInitialFragment(Intent intent) {
        return new PackagesListFragment();
    }

    @Override
    public int getSelectedPackage() {
        return selectedPackage;
    }

    @Override
    public void setSelectedPackage(int selectedPackage, boolean isUserPackage) {
        this.selectedPackage = selectedPackage;
        this.isUserPackage = isUserPackage;
    }

    @Override
    public boolean isUserPackage() {
        return isUserPackage;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddServiceActivity.class);
        context.startActivity(intent);
    }
}
