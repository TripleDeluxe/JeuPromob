package com.iot.jeupromob.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.iot.jeupromob.R;
import com.iot.jeupromob.activity.ShapeGameFragment;

import java.util.ArrayList;

public class PaintView extends View {
    private final static int M_BRUSH_SIZE = 20;
    private final static int M_COLOR = Color.BLACK;
    private static final int M_BACKGROUND_COLOR = Color.WHITE;
    private static final int M_SHAPE_COLOR = Color.GRAY;
    private static final float M_TOUCH_TOLERANCE = 4;
    private static final int M_MINIMUM_MARGIN = 100;
    private static final int M_PRECISION = 100;
    private static final int M_TOLERANCE = 10;
    private static final int M_OBJECTIVE_COLOR = Color.RED;
    private static final int M_OBJECTIVE_RADIUS = 40;

    private ShapeGameFragment mShapeGameFragment;
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
    private int minimumSize;
    private int maximumSize;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    //Dit si la forme à dessiner a été dessiner
    private boolean mIsShapeDrawed = false;
    private Path mShapeToDraw = new Path();
    private ArrayList<Point> mShapeCoordinates = new ArrayList<>();
    private ArrayList<Point> mUserCoordinates = new ArrayList<>();
    private ArrayList<Point> mShapeObjectives = new ArrayList<>();
    private int mObjectiveIndex = 0;
    private boolean isObjectiveReached = false;

    private MediaPlayer soundBadAnswer = null;
    private MediaPlayer soundGoodAnswer = null;

    public PaintView(Context context) {
        super(context);
    }

    private class Point{
        int x;int y;
        public Point(int x, int y){this.x = x; this.y = y;}
    }

    private class FlaotPoint {
        float x, y;

        public FlaotPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
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

    public void init(ShapeGameFragment shapeGameFragment) {
        mShapeGameFragment = shapeGameFragment;

        soundBadAnswer = MediaPlayer.create(getContext(), R.raw.mauvaise_reponse);
        soundGoodAnswer = MediaPlayer.create(getContext(), R.raw.bonne_reponse);

        //Récupération de la taille de la View
        height = getHeight();
        width = getWidth();
        mOrigin = new Point(width / 2, height / 2);

        //Détermination de la taille aléatoire de la forme
        if(height > width){
            maximumSize = height - M_BRUSH_SIZE;
            Double minimumSizeDouble = height * 0.3;
            minimumSize = minimumSizeDouble.intValue();
            }else{
            Double minimumSizeDouble = width * 0.3;
            minimumSize = minimumSizeDouble.intValue();
            maximumSize = width - M_BRUSH_SIZE;
        }

        do{
            size = Random.randomNumber(maximumSize - M_BRUSH_SIZE - M_MINIMUM_MARGIN);
        }while(size < minimumSize);

        //Création du canvas
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }



    public void drawSquare(){
        int secondSize;
        Point firstPoint;
        Point secondPoint;
        Point thirdPoint;
        Point fourthPoint;

        //Détermination de la secondSize selon que l'on fasse un carré ou un rectangle, 1/2 de faire un carré
        if(Random.randomNumber(1) > 0){
            secondSize = size;
        }else{
            do {
                secondSize = Random.randomNumber(maximumSize - M_BRUSH_SIZE);
            }while(secondSize < minimumSize && secondSize >= size);
        }

        //Attribution de la secondSize a l'axe le plus grand)
        if(height > width){
            firstPoint = new Point(mOrigin.x - (size/2), mOrigin.y + (secondSize/2));
            secondPoint = new Point(mOrigin.x - (size/2), mOrigin.y - (secondSize/2));
            thirdPoint = new Point(mOrigin.x + (size/2), mOrigin.y - (secondSize/2));
            fourthPoint = new Point(mOrigin.x + (size/2), mOrigin.y + (secondSize/2));
        }else{
            firstPoint = new Point(mOrigin.x - (secondSize/2), mOrigin.y + (size/2));
            secondPoint = new Point(mOrigin.x - (secondSize/2), mOrigin.y - (size/2));
            thirdPoint = new Point(mOrigin.x + (secondSize/2), mOrigin.y - (size/2));
            fourthPoint = new Point(mOrigin.x + (secondSize/2), mOrigin.y + (size/2));
        }

        mShapeObjectives.add(firstPoint);
        mShapeObjectives.add(secondPoint);
        mShapeObjectives.add(thirdPoint);
        mShapeObjectives.add(fourthPoint);
        mShapeObjectives.add(firstPoint);

        mShapeToDraw.moveTo(firstPoint.x, firstPoint.y);
        mShapeToDraw.lineTo(secondPoint.x, secondPoint.y);
        mShapeToDraw.lineTo(thirdPoint.x, thirdPoint.y);
        mShapeToDraw.lineTo(fourthPoint.x, fourthPoint.y);
        mShapeToDraw.lineTo(firstPoint.x, firstPoint.y);
    }

