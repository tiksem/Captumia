package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.captumia.R;
import com.captumia.ui.RequestManagerActivity;

public class LoginActivity extends RequestManagerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
