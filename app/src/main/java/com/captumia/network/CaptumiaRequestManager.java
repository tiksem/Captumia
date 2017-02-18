package com.captumia.network;

import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.io.IOException;

import retrofit2.Response;

public class CaptumiaRequestManager extends RetrofitRequestManager<ErrorData> {
    @Override
    protected ErrorData createErrorData(Response response) {
        return new ErrorData();
    }
}
