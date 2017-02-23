package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.captumia.R;
import com.captumia.data.Region;
import com.utilsframework.android.adapters.SingleViewArrayAdapter;
import com.utilsframework.android.adapters.SpinnerAdapter;

/**
 * Created by stikhonenko on 2/23/17.
 */
public class SelectRegionAdapter extends SpinnerAdapter<Region> {
    public SelectRegionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getHint() {
        return R.string.select_region;
    }

    @Override
    public String getTextOfElement(Region region) {
        return region.getName();
    }
}
