package com.captumia.ui.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.captumia.R;

public class CategoryViewHolder {
    public TextView name;
    public ImageView image;

    public CategoryViewHolder(View view) {
        name = (TextView) view.findViewById(R.id.name);
        image = (ImageView) view.findViewById(R.id.image);
    }
}
