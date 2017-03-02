package com.captumia.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.data.Review;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

public class UiUtils {
    public static void fillPostExcludingImage(PostViewHolder holder, Post post) {
        holder.title.setText(post.getTitle());

        holder.contacts.setVisibility(View.GONE);

        String phone = post.getPhone();
        if (phone != null) {
            holder.phone.setText(phone);
            GuiUtilities.setVisibility(View.VISIBLE, holder.phone, holder.contacts);
        } else {
            holder.phone.setVisibility(View.GONE);
        }

        List<String> addresses = post.getAddress();
        if (addresses.isEmpty()) {
            holder.address.setVisibility(View.GONE);
        } else {
            String address = TextUtils.join("\n", addresses);
            holder.address.setText(address);
            GuiUtilities.setVisibility(View.VISIBLE, holder.address, holder.contacts);
        }
    }

    public static void fillPostImage(final Picasso picasso,
                                     final PostViewHolder holder,
                                     final Post post,
                                     final Transformation transformation) {
        loadImageWithCenterCrop(picasso.load(post.getMedia().getDisplayInListUrl()).
                        transform(transformation).placeholder(R.drawable.post_image_placeholder),
                holder.image);
    }

    public static void loadImageWithCenterCrop(final RequestCreator picassoRequestCreator,
                                               final ImageView imageView) {
        GuiUtilities.executeWhenViewMeasuredUsingLoop(imageView, new Runnable() {
            @Override
            public void run() {
                picassoRequestCreator.
                        resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).
                        centerCrop().
                        into(imageView);
            }
        });
    }

    public static void setupHeaderBackground(View header) {
        ImageView background = (ImageView) header.findViewById(R.id.background);
        Picasso.with(background.getContext()).load(R.drawable.home_header_background)
                .placeholder(R.drawable.rect_image_placeholder)
                .into(background);
    }

    public static void fillPostRating(List<ImageView> stars, int rating) {
        for (int i = 0; i < Review.MAX_RATING_VALUE; i++) {
            int drawable = i < rating ? R.drawable.ic_star : R.drawable.ic_star_empty;
            stars.get(i).setImageResource(drawable);
        }
    }
}
