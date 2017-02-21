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
import com.utils.framework.strings.Strings;
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
        Context context = getContext();

        setupHeader(view, context);
        setupPhotoGallery(view, context);
        setupDescription(view);

        view.findViewById(R.id.book_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookServiceActivity.start(getContext(), post.getId());
            }
        });
    }

    private void setupDescription(View view) {
        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        View descriptionContainer = view.findViewById(R.id.descriptionContainer);
        String description = post.getDescription();
        if (Strings.isEmpty(description)) {
            descriptionContainer.setVisibility(View.GONE);
        } else {
            //description = Strings.removeFromStart(description, "<p>");
            description = Strings.removeFromEnd(description, "\n");
            descriptionContainer.setVisibility(View.VISIBLE);
            Spanned descriptionHtml = Html.fromHtml(description);
            descriptionView.setText(descriptionHtml);
        }
    }

    private void setupHeader(View view, Context context) {
        PostViewHolder holder = new PostViewHolder(view);
        UiUtils.fillPostExcludingImage(holder, post);
        UiUtils.fillPostImage(Picasso.with(context), holder, post,
                new DarkenImageTransformation(context));
    }

    private void setupPhotoGallery(View view, Context context) {
        AbsListView photosListView = (AbsListView) view.findViewById(R.id.photo_gallery_list);
        PhotoGalleryAdapter adapter = new PhotoGalleryAdapter(context);
        adapter.setElements(post.getPhotos());
        photosListView.setAdapter(adapter);
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
