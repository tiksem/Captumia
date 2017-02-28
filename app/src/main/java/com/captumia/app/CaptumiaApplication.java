package com.captumia.app;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.captumia.events.LoginEvent;
import com.captumia.events.LogoutEvent;
import com.captumia.network.RestApiClient;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.network.retrofit.RetrofitRequestManagerFactory;

import org.greenrobot.eventbus.EventBus;

public class CaptumiaApplication extends Application {
    public static final int IMAGES_CACHE_SIZE = 1024 * 1024 * 20;

    private static CaptumiaApplication instance;

    private NetworkHandler networkHandler;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        networkHandler = new NetworkHandler(this);
        setupPicasso();

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void setupPicasso() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.i(Picasso.class.getSimpleName(), "loading image " + uri + " failed", exception);
            }
        });
        picassoBuilder.memoryCache(new LruCache(IMAGES_CACHE_SIZE));
        Picasso.setSingletonInstance(picassoBuilder.build());
    }

    public RestApiClient getRestApiClient() {
        return networkHandler.getRestApiClient();
    }

    public static CaptumiaApplication getInstance() {
        return instance;
    }

    public void logout() {
        networkHandler.logout();
        EventBus.getDefault().post(new LogoutEvent());
    }

    public void login() {
        EventBus.getDefault().post(new LoginEvent());
    }

    public RetrofitRequestManagerFactory getRequestManagerFactory() {
        return networkHandler.getRequestManagerFactory();
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
