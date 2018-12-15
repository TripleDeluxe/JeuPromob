package com.iot.jeupromob.util;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import static com.iot.jeupromob.util.Monster.globalMonster;


public class MonsterView extends View {

    private Paint paint;

    public MonsterView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
        // selection de la couleur
        paint = new Paint();
        switch(globalMonster.getColor()){
            case(0): paint.setColor(Color.BLACK);
            case(1): paint.setColor(Color.RED);
            case(2): paint.setColor(Color.WHITE);
            case(3): paint.setColor(Color.GREEN);
        }

    }

    /*
    public void clearMonster(){
        Canvas.drawColor(Color.TRANSPARENT);
    }
    */

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        canvas.drawCircle(globalMonster.getX(), globalMonster.getY(), 50, paint);
    }

}