package com.captumia;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.captumia.network.RestApiClient;
import com.squareup.picasso.Picasso;
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
        OkHttpClient.Builder clientBuilder = RetrofitTemplates.generateClientWithLogging();
        okHttpBaseAuthHandler = new OkHttpBaseAuthHandler(clientBuilder, this);
        Retrofit retrofit = builder.client(
                clientBuilder.build()).build();

        restApiClient = retrofit.create(RestApiClient.class);

        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.i(Picasso.class.getSimpleName(), "loading image " + uri + " failed", exception);
            }
        });
        Picasso.setSingletonInstance(picassoBuilder.build());
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
