package com.captumia.ui.imgtransform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class PostImageTransformation extends RoundedCornersImageTransformation {
    public PostImageTransformation(Context context) {
        super(context);
    }

    @Override
    protected void onPostTransform(Paint paint, Paint darkTintPaint,
                                   float radius, int height,
                                   Canvas canvas,
                                   RectF rect) {
        rect.top = height - radius;
        canvas.drawRect(rect, paint);
        canvas.drawRect(rect, darkTintPaint);
    }

    @Override
    public String key() {
        return "post";
    }
}