package com.captumia.network.posts;

import com.captumia.data.Post;
import com.captumia.network.RestApiClient;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class PostsByCategoryLazyLoadingList extends BasePostsLazyLoadingList {
    private int categoryId;

    public PostsByCategoryLazyLoadingList(RetrofitRequestManager requestManager,
                                          RestApiClient restApiClient, int categoryId) {
        super(requestManager, restApiClient);
        this.categoryId = categoryId;
    }

    @Override
    protected Call<List<Post>> createLoadPageCall(int itemsPerPage) {
        return getRestApiClient().getPostsByCategory(categoryId, getLoadedPagesCount() + 1, itemsPerPage);
    }
}
