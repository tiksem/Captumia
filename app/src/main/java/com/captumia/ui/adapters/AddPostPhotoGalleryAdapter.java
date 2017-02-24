package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;

import com.captumia.R;
import com.captumia.ui.UiUtils;
import com.captumia.ui.adapters.holders.AddPostGalleryItemHolder;
import com.captumia.ui.imgtransform.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.utilsframework.android.adapters.ViewArrayAdapter;

import java.io.File;

public class AddPostPhotoGalleryAdapter extends ViewArrayAdapter<File, AddPostGalleryItemHolder> {
    private final Picasso picasso;

    public AddPostPhotoGalleryAdapter(Context context) {
        super(context);
        picasso = Picasso.with(context);
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.add_post_gallery_item;
    }

    @Override
    protected AddPostGalleryItemHolder createViewHolder(View view) {
        return new AddPostGalleryItemHolder(view);
    }

    @Override
    protected void reuseView(final File url, AddPostGalleryItemHolder holder,
                             final int position, View view) {
        RequestCreator requestCreator = picasso.load(url).
                placeholder(R.drawable.photo_gallery_placeholder).
                transform(new CircleTransform());
        UiUtils.loadImageWithCenterCrop(requestCreator, holder.imageView);

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemAt(position);
            }
        });
    }
}
