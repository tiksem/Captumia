package com.captumia.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.captumia.R;
import com.squareup.picasso.Transformation;
import com.utilsframework.android.view.MeasureUtils;

public class RoundedCornersImageTransformation implements Transformation {
    private static final int RADIUS_IN_DP = 5;

    private Context context;

    public RoundedCornersImageTransformation(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        Paint darkTintPaint = new Paint();
        darkTintPaint.setColor(context.getResources().getColor(R.color.postDarkTint));

        float radius = MeasureUtils.convertDpToPixel(RADIUS_IN_DP, context);
        int height = source.getHeight();
        Bitmap output = Bitmap.createBitmap(source.getWidth(), height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        RectF rect = new RectF(0, 0, source.getWidth(), height);
        canvas.drawRoundRect(rect, radius, radius, paint);
        canvas.drawRoundRect(rect, radius, radius, darkTintPaint);
        onPostTransform(paint, darkTintPaint, radius, height, canvas, rect);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    protected void onPostTransform(Paint paint, Paint darkTintPaint,
                                 float radius, int height,
                                 Canvas canvas,
                                 RectF rect) {
    }

    @Override
    public String key() {
        return "rounded";
    }
}
