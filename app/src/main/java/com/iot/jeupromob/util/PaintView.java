package com.iot.jeupromob.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class PaintView extends View {
    private final static int M_BRUSH_SIZE = 20;
    private final static int M_COLOR = Color.BLACK;
    private static final int M_BACKGROUND_COLOR = Color.WHITE;
    private static final float M_TOUCH_TOLERANCE = 4;

    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    public ArrayList<FingerPath> mFingerPaths = new ArrayList<>();
    private int height;
    private int width;
    private Point mOrigin;
    private int size;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private TextView accX;
    private TextView accY;

    public PaintView(Context context) {
        super(context);
    }

    private class Point{
        int x;int y;
        public Point(int x, int y){this.x = x; this.y = y;}
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(M_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics, TextView x, TextView y) {
        //Récupération de la taille de la View
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        mOrigin = new Point(width / 2, height / 2);
        int minimumSize;
        int maximumSize;

        //Détermination de la taille aléatoire de la forme
        if(height > width){
            maximumSize = height;
            Double minimumSizeDouble = height * 0.3;
            minimumSize = minimumSizeDouble.intValue();
        }else{
            Double minimumSizeDouble = width * 0.3;
            minimumSize = minimumSizeDouble.intValue();
            maximumSize = width;
        }

        do{
            size = Random.randomNumber(maximumSize);
        }while(size < minimumSize);

        //Création du canvas
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        accX = x;
        accY = y;
    }

    public void drawCircle(){
        ArrayList<Path> mCirclePath = new ArrayList<>();

        boolean mFinish = false;
        do{
           // int mX =
        }while(!mFinish);
    }

    public void drawSquare(){
        //Point firstPoint =
    }

    public void clear() {
        mFingerPaths.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("awwer", "draw");
        canvas.save();
        mCanvas.drawColor(M_BACKGROUND_COLOR);

        for (FingerPath fp : mFingerPaths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(fp.path, mPaint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        mPath = new Path();
        FingerPath fp = new FingerPath(M_COLOR, false, false, M_BRUSH_SIZE, mPath);
        mFingerPaths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= M_TOUCH_TOLERANCE || dy >= M_TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

        accX.setText("X : " + mX );
        accY.setText("Y : " + mY);
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

}
