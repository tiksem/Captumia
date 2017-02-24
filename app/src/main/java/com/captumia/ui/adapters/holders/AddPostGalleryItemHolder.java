package com.captumia.ui.adapters.holders;

import android.view.View;
import android.widget.ImageView;

import com.captumia.R;

public class AddPostGalleryItemHolder {
    public ImageView imageView;
    public View deleteImageButton;

    public AddPostGalleryItemHolder(View view) {
        imageView = (ImageView) view.findViewById(R.id.image);
        deleteImageButton = view.findViewById(R.id.delete_image);
    }
}
