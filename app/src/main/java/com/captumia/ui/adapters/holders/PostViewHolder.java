package com.captumia.ui.adapters.holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.captumia.R;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

public class PostViewHolder {
    public List<ImageView> rating;
    public ImageView image;
    public TextView title;
    public TextView phone;
    public View contacts;

    public PostViewHolder(View view) {
        image = (ImageView) view.findViewById(R.id.image);
        ViewGroup ratingContainerView = (ViewGroup) view.findViewById(R.id.rating);
        rating = GuiUtilities.getChildrenAsList(ratingContainerView, ImageView.class);
        title = (TextView) view.findViewById(R.id.title);
        phone = (TextView) view.findViewById(R.id.phone);
        contacts = view.findViewById(R.id.contacts);
    }
}
