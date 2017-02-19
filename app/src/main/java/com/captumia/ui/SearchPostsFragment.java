package com.captumia.ui;

import android.content.Context;

import com.captumia.data.Post;
import com.captumia.network.SearchPostsLazyLoadingList;
import com.utils.framework.collections.LazyLoadingList;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public class SearchPostsFragment extends BasePostsFragment {
    private static final String FILTER = "filter";

    private String filter;

    public static SearchPostsFragment create(String filter) {
        return Fragments.createFragmentWith1Arg(new SearchPostsFragment(), FILTER, filter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        filter = getArguments().getString(FILTER);
    }

    @Override
    protected String getInitialFilter() {
        return filter;
    }

    @Override
    protected LazyLoadingList<Post> getLazyLoadingList(RetrofitRequestManager requestManager,
                                                       String filter) {
        return new SearchPostsLazyLoadingList(getRequestManager(),
                getRestApiClient(), filter);
    }
}
