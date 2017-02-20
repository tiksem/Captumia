package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.ui.adapters.holders.CategoryViewHolder;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.adapters.navigation.LazyLoadingListAdapter;

public class CategoryAdapter extends LazyLoadingListAdapter<Category, CategoryViewHolder> {
    private final Picasso picasso;

    public CategoryAdapter(Context context) {
        super(context);
        picasso = Picasso.with(context);
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.category;
    }

    @Override
    protected CategoryViewHolder createViewHolder(View view) {
        return new CategoryViewHolder(view);
    }

    @Override
    protected void reuseView(Category category, CategoryViewHolder holder,
                             int position, View view) {
        holder.name.setText(category.getName());
    }
}
