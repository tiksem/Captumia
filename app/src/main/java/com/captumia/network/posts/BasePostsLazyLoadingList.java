package com.captumia.network.posts;

import com.captumia.data.Post;
import com.captumia.network.BaseLazyLoadingList;
import com.captumia.network.RestApiClient;
import com.utils.framework.KeyProvider;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public abstract class BasePostsLazyLoadingList extends BaseLazyLoadingList<Post> {
    public BasePostsLazyLoadingList(RetrofitRequestManager requestManager,
                                    RestApiClient restApiClient) {
        super(requestManager, restApiClient);
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
