package com.captumia;

import android.app.Application;

import com.captumia.network.RestApiClient;
import com.utilsframework.android.network.retrofit.OkHttpBaseAuthHandler;
import com.utilsframework.android.network.retrofit.RetrofitTemplates;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class CaptumiaApplication extends Application {
    private static CaptumiaApplication instance;

    private RestApiClient restApiClient;
    private OkHttpBaseAuthHandler okHttpBaseAuthHandler;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        initRestApiClient();
    }

    private void initRestApiClient() {
        Retrofit.Builder builder = RetrofitTemplates.generateRetrofitWithJackson(
                RestApiClient.BASE_URL);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        okHttpBaseAuthHandler = new OkHttpBaseAuthHandler(clientBuilder, this);
        Retrofit retrofit = builder.client(
                clientBuilder.build()).build();

        restApiClient = retrofit.create(RestApiClient.class);
    }

    public OkHttpBaseAuthHandler getOkHttpBaseAuthHandler() {
        return okHttpBaseAuthHandler;
    }

    public RestApiClient getRestApiClient() {
        return restApiClient;
    }

    public static CaptumiaApplication getInstance() {
        return instance;
    }
}
