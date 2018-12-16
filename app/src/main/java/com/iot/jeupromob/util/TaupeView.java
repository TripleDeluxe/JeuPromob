package com.iot.jeupromob.util;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import static com.iot.jeupromob.util.Taupe.globalTaupe;


public class TaupeView extends View {

    private Paint paint;

    public TaupeView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
        // selection de la couleur
        paint = new Paint();
        switch(globalTaupe.getColor()){
            case(0): paint.setColor(Color.RED);
            case(1): paint.setColor(Color.BLUE);
            case(2): paint.setColor(Color.BLACK);
            case(3): paint.setColor(Color.GREEN);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);//remet un fond blanc
        canvas.drawCircle(globalTaupe.getX(), globalTaupe.getY(), 50, paint);
    }

}