package com.captumia;

import android.app.Application;
import android.util.Log;

import com.captumia.network.RestApiClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CaptumiaApplication extends Application {
    private static final String REQUEST_LOGGING_TAG = "Retrofit";

    private static CaptumiaApplication instance;

    private RestApiClient restApiClient;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        initRestApiClient();
    }

    private void initRestApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(REQUEST_LOGGING_TAG, message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(interceptor).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonConverterFactory factory = JacksonConverterFactory.create(objectMapper);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApiClient.BASE_URL)
                .client(client)
                .addConverterFactory(factory)
                .build();

        restApiClient = retrofit.create(RestApiClient.class);
    }

    public RestApiClient getRestApiClient() {
        return restApiClient;
    }

    public static CaptumiaApplication getInstance() {
        return instance;
    }
}
