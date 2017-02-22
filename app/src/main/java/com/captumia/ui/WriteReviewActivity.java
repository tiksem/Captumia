package com.captumia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.captumia.R;

public class WriteReviewActivity extends RequestManagerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review_activity);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WriteReviewActivity.class);
        context.startActivity(intent);
    }
}
