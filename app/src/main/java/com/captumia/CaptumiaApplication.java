package com.captumia;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.captumia.events.LoginEvent;
import com.captumia.events.LogoutEvent;
import com.captumia.network.RestApiClient;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.network.retrofit.OkHttpTokenAuthHandler;
import com.utilsframework.android.network.retrofit.RetrofitTemplates;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class CaptumiaApplication extends Application {
    private static CaptumiaApplication instance;

    private RestApiClient restApiClient;
    private OkHttpTokenAuthHandler authHandler;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        initRestApiClient();
        setupPicasso();
    }

    private void initRestApiClient() {
        Retrofit.Builder builder = RetrofitTemplates.generateRetrofitWithJackson(
                RestApiClient.BASE_URL);
        OkHttpClient.Builder clientBuilder = RetrofitTemplates.generateClientWithLogging();
        authHandler = new OkHttpTokenAuthHandler(clientBuilder, this);
//        clientBuilder.addInterceptor(new ReceivedCookiesInterceptor(this));
//        clientBuilder.addInterceptor(new AddCookiesInterceptor(this));
        //JavaNetCookieJar javaNetCookieJar = new JavaNetCookieJar(CookieHandler.getDefault());
        clientBuilder.cookieJar(new PersistentCookieJar(
                new SetCookieCache(), new SharedPrefsCookiePersistor(this)));
        Retrofit retrofit = builder.client(
                clientBuilder.build()).build();

        restApiClient = retrofit.create(RestApiClient.class);
    }

    private void setupPicasso() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.i(Picasso.class.getSimpleName(), "loading image " + uri + " failed", exception);
            }
        });
        Picasso.setSingletonInstance(picassoBuilder.build());
    }

    public OkHttpTokenAuthHandler getAuthHandler() {
        return authHandler;
    }

    public RestApiClient getRestApiClient() {
        return restApiClient;
    }

    public static CaptumiaApplication getInstance() {
        return instance;
    }

    public void logout() {
        authHandler.logout();
        EventBus.getDefault().post(new LogoutEvent());
    }

    public void login(String token) {
        authHandler.login(token);
        EventBus.getDefault().post(new LoginEvent());
    }
}
