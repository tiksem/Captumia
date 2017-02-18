package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.lib.RoundedTransformation;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.adapters.navigation.LazyLoadingListAdapter;
import com.utilsframework.android.view.GuiUtilities;

public class PostsAdapter extends LazyLoadingListAdapter<Post, PostViewHolder> {
    private static final int POST_IMAGE_CORNERS_RADIUS = 20;

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
                             final PostViewHolder postViewHolder,
                             int position,
                             View view) {
        GuiUtilities.executeWhenViewMeasured(postViewHolder.image, new Runnable() {
            @Override
            public void run() {
                ImageView imageView = postViewHolder.image;
                picasso.load(post.getMedia().getDisplayInListUrl()).
                        resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).
                        centerCrop().
                        transform(new RoundedTransformation(POST_IMAGE_CORNERS_RADIUS, 0)).
                        into(imageView);
            }
        });
    }
}
