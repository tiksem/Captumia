package com.captumia.network;

import android.content.Context;
import android.content.Intent;

import com.captumia.events.LogoutEvent;
import com.captumia.ui.forms.LoginActivity;
import com.utilsframework.android.network.retrofit.LoginResponseErrorInterceptor;

import org.greenrobot.eventbus.EventBus;

public class LoginRequiredHandler extends LoginResponseErrorInterceptor {
    private Context context;

    public LoginRequiredHandler(Context context) {
        this.context = context;
    }

    @Override
    protected void onLoginRequired() {
        EventBus.getDefault().post(new LogoutEvent());
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
}
