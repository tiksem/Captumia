package com.captumia.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.ui.PostImageTransformation;
import com.captumia.ui.UiUtils;
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
        PostImageTransformation transformation = new PostImageTransformation(view.getContext());
        UiUtils.fillPostImage(picasso, holder, post, transformation);
        UiUtils.fillPostExcludingImage(holder, post);
    }
}
