package com.iot.jeupromob.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/** Un monstre est un point avec une couleur. Il est unique donc c'est un singleton.
 * Pour cela, l'instance unique est initialisé directement et le constructeur est bloqué
 * */
public class Monster {

    private float x, y;
    private int color;

    public static volatile Monster globalMonster = new Monster(0,0,1);

    private Monster(final float x, final float y, final int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //getter
    public float getX() { return x; }

    public float getY() { return y; }

    public int getColor() { return color; }

    public static Monster getInstance() {
        return globalMonster;
    }

    //Remplacer le monstre
    public void changeMonster(int screenWidth, int screenHeight) {

        float xpos = (float) (screenWidth*Math.random()*0.9);
        float ypos = (float) (screenHeight*Math.random()*0.9);
        //color sera 3,2,1 ou 0. on s'assure que chaque valeur ai autant de chance d'apparaitre.
        int colo = (int) Math.floor(0.50001 + Math.random()*3.9999) - 1;

        globalMonster.x=xpos;
        globalMonster.y=ypos;
        globalMonster.color=colo;



    }






}

