package com.captumia.network;

import com.captumia.data.Post;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class PostsByTagLazyLoadingList extends BasePostsLazyLoadingList {
    private int tagId;

    public PostsByTagLazyLoadingList(RetrofitRequestManager requestManager,
                                     RestApiClient restApiClient, int tagId) {
        super(requestManager, restApiClient);
        this.tagId = tagId;
    }

    @Override
    protected Call<List<Post>> createLoadPageCall(int itemsPerPage) {
        return getRestApiClient().getPostsByTag(tagId, getLoadedPagesCount() + 1, itemsPerPage);
    }
}
