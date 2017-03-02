package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.captumia.R;
import com.captumia.data.Review;
import com.captumia.ui.RequestManagerActivity;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

public class WriteReviewActivity extends RequestManagerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review_activity);
        setupRating();
    }

    private void setupRating() {
        View ratingLayout = findViewById(R.id.rating_layout);
        final List<ImageView> stars = GuiUtilities.getAllChildrenRecursive(ratingLayout,
                ImageView.class);
        int index = 0;
        for (ImageView star : stars) {
            final int finalIndex = index;
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int starIndex = finalIndex % Review.MAX_RATING_VALUE;
                    int firstStarIndex = finalIndex - starIndex;
                    for (int i = 0; i < Review.MAX_RATING_VALUE; i++) {
                        ImageView starToFill = stars.get(i + firstStarIndex);
                        if (i <= starIndex) {
                            starToFill.setImageResource(R.drawable.ic_star);
                        } else {
                            starToFill.setImageResource(R.drawable.ic_star_empty);
                        }
                    }
                }
            });
            index++;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WriteReviewActivity.class);
        context.startActivity(intent);
    }
}
