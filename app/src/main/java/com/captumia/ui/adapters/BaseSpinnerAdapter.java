package com.captumia.ui.adapters;

import android.content.Context;

import com.captumia.R;
import com.utilsframework.android.adapters.SpinnerAdapter;

public abstract class BaseSpinnerAdapter<T> extends SpinnerAdapter<T> {
    public BaseSpinnerAdapter(Context context) {
        super(context);
    }

    @Override
    public int getHintTextColor() {
        return R.color.greyText;
    }

    @Override
    public int getTextColor() {
        return R.color.greyHeaderText;
    }
}
