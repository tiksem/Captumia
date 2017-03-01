package com.captumia.app;

import android.content.Context;

import com.captumia.network.RestApiClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkHandler {
    public static final String LOGGED_IN_COOKIE_TOKEN = "wordpress_logged_in_";

    private final MyRetrofitRequestManagerFactory requestManagerFactory;
    private final RestApiClient restApiClient;
    private final SharedPrefsCookiePersistor persistedCookies;

    public NetworkHandler(Context context) {
        OkHttpClient.Builder clientBuilder = RetrofitTemplates.generateClientWithLogging();
        persistedCookies = new SharedPrefsCookiePersistor(context);

        PersistentCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(), persistedCookies);
        clientBuilder.cookieJar(cookieJar);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.
                create(objectMapper);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApiClient.BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(jacksonConverterFactory).build();

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

    public SharedPrefsCookiePersistor getPersistedCookies() {
        return persistedCookies;
    }

    private Cookie getLoginCookie() {
        List<Cookie> cookies = persistedCookies.loadAll();
        return CollectionUtils.find(cookies, new Predicate<Cookie>() {
            @Override
            public boolean check(Cookie item) {
                return item.name().startsWith(LOGGED_IN_COOKIE_TOKEN);
            }
        });
    }

    public MyRetrofitRequestManagerFactory getRequestManagerFactory() {
        return requestManagerFactory;
    }

    public RestApiClient getRestApiClient() {
        return restApiClient;
    }

    public static Cookie keyValueCookie(String key, String value) {
        return new Cookie.Builder()
                .domain(RestApiClient.DOMAIN)
                .path("/")
                .name(key)
                .value(value)
                .secure()
                .build();
    }
}
