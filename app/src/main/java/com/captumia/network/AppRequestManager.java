package com.captumia.network;

import com.fasterxml.jackson.core.JsonFactory;
import com.utilsframework.android.json.Json;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.io.IOException;

import retrofit2.Response;

public class AppRequestManager extends RetrofitRequestManager {
    @Override
    protected <T> Object getHttpResponseExceptionData(Response<T> response) throws IOException {
        String body = response.errorBody().string();
        return Json.fromString(body).get("message");
    }
}
