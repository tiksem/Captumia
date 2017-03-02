package com.captumia.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.app.CaptumiaApplication;
import com.captumia.data.Review;
import com.captumia.data.User;
import com.captumia.ui.adapters.holders.ReviewHolder;
import com.utilsframework.android.adapters.navigation.LazyLoadingListAdapter;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

public class ReviewsAdapter extends LazyLoadingListAdapter<Review, ReviewHolder> {
    public ReviewsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.review_item;
    }

    @Override
    protected ReviewHolder createViewHolder(View view, int itemViewType) {
        return new ReviewHolder(view);
    }

    @Override
    protected void reuseView(Review review, ReviewHolder holder, int position, View view) {
        holder.message.setText(review.getMessage());
        holder.userName.setText(review.getAuthorName());
        holder.date.setText(view.getResources().getString(R.string.date_ago, review.getDate()));

        User user = CaptumiaApplication.getInstance().getUser();
        int drawable = 0;
        if (user != null && user.getId() == review.getAuthorId()) {
            drawable = R.drawable.ic_edit;
        }
        holder.userName.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);

        List<Integer> ratingList = review.getRating();
        for (int i = 0; i < Review.RATING_CATEGORIES_COUNT; i++) {
            int rating = ratingList.get(i);
            for (int j = 0; j < Review.MAX_RATING_VALUE; j++) {
                ImageView starView = holder.rating.get(i).get(j);
                starView.setImageResource(j < rating ? R.drawable.ic_star :
                        R.drawable.ic_star_empty);
            }
        }
    }
}
