package com.app.infideap.notificationapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;

import com.amulyakhare.textdrawable.TextDrawable;
import com.app.infideap.stylishwidget.view.Stylish;

/**
 * Created by Shiburagi on 24/01/2017.
 */
public class IconTextDrawable extends Drawable {

    private final String text;
    private final Paint paint;
    private final Bitmap bitmap;
    private final Context context;

    public IconTextDrawable(Context context, String text, int drawable) {
        this.context = context;
        this.text = text;
        this.bitmap = getBitmapFromVectorDrawable(context, drawable);

        this.paint = new Paint();
        paint.setTextSize(22f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    public void draw(Canvas canvas) {
        int sizeX = -DimensionConverter.stringToDimensionPixelSize("18dp", context.getResources().getDisplayMetrics());
        int sizeY = -DimensionConverter.stringToDimensionPixelSize("12dp", context.getResources().getDisplayMetrics());

        canvas.drawBitmap(bitmap, sizeX, sizeY, paint);
        paint.setColor(Color.RED);
//        canvas.drawCircle(bitmap.getWidth()-6, bitmap.getHeight()/2-6, 12, paint);
        paint.setColor(Color.YELLOW);
//        canvas.drawText(text, bitmap.getWidth(), bitmap.getHeight()/2, paint);
        int textSize = DimensionConverter.stringToDimensionPixelSize("12sp", context.getResources().getDisplayMetrics());
        int width = 25 + textSize * text.length() / 2;
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Stylish.getInstance().bold(context))
                .fontSize(textSize) /* size in px */
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRoundRect(text, ContextCompat.getColor(context, R.color.colorDeepOrange_500), textSize);
        canvas.drawBitmap(convertToBitmap(drawable, width, 25 + textSize / 3),
                bitmap.getWidth() - width + sizeX, sizeY, paint);
//        bitmap.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }
}