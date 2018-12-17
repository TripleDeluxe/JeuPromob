package com.iot.jeupromob.util;



public class Boar {

    private float x, y;
    private int life;

    public static volatile Boar globalBoar = new Boar(1,1,1);

    private Boar(final float x, final float y, final int life) {
        this.x = x;
        this.y = y;
        this.life = life;
    }

    //getter
    public float getX() { return x; }

    public float getY() { return y; }

    public int getLife() { return life; }

    public static Boar getInstance() {
        return globalBoar;
    }
    public void decideBoar(float x, float y) {

        globalBoar.x=x;
        globalBoar.y=y;
    }







}

