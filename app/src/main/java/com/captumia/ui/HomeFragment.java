package com.captumia.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.captumia.R;
import com.captumia.ui.adapters.HomeAdapter;
import com.captumia.ui.adapters.items.HomeItem;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.ListViewFragment;
import com.utilsframework.android.navdrawer.NavigationActivityInterface;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends ListViewFragment<HomeItem> {

    private EditText searchEditText;

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

        searchEditText = (EditText) view.findViewById(R.id.search_edit);
        view.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButtonClicked();
            }
        });
    }

    private void onSearchButtonClicked() {
        String filter = searchEditText.getText().toString();
        getNavigationInterface().replaceFragment(SearchPostsFragment.create(filter), 1);
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

    public NavigationActivityInterface getNavigationInterface() {
        return (NavigationActivityInterface) getActivity();
    }
}
