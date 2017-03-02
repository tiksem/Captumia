package com.captumia.network.reviews;

import com.captumia.data.Review;
import com.captumia.network.BaseLazyLoadingList;
import com.captumia.network.RestApiClient;
import com.utils.framework.KeyProvider;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.List;

import retrofit2.Call;

public class ReviewsLazyLoadingList extends BaseLazyLoadingList<Review> {
    private int postId;

    public ReviewsLazyLoadingList(RetrofitRequestManager requestManager,
                                  RestApiClient restApiClient, int postId) {
        super(requestManager, restApiClient);
        this.postId = postId;
    }

    @Override
    protected Call<List<Review>> createLoadPageCall(int itemsPerPage) {
        return getRestApiClient().getReviews(postId, getLoadedPagesCount() + 1, itemsPerPage);
    }

    @Override
    protected KeyProvider<Object, Review> getKeyProvider() {
        return new KeyProvider<Object, Review>() {
            @Override
            public Object getKey(Review value) {
                return value.getId();
            }
        };
    }
}
