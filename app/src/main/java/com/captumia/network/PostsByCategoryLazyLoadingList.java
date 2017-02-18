package com.captumia.network;

import com.captumia.data.Post;
import com.utils.framework.KeyProvider;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class PostsByCategoryLazyLoadingList extends BaseLazyLoadingList<Post> {
    private int categoryId;

    public PostsByCategoryLazyLoadingList(RetrofitRequestManager requestManager,
                                          RestApiClient restApiClient, int categoryId) {
        super(requestManager, restApiClient);
        this.categoryId = categoryId;
    }

    @Override
    protected Call<List<Post>> createLoadPageCall(int itemsPerPage) {
        return getRestApiClient().getPosts(categoryId, getLoadedPagesCount() + 1, itemsPerPage);
    }

    @Override
    protected KeyProvider<Object, Post> getKeyProvider() {
        return new KeyProvider<Object, Post>() {
            @Override
            public Object getKey(Post value) {
                return value.getId();
            }
        };
    }
}
