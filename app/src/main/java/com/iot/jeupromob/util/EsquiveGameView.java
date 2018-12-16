package com.iot.jeupromob.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.iot.jeupromob.R;

/**
 * TODO: document your custom view class.
 */
public class EsquiveGameView extends View {
    private final static int M_OBJECT_COLOR = Color.BLACK;
    private final static int M_BACKGROUND_COLOR = Color.WHITE;

    private Canvas mCanvas;
    private Bitmap mBitmap;

    public EsquiveGameView(Context context){
        super(context);

    }

    public EsquiveGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setColor(M_COLOR);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setXfermode(null);
//        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.save();
        mCanvas.drawColor(M_BACKGROUND_COLOR);
    }
}
