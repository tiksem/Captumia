package com.captumia.network;

import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.io.IOException;

import retrofit2.Response;

public class AppRequestManager extends RetrofitRequestManager {
    @Override
    protected <T> Object getHttpResponseExceptionData(Response<T> response) throws IOException {
        return null;
    }
}
