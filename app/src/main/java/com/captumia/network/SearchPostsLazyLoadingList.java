package com.captumia.network;

import com.captumia.data.Post;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class SearchPostsLazyLoadingList extends BasePostsLazyLoadingList {
    private String filter;

    public SearchPostsLazyLoadingList(RetrofitRequestManager requestManager,
                                      RestApiClient restApiClient, String filter) {
        super(requestManager, restApiClient);
        this.filter = filter;
    }

    @Override
    protected Call<List<Post>> createLoadPageCall(int itemsPerPage) {
        return getRestApiClient().searchPosts(filter, getLoadedPagesCount() + 1, itemsPerPage);
    }
}
