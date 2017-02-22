package com.captumia.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.squareup.picasso.Picasso;
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
        GuiUtilities.executeWhenViewMeasuredUsingLoop(holder.image, new Runnable() {
            @Override
            public void run() {
                ImageView imageView = holder.image;
                picasso.load(post.getMedia().getDisplayInListUrl()).
                        resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).
                        placeholder(R.drawable.post_image_placeholder).
                        centerCrop().
                        transform(transformation).
                        into(imageView);
            }
        });
    }

    public static void fillPostImage(final PostViewHolder holder,
                                     final Post post,
                                     final Transformation transformation) {
        fillPostImage(Picasso.with(holder.image.getContext()), holder, post, transformation);
    }
}
