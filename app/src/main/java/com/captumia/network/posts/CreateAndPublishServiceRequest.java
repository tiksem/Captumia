package com.captumia.network.posts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.captumia.R;
import com.captumia.app.CaptumiaApplication;
import com.captumia.app.NetworkHandler;
import com.captumia.data.OperatingHoursItem;
import com.captumia.data.Post;
import com.captumia.data.Tag;
import com.captumia.network.RestApiClient;
import com.captumia.network.ServicePublicationListener;
import com.utils.framework.ArrayUtils;
import com.utils.framework.CollectionUtils;
import com.utils.framework.Lists;
import com.utils.framework.Transformer;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.network.CancelStrategy;
import com.utilsframework.android.network.ProgressDialogRequestListener;
import com.utilsframework.android.network.RequestListener;
import com.utilsframework.android.network.retrofit.RequestBodies;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;
import com.utilsframework.android.threading.MainThreadExecutor;
import com.utilsframework.android.view.Alerts;
import com.utilsframework.android.view.Toasts;
import com.utilsframework.android.web.WebViewActivity;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class CreateAndPublishServiceRequest {
    private Activity activity;
    private RestApiClient restApiClient;
    private RetrofitRequestManager requestManager;
    private ServicePublicationListener servicePublicationListener;

    public CreateAndPublishServiceRequest(Activity activity, RestApiClient restApiClient,
                                          RetrofitRequestManager requestManager,
                                          int selectedPackage, boolean userPackage) {
        this.activity = activity;
        this.restApiClient = restApiClient;
        this.requestManager = requestManager;
    }

    public void execute(Post post) {
        List<Integer> tags = CollectionUtils.transform(post.getTags(),
                new Transformer<Tag, Integer>() {
                    @Override
                    public Integer get(Tag tag) {
                        return tag.getId();
                    }
                });

        Map<String, RequestBody> operatingHours = new HashMap<>();
        for (OperatingHoursItem item : post.getOperationHours()) {
            // days are started from 0 - Saturday. But in request Saturday - 6, Sunday - 0
            int day = item.getDay();
            if (day == 0) {
                day = 6;
            } else {
                day--;
            }

            String format = "job_hours[%d][%s]";
            String startKey = String.format(format, day, "start");
            String endKey = String.format(format, day, "end");
            operatingHours.put(startKey, RequestBodies.fromString(item.getStartTime()));
            operatingHours.put(endKey, RequestBodies.fromString(item.getEndTime()));
        }

        List<String> photos = post.getPhotos();
        MultipartBody.Part[] galleryImages = new MultipartBody.Part[0];
        if (!Lists.isEmpty(photos)) {
            galleryImages = new MultipartBody.Part[photos.size()];
            for (int i = 0; i < galleryImages.length; i++) {
                galleryImages[i] = RequestBodies.imagePartFromFilePath("gallery_images[]",
                        photos.get(i));
            }
        }

        Integer region = post.getRegion() != null ? post.getRegion().getId() : null;

        MultipartBody.Part coverImage = RequestBodies.imagePartFromFilePath("featured_image",
                post.getCoverImage());
        Call<Post> call = restApiClient.createService(post.getTitle(),
                region,
                post.getEmail(),
                Arrays.asList(post.getCategory().getId()),
                tags,
                operatingHours,
                coverImage,
                galleryImages,
                post.getDescription(),
                post.getWebsite(),
                post.getPhone(),
                post.getVideoUrl());


        RequestListener<Post, Throwable> listener =
                new ProgressDialogRequestListener<Post, Throwable>(
                        activity, R.string.saving_changes) {
                    @Override
                    public void onSuccess(Post responseBody) {
                        onServiceCreatingFinished(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasts.toast(activity, e.getMessage());
                        executeErrorListener();
                    }
                };
        requestManager.executeCall(call, listener,
                CancelStrategy.INTERRUPT);
    }

    private void onServiceCreatingFinished(Post post) {
        if (servicePublicationListener != null) {
            servicePublicationListener.onServicePublished();
        }
    }

    public void setServicePublicationListener(ServicePublicationListener servicePublicationListener) {
        this.servicePublicationListener = servicePublicationListener;
    }

    private void executeErrorListener() {
        if (servicePublicationListener != null) {
            servicePublicationListener.onServicePublicationError();
        }
    }
}
