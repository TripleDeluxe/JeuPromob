package com.iot.jeupromob.util;


/** Un monstre est un point avec une couleur. Il est unique donc c'est un singleton.
 * Pour cela, l'instance unique est initialisé directement et le constructeur est bloqué
 * */
public class Taupe {

    private float x, y;
    private int color;

    public static volatile Taupe globalTaupe = new Taupe(0,0,1);

    private Taupe(final float x, final float y, final int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //getter
    public float getX() { return x; }

    public float getY() { return y; }

    public int getColor() { return color; }

    public static Taupe getInstance() {
        return globalTaupe;
    }

    //Remplacer le monstre
    public void changeTaupe(int screenWidth, int screenHeight) {

        float xpos = (float) (screenWidth*Math.random()*0.8);
        float ypos = (float) (screenHeight*Math.random()*0.8);
        //color sera 3,2,1 ou 0. on s'assure que chaque valeur ai autant de chance d'apparaitre.
        int colo = (int)Math.floor(0.50001 + Math.random()*3.9999) - 1;

        globalTaupe.x=xpos;
        globalTaupe.y=ypos;
        globalTaupe.color=colo;
    }






}

