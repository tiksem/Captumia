package com.captumia.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.captumia.R;
import com.captumia.ui.adapters.HomeAdapter;
import com.captumia.ui.adapters.items.HomeItem;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.ListViewFragment;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends ListViewFragment<HomeItem> {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) getListView();
        View header = View.inflate(getContext(), R.layout.home_header, null);
        listView.addHeaderView(header);

        ImageView background = (ImageView) header.findViewById(R.id.background);
        Picasso.with(getContext()).load(R.drawable.home_header_background)
                .placeholder(R.drawable.home_header_placeholder)
                .into(background);
    }

    @Override
    protected ViewArrayAdapter<HomeItem, ?> createAdapter() {
        return new HomeAdapter(getContext());
    }

    @Override
    protected List<HomeItem> createList() {
        return Collections.emptyList();
    }

    @Override
    protected void onListItemClicked(HomeItem item, int position) {

    }
}
