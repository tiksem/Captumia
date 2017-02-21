package com.captumia.ui.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.ui.CircleTransform;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.view.GuiUtilities;

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
    protected void reuseView(final String url, final ImageView imageView, int position, View view) {
        GuiUtilities.executeWhenViewMeasured(imageView, new Runnable() {
            @Override
            public void run() {
                picasso.load(url).placeholder(R.drawable.photo_gallery_placeholder)
                        .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(imageView);
            }
        });
    }
}
