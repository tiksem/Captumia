package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.adapters.navigation.LazyLoadingListAdapter;

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
    protected void reuseView(Post post,
                             PostViewHolder postViewHolder,
                             int position,
                             View view) {
        picasso.load(post.getMedia().getDisplayInListUrl()).into(postViewHolder.image);
    }
}
