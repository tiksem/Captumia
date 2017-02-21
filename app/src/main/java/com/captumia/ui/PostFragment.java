package com.captumia.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.captumia.R;
import com.captumia.data.Post;
import com.captumia.network.AppRequestManager;
import com.captumia.ui.adapters.PhotoGalleryAdapter;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.fragments.RequestManagerFragment;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public class PostFragment extends RequestManagerFragment {
    public static final String POST = "post";
    private Post post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_page, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PostViewHolder holder = new PostViewHolder(view);
        UiUtils.fillPostExcludingImage(holder, post);
        Context context = getContext();
        UiUtils.fillPostImage(Picasso.with(context), holder, post,
                new DarkenImageTransformation(context));

        AbsListView photosListView = (AbsListView) view.findViewById(R.id.photo_gallery_list);
        PhotoGalleryAdapter adapter = new PhotoGalleryAdapter(context);
        adapter.setElements(post.getPhotos());
        photosListView.setAdapter(adapter);

        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        Spanned description = Html.fromHtml(post.getDescription());
        descriptionView.setText(description);
    }

    public static PostFragment create(Post post) {
        return Fragments.createFragmentWith1Arg(new PostFragment(), POST, post);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        post = Fragments.getParcelable(this, POST);
    }

    @Override
    protected RetrofitRequestManager obtainRequestManager() {
        return new AppRequestManager();
    }
}
