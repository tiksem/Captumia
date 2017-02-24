package com.captumia.ui.imgtransform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.captumia.R;
import com.squareup.picasso.Transformation;

public class DarkenImageTransformation implements Transformation {
    private Context context;

    public DarkenImageTransformation(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        Paint darkTintPaint = new Paint();
        darkTintPaint.setColor(context.getResources().getColor(R.color.postDarkTint));

        int height = source.getHeight();
        Bitmap output = Bitmap.createBitmap(source.getWidth(), height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        RectF rect = new RectF(0, 0, source.getWidth(), height);
        canvas.drawRect(rect, paint);
        canvas.drawRect(rect, darkTintPaint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return "dark";
    }
}
