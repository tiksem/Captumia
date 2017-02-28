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
import android.widget.TableRow;
import android.widget.TextView;

import com.captumia.app.CaptumiaApplication;
import com.captumia.R;
import com.captumia.data.Media;
import com.captumia.data.OperatingHoursItem;
import com.captumia.data.Post;
import com.captumia.data.Tag;
import com.captumia.network.CreateAndPublishServiceRequest;
import com.captumia.network.RestApiClient;
import com.captumia.network.ServicePublicationListener;
import com.captumia.ui.forms.AddServiceActivityInterface;
import com.captumia.ui.forms.BookServiceActivity;
import com.captumia.ui.UiUtils;
import com.captumia.ui.forms.WriteReviewActivity;
import com.captumia.ui.adapters.PhotoGalleryAdapter;
import com.captumia.ui.adapters.holders.PostViewHolder;
import com.captumia.ui.imgtransform.DarkenImageTransformation;
import com.squareup.picasso.Picasso;
import com.utils.framework.Lists;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.fragments.RequestManagerFragment;
import com.utilsframework.android.navdrawer.NavigationActivityInterface;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;
import com.utilsframework.android.view.Alerts;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

public class ServiceFragment extends RequestManagerFragment implements ServicePublicationListener {
    public static final String POST = "post";
    public static final String PREVIEW_MODE = "previewMode";

    private Post post;
    private View publishButton;

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

        View bookButton = view.findViewById(R.id.book_now);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookServiceActivity.start(getContext(), post.getId());
            }
        });

        View reviewButton = view.findViewById(R.id.write_a_review_button);
        reviewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onWriteReviewTap();
                    }
                });

        setupTags(view);

        publishButton = view.findViewById(R.id.publish);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPublish();
            }
        });

        if (isPreviewMode()) {
            GuiUtilities.setVisibility(View.GONE, bookButton, reviewButton);
            publishButton.setVisibility(View.VISIBLE);
        } else {
            GuiUtilities.setVisibility(View.VISIBLE, bookButton, reviewButton);
            publishButton.setVisibility(View.GONE);
        }

        View operatingHoursContainer = view.findViewById(R.id.operation_hours);
        List<OperatingHoursItem> operationHours = post.getOperationHours();
        if (Lists.isEmpty(operationHours)) {
            operatingHoursContainer.setVisibility(View.GONE);
        } else {
            operatingHoursContainer.setVisibility(View.VISIBLE);
            List<TableRow> rows = GuiUtilities.getAllChildrenRecursive(operatingHoursContainer,
                    TableRow.class);
            for (OperatingHoursItem item : operationHours) {
                int day = item.getDay();
                TableRow tableRow = rows.get(day);
                tableRow.setVisibility(View.VISIBLE);
                TextView timeView = (TextView) tableRow.getChildAt(1);
                timeView.setText(item.getStartTime() + " - " + item.getEndTime());
            }
        }
    }

    private void onPublish() {
        publishButton.setEnabled(false);
        AddServiceActivityInterface activityInterface = getActivityInterface();
        CreateAndPublishServiceRequest request = new CreateAndPublishServiceRequest(getContext(),
                getRestApiClient(), getRequestManager(),
                activityInterface.getSelectedPackage(),
                activityInterface.isUserPackage());
        request.setServicePublicationListener(this);
        request.execute(post);
    }

    @Override
    public void onServicePublished() {
        Alerts.showOkButtonAlert(getContext().getApplicationContext(),
                R.string.service_submitted_message);
        getActivity().finish();
        publishButton.setEnabled(true);
    }

    @Override
    public void onServicePublicationError() {
        getActivity().onBackPressed();
        publishButton.setEnabled(true);
    }

    private boolean isPreviewMode() {
        return Fragments.getBoolean(this, PREVIEW_MODE);
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
                if (!isPreviewMode()) {
                    tagView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PostsByTagFragment fragment = PostsByTagFragment.create(tag.getId());
                            getNavigationInterface().replaceFragment(fragment, 1);
                        }
                    });
                }
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
        Media media = post.getMedia();
        if (media != null) {
            String displayInListUrl = media.getDisplayInListUrl();
            if (!displayInListUrl.startsWith("http")) {
                displayInListUrl = "file://" + displayInListUrl;
            }

            UiUtils.loadImageWithCenterCrop(Picasso.with(context).load(
                    displayInListUrl).
                    transform(new DarkenImageTransformation(getContext()))
                    .placeholder(R.drawable.rect_image_placeholder), holder.image);
        }
    }

    private void setupPhotoGallery(View view, Context context) {
        AbsListView photosListView = (AbsListView) view.findViewById(R.id.photo_gallery_list);
        PhotoGalleryAdapter adapter = new PhotoGalleryAdapter(context);
        adapter.setElements(post.getPhotos());
        photosListView.setAdapter(adapter);
    }

    public static ServiceFragment create(Post post, boolean previewMode) {
        Bundle args = new Bundle();
        args.putParcelable(POST, post);
        args.putBoolean(PREVIEW_MODE, previewMode);
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        post = Fragments.getParcelable(this, POST);
    }

    @Override
    protected RetrofitRequestManager obtainRequestManager() {
        return CaptumiaApplication.getInstance().getRequestManagerFactory().createRequestManager();
    }

    public NavigationActivityInterface getNavigationInterface() {
        return (NavigationActivityInterface) getActivity();
    }

    public RestApiClient getRestApiClient() {
        return CaptumiaApplication.getInstance().getRestApiClient();
    }

    public AddServiceActivityInterface getActivityInterface() {
        return (AddServiceActivityInterface) getActivity();
    }
}
