package com.captumia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.captumia.R;

public class BusinessRegisterActivity extends RequestManagerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, BusinessRegisterActivity.class);
        context.startActivity(intent);
    }
}