    public void drawTriangle(){
        Point firstPoint;
        Point secondPoint;
        Point thirdPoint;

        //1/2 d'etre vers le haut ou vers le bas
        if(Random.randomNumber(1) == 0){
            firstPoint = new Point(mOrigin.x, mOrigin.y - (size/2));
            secondPoint = new Point(mOrigin.x - (size/2), mOrigin.y + (size/2));
            thirdPoint = new Point(mOrigin.x + (size/2), mOrigin.y + (size/2));
        }else{
            firstPoint = new Point(mOrigin.x, mOrigin.y + (size/2));
            secondPoint = new Point(mOrigin.x - (size/2), mOrigin.y - (size/2));
            thirdPoint = new Point(mOrigin.x + (size/2), mOrigin.y - (size/2));
        }

        mShapeObjectives.add(firstPoint);
        mShapeObjectives.add(secondPoint);
        mShapeObjectives.add(thirdPoint);
        mShapeObjectives.add(firstPoint);

        mShapeToDraw.moveTo(firstPoint.x, firstPoint.y);
        mShapeToDraw.lineTo(secondPoint.x, secondPoint.y);
        mShapeToDraw.lineTo(thirdPoint.x, thirdPoint.y);
        mShapeToDraw.lineTo(firstPoint.x, firstPoint.y);
    }

    public int calculateScore(){
        int score = 1000;
        FlaotPoint[] shapePoints = getPoints(mShapeToDraw);
        FlaotPoint[] userPoints = getPoints(mFingerPaths.get(0).path);

        Log.d("yyyyy", "shape  " + shapePoints.length);
        Log.d("yyyyy", "user  " + userPoints.length);

        for(int i=0; i < M_PRECISION; i++){
            double dist = Math.sqrt(Math.pow((double) userPoints[i].x - shapePoints[i].x, 2) + Math.pow((double) userPoints[i].y - shapePoints[i].y, 2) );
            if(dist > M_TOLERANCE){
                score -= dist;
            }
        }

        return score;
    }

    private FlaotPoint[] getPoints(Path path) {
        FlaotPoint[] pointArray = new FlaotPoint[M_PRECISION];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / M_PRECISION;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < M_PRECISION)) {
            // get point from the path
            pm.getPosTan(distance, aCoordinates, null);
            pointArray[counter] = new FlaotPoint(aCoordinates[0], aCoordinates[1]);
            counter++;
            distance = distance + speed;
        }

        return pointArray;
    }

    public void clearUser(){
        mFingerPaths.clear();
        mUserCoordinates.clear();
        mObjectiveIndex = 0;
        invalidate();
    }

    public void clear(){
        mShapeToDraw.reset();
        mShapeCoordinates.clear();
        mFingerPaths.clear();
        mUserCoordinates.clear();
        invalidate();
    }

    private boolean checkIfObjectiveReached(float x, float y){
        double dist = Math.sqrt(Math.pow((double) x - mShapeObjectives.get(mObjectiveIndex).x, 2) + Math.pow((double) y - mShapeObjectives.get(mObjectiveIndex).y, 2) );
        if(dist < M_OBJECTIVE_RADIUS){
            isObjectiveReached = true;
            mObjectiveIndex++;
            soundGoodAnswer.start();

            if(mObjectiveIndex == mShapeObjectives.size()){
                mShapeGameFragment.nextRound();
            }

            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(M_BACKGROUND_COLOR);
        mPaint.setStrokeWidth(M_BRUSH_SIZE);
        mPaint.setMaskFilter(null);

        mPaint.setColor(M_SHAPE_COLOR);
        mCanvas.drawPath(mShapeToDraw, mPaint);

        Path mObjective = new Path();
        //mObjective.moveTo();
        mObjective.addCircle(mShapeObjectives.get(mObjectiveIndex).x, mShapeObjectives.get(mObjectiveIndex).y, M_OBJECTIVE_RADIUS, Path.Direction.CW);
        mPaint.setColor(M_OBJECTIVE_COLOR);
        mCanvas.drawPath(mObjective, mPaint);

        for (FingerPath fp : mFingerPaths) {
            mPaint.setColor(fp.color);
            mCanvas.drawPath(fp.path, mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        if(mFingerPaths.size() > 1){
            clearUser();
        }
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        FingerPath fp = new FingerPath(M_COLOR, false, false, M_BRUSH_SIZE, mPath);
        mFingerPaths.add(fp);

        if(checkIfObjectiveReached(x, y)){

        }else{
            soundBadAnswer.start();
        }
    }

    private void touchMove(float x, float y) {
        checkIfObjectiveReached(x, y);

        if(isObjectiveReached){
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);

            if (dx >= M_TOUCH_TOLERANCE || dy >= M_TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

    }

    private void touchUp() {
        clearUser();
        mObjectiveIndex = 0;
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

