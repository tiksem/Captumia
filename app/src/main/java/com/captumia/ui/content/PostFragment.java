package com.captumia.ui.content;

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
import com.captumia.data.Tag;
import com.captumia.network.AppRequestManager;
import com.captumia.ui.forms.BookServiceActivity;
import com.captumia.ui.UiUtils;
import com.captumia.ui.forms.WriteReviewActivity;
import com.captumia.ui.adapters.PhotoGalleryAdapter;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.captumia.ui.imgtransform.DarkenImageTransformation;
import com.squareup.picasso.Picasso;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.fragments.RequestManagerFragment;
import com.utilsframework.android.navdrawer.NavigationActivityInterface;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

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

        view.findViewById(R.id.write_a_review_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onWriteReviewTap();
                    }
                });

        setupTags(view);
    }

    private void setupTags(View view) {
        ViewGroup tagsView = (ViewGroup) view.findViewById(R.id.tags);
        List<Tag> tags = post.getTags();
        if (tags.isEmpty()) {
            tagsView.setVisibility(View.GONE);
        } else {
            tagsView.setVisibility(View.VISIBLE);
            for (final Tag tag : tags) {
                View tagView = createTagView(tag);
                tagView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostsByTagFragment fragment = PostsByTagFragment.create(tag.getId());
                        getNavigationInterface().replaceFragment(fragment, 1);
                    }
                });
                tagsView.addView(tagView);
            }
        }
    }

    private View createTagView(Tag tag) {
        TextView tagView = (TextView) View.inflate(getContext(), R.layout.tag, null);
        tagView.setText(tag.getName());
        return tagView;
    }

    private void onWriteReviewTap() {
        WriteReviewActivity.start(getContext());
    }

    private void setupDescription(View view) {
        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        View descriptionContainer = view.findViewById(R.id.descriptionContainer);
        String description = post.getDescription();
        if (Strings.isEmpty(description)) {
            descriptionContainer.setVisibility(View.GONE);
        } else {
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

    public NavigationActivityInterface getNavigationInterface() {
        return (NavigationActivityInterface) getActivity();
    }
}
