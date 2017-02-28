package com.captumia.ui.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.network.HomeLazyLoadingList;
import com.captumia.ui.UiUtils;
import com.utils.framework.collections.LazyLoadingList;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;
import com.utilsframework.android.view.listview.ListViews;

public class HomeFragment extends CategoriesFragment {
    private EditText searchEditText;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) getListView();
        View header = ListViews.addHeader(listView, R.layout.home_header);
        UiUtils.setupHeaderBackground(header);

        searchEditText = (EditText) view.findViewById(R.id.search_edit);
        view.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButtonClicked();
            }
        });

        ListViews.addHeader(listView, R.layout.home_sub_header);
    }

    private void onSearchButtonClicked() {
        String filter = searchEditText.getText().toString();
        getNavigationInterface().replaceFragment(SearchPostsFragment.create(filter), 1);
    }

    @Override
    protected LazyLoadingList<Category> getLazyLoadingList(RetrofitRequestManager requestManager,
                                                           String filter) {
        return new HomeLazyLoadingList(requestManager, getRestApiClient());
    }
}
