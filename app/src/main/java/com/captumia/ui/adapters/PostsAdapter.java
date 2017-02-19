package com.captumia.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.ui.PostImageTransformation;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.squareup.picasso.Picasso;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.adapters.navigation.LazyLoadingListAdapter;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

public class PostsAdapter extends LazyLoadingListAdapter<Post, PostViewHolder> {
    private final Picasso picasso;

    public PostsAdapter(Context context) {
        super(context);
        picasso = Picasso.with(context);
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.post;
    }

    @Override
    protected PostViewHolder createViewHolder(View view) {
        return new PostViewHolder(view);
    }

    @Override
    protected void reuseView(final Post post,
                             final PostViewHolder holder,
                             int position,
                             View view) {
        GuiUtilities.executeWhenViewMeasured(holder.image, new Runnable() {
            @Override
            public void run() {
                ImageView imageView = holder.image;
                picasso.load(post.getMedia().getDisplayInListUrl()).
                        resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).
                        placeholder(R.drawable.post_image_placeholder).
                        centerCrop().
                        transform(new PostImageTransformation(imageView.getContext())).
                        into(imageView);
            }
        });
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
}
