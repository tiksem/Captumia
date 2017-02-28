package com.captumia.ui.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.captumia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageHolder {
    @BindView(R.id.description)
    public TextView description;
    @BindView(R.id.title)
    public TextView title;
    @BindView(R.id.selection)
    public View selection;

    public PackageHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
