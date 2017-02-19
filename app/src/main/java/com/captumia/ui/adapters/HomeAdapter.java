package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;

import com.captumia.R;
import com.captumia.ui.adapters.holders.HomeItemViewHolder;
import com.captumia.ui.adapters.items.HomeItem;
import com.utilsframework.android.adapters.ViewArrayAdapter;

public class HomeAdapter extends ViewArrayAdapter<HomeItem, HomeItemViewHolder> {
    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.post;
    }

    @Override
    protected HomeItemViewHolder createViewHolder(View view) {
        return new HomeItemViewHolder();
    }

    @Override
    protected void reuseView(HomeItem homeItem, HomeItemViewHolder homeItemViewHolder,
                             int position, View view) {

    }
}
