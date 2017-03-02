package com.captumia.ui.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.captumia.R;
import com.captumia.data.Review;
import com.utils.framework.Lists;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

/**
 * Created by stikhonenko on 3/2/17.
 */
public class ReviewHolder {
    public TextView userName;
    public TextView message;
    public TextView date;
    public List<List<ImageView>> rating;

    public ReviewHolder(View view) {
        userName = (TextView) view.findViewById(R.id.user_name);
        message = (TextView) view.findViewById(R.id.message);
        date = (TextView) view.findViewById(R.id.date);

        View ratingLayout = view.findViewById(R.id.rating_layout);
        List<ImageView> starViews = GuiUtilities.getAllChildrenRecursive(
                ratingLayout, ImageView.class);
        rating = Lists.separateIntoListsWithSize(Review.MAX_RATING_VALUE, starViews);
    }
}
