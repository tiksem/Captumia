package com.captumia.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.captumia.R;
import com.utilsframework.android.view.MeasureUtils;

// enables hardware accelerated rounded corners
// original idea here : http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/
public class PostImageTransformation implements com.squareup.picasso.Transformation {
    private static final int RADIUS_IN_DP = 5;

    private Context context;

    // radius is corner radii in dp
    // margin is the board in dp
    public PostImageTransformation(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        float radius = MeasureUtils.convertDpToPixel(RADIUS_IN_DP, context);
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
        Paint darkTintPaint = new Paint();
        darkTintPaint.setColor(context.getResources().getColor(R.color.postDarkTint));
        canvas.drawRoundRect(rect, radius, radius, darkTintPaint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}