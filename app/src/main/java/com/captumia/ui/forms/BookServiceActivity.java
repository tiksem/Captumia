package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.captumia.R;
import com.captumia.ui.RequestManagerActivity;

public class BookServiceActivity extends RequestManagerActivity {

    public static final String SERVICE_ID = "serviceId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_service);
    }

    public static void start(Context context, int serviceId) {
        Intent intent = new Intent(context, BookServiceActivity.class);
        intent.putExtra(SERVICE_ID, serviceId);
        context.startActivity(intent);
    }
}
