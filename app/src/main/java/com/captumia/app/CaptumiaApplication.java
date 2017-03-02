package com.captumia.app;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.captumia.data.User;
import com.captumia.events.LoginEvent;
import com.captumia.events.LogoutEvent;
import com.captumia.network.RestApiClient;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.network.retrofit.RetrofitRequestManagerFactory;
import com.utilsframework.android.serialization.SerialisationUtils;
import com.utilsframework.android.threading.Threading;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class CaptumiaApplication extends Application {
    public static final int IMAGES_CACHE_SIZE = 1024 * 1024 * 20;
    public static final String TAG = CaptumiaApplication.class.getSimpleName();

    private static final String SAVE_USER_FILE = "_user";

    private static CaptumiaApplication instance;

    private NetworkHandler networkHandler;
    private User user;

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

        try {
            user = SerialisationUtils.readObjectFromFileInDataDir(this, SAVE_USER_FILE);
        } catch (IOException e) {
            Log.e(TAG, "reading user failed", e);
        }
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
        user = null;
        EventBus.getDefault().post(new LogoutEvent());
    }

    public void login(final User user) {
        this.user = user;
        Threading.runOnBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    SerialisationUtils.writeObjectToFileInDataDir(instance, SAVE_USER_FILE, user);
                } catch (IOException e) {
                    Log.e(TAG, "user saving failed", e);
                }
            }
        });
        EventBus.getDefault().post(new LoginEvent());
    }

    public RetrofitRequestManagerFactory getRequestManagerFactory() {
        return networkHandler.getRequestManagerFactory();
    }

    public User getUser() {
        return user;
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
