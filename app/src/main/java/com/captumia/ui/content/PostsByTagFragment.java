package com.captumia.ui.content;

import android.content.Context;

import com.captumia.data.Post;
import com.captumia.network.PostsByTagLazyLoadingList;
import com.captumia.ui.BasePostsFragment;
import com.utils.framework.collections.LazyLoadingList;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

public class PostsByTagFragment extends BasePostsFragment {
    public static final String TAG_ID = "tagId";

    private int tagId;

    public static PostsByTagFragment create(int tagId) {
        return Fragments.createFragmentWith1Arg(new PostsByTagFragment(), TAG_ID,
                tagId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tagId = getArguments().getInt(TAG_ID);
    }

    @Override
    protected LazyLoadingList<Post> getLazyLoadingList(RetrofitRequestManager requestManager,
                                                       String filter) {
        return new PostsByTagLazyLoadingList(requestManager, getRestApiClient(), tagId);
    }
}
