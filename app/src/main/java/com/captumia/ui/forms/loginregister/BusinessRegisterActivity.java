package com.captumia.ui.forms.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.captumia.R;
import com.captumia.ui.RequestManagerActivity;

public class BusinessRegisterActivity extends RequestManagerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_business_activity);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, BusinessRegisterActivity.class);
        context.startActivity(intent);
    }
}
