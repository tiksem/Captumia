package com.captumia.app;

import android.content.Context;

import com.captumia.network.RestApiClient;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.utils.framework.CollectionUtils;
import com.utils.framework.Predicate;
import com.utilsframework.android.network.retrofit.Cookies;
import com.utilsframework.android.network.retrofit.RetrofitTemplates;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class NetworkHandler {
    private final MyRetrofitRequestManagerFactory requestManagerFactory;
    private final RestApiClient restApiClient;
    private final SharedPrefsCookiePersistor persistedCookies;
    private List<Cookie> additionalCookies = new ArrayList<>();

    public NetworkHandler(Context context) {
        Retrofit.Builder builder = RetrofitTemplates.generateRetrofitWithJackson(
                RestApiClient.BASE_URL);
        OkHttpClient.Builder clientBuilder = RetrofitTemplates.generateClientWithLogging();
        persistedCookies = new SharedPrefsCookiePersistor(context);

        PersistentCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(), persistedCookies);
        clientBuilder.cookieJar(cookieJar);
        Retrofit retrofit = builder.client(
                clientBuilder.build()).build();

        restApiClient = retrofit.create(RestApiClient.class);
        requestManagerFactory = new MyRetrofitRequestManagerFactory(context);
    }

    public boolean isLoggedIn() {
        return getLoginCookie() != null;
    }

    public void logout() {
        Cookie loginCookie = getLoginCookie();
        persistedCookies.removeAll(Collections.singletonList(loginCookie));
    }

    private Cookie getLoginCookie() {
        List<Cookie> cookies = persistedCookies.loadAll();
        return CollectionUtils.find(cookies, new Predicate<Cookie>() {
            @Override
            public boolean check(Cookie item) {
                return item.name().startsWith("wordpress_logged_in_");
            }
        });
    }

    public MyRetrofitRequestManagerFactory getRequestManagerFactory() {
        return requestManagerFactory;
    }

    public RestApiClient getRestApiClient() {
        return restApiClient;
    }

    public void addAdditionalCookie(Cookie cookie) {
        additionalCookies.add(cookie);
    }

    public void removeAdditionalCookies(Cookie cookie) {
        additionalCookies.remove(cookie);
    }

    public static Cookie keyValueCookie(String key, String value) {
        return new Cookie.Builder()
                .domain(RestApiClient.BASE_URL)
                .path("/")
                .name(key)
                .value(value)
                .secure()
                .build();
    }
}
