package com.captumia.app;

import android.content.Context;

import com.captumia.network.LoginRequiredHandler;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;
import com.utilsframework.android.network.retrofit.RetrofitRequestManagerFactory;
import com.utilsframework.android.network.retrofit.RetrofitRequestManagerResponseErrorInterceptor;

import java.util.HashSet;
import java.util.Set;

class MyRetrofitRequestManagerFactory implements RetrofitRequestManagerFactory {
    private Context context;

    public MyRetrofitRequestManagerFactory(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }

        this.context = context;
        errorInterceptors = new HashSet<>();

        {
            errorInterceptors.add(new LoginRequiredHandler(context));
        }
    }

    private Set<RetrofitRequestManagerResponseErrorInterceptor> errorInterceptors;

    @Override
    public RetrofitRequestManager createRequestManager() {
        AppRequestManager requestManager = new AppRequestManager();
        requestManager.setErrorInterceptors(errorInterceptors);
        return requestManager;
    }
}
