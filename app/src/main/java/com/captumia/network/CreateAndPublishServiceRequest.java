package com.captumia.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.captumia.R;
import com.captumia.data.OperatingHoursItem;
import com.captumia.data.Post;
import com.captumia.data.Tag;
import com.utils.framework.CollectionUtils;
import com.utils.framework.Transformer;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.network.CancelStrategy;
import com.utilsframework.android.network.RequestListener;
import com.utilsframework.android.network.retrofit.RequestBodies;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;
import com.utilsframework.android.view.Toasts;
import com.utilsframework.android.web.WebViewActivity;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class CreateAndPublishServiceRequest {
    public static final String PUBLISH_STEP = "2";
    public static final String PREVIEW_STEP = "1";
    public static final String FORM_NAME = "submit-job";
    public static final String DEFAULT_JOB_ID = "0";

    private Context context;
    private RestApiClient restApiClient;
    private RetrofitRequestManager requestManager;
    private ProgressDialog progressDialog;
    private ServicePublicationListener servicePublicationListener;

    public CreateAndPublishServiceRequest(Context context, RestApiClient restApiClient,
                                          RetrofitRequestManager requestManager) {
        this.context = context;
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

        List<File> photos = CollectionUtils.transform(post.getPhotos(),
                new Transformer<String, File>() {
                    @Override
                    public File get(String s) {
                        return new File(s);
                    }
                });

        Integer region = post.getRegion() != null ? post.getRegion().getId() : null;

        File coverImage = post.getCoverImage() != null ? new File(post.getCoverImage()) : null;
        Call<ResponseBody> call = restApiClient.createService(post.getTitle(),
                region,
                post.getEmail(),
                Arrays.asList(post.getCategory().getId()),
                tags,
                operatingHours,
                coverImage,
                photos,
                post.getDescription(),
                post.getWebsite(),
                post.getPhone(),
                post.getVideoUrl(),
                DEFAULT_JOB_ID,
                FORM_NAME,
                PREVIEW_STEP);


        progressDialog = ProgressDialog.show(context, null, context.getString(R.string.saving_changes));
        RequestListener<ResponseBody, Throwable> listener =
                new RequestListener<ResponseBody, Throwable>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        onServiceCreatingFinished(responseBody);
                    }

                    @Override
                    public void onCanceled() {
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasts.toast(context, e.getMessage());
                        progressDialog.hide();
                        executeErrorListener();
                    }
                };
        requestManager.executeCall(call, listener,
                CancelStrategy.INTERRUPT);
    }

    private void onServiceCreatingFinished(ResponseBody responseBody) {
        try {
            String body = responseBody.string();
            WebViewActivity.loadHtml(context, body);
            String error = Strings.getFirstStringBetweenQuotes(body,
                    "class=\"job-manager-error\">", "</");
            if (!Strings.isEmpty(error)) {
                Toasts.toast(context, error);
                progressDialog.hide();
                executeErrorListener();
            } else {
                onServiceCreated(body);
            }
        } catch (IOException e) {
            Toasts.toast(context, e.getMessage());
            progressDialog.hide();
            executeErrorListener();
        }
    }

    public void setServicePublicationListener(ServicePublicationListener servicePublicationListener) {
        this.servicePublicationListener = servicePublicationListener;
    }

    private void onServiceCreated(String body) {
        String jobId = Strings.getFirstStringBetweenQuotes(body, "name=\"job_id\" value=\"", "\" />");
        if (jobId == null || jobId.equals("0")) {
            Toasts.toast(context, R.string.unknown_error);
            progressDialog.hide();
            return;
        }
        Call<ResponseBody> call = restApiClient.publishService(jobId, PUBLISH_STEP, FORM_NAME);
        requestManager.executeCall(call, new RequestListener<ResponseBody, Throwable>() {
            @Override
            public void onAfterCompleteOrCanceled() {
                progressDialog.hide();
            }

            @Override
            public void onError(Throwable e) {
                Toasts.toast(context, R.string.unknown_error);
                executeErrorListener();
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                if (servicePublicationListener != null) {
                    servicePublicationListener.onServicePublished();
                }
            }
        }, CancelStrategy.INTERRUPT);
    }

    private void executeErrorListener() {
        if (servicePublicationListener != null) {
            servicePublicationListener.onServicePublicationError();
        }
    }
}
