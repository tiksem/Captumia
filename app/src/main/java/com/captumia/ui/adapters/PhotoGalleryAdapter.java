package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.ui.imgtransform.CircleTransform;
import com.captumia.ui.UiUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.utilsframework.android.adapters.ViewArrayAdapter;

public class PhotoGalleryAdapter extends ViewArrayAdapter<String, ImageView> {
    private final Picasso picasso;

    public PhotoGalleryAdapter(Context context) {
        super(context);
        picasso = Picasso.with(context);
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.photo_gallery_item;
    }

    @Override
    protected ImageView createViewHolder(View view) {
        return (ImageView) view.findViewById(R.id.image);
    }

    @Override
    protected void reuseView(String url, final ImageView imageView, int position, View view) {
        if (!url.startsWith("http")) {
            url = "file://" + url;
        }

        RequestCreator requestCreator = picasso.load(url).
                placeholder(R.drawable.photo_gallery_placeholder).
                transform(new CircleTransform());
        UiUtils.loadImageWithCenterCrop(requestCreator, imageView);
    }
}
