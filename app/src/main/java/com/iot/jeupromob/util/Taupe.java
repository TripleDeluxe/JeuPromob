package com.iot.jeupromob.util;


/** Un monstre est un point avec une couleur. Il est unique donc c'est un singleton.
 * Pour cela, l'instance unique est initialisé directement et le constructeur est bloqué
 * */
public class Taupe {

    private float x, y;

    public static volatile Taupe globalTaupe = new Taupe(1,1);

    private Taupe(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    //getter
    public float getX() { return x; }

    public float getY() { return y; }


    public static Taupe getInstance() {
        return globalTaupe;
    }

    //Remplacer le monstre

    public void changeTaupe(int screenWidth, int screenHeight) {

        float xpos = (float) ((screenWidth*Math.random()*0.7) );
        float ypos = (float) ((screenHeight*Math.random()*0.7)  );

        globalTaupe.x=xpos;
        globalTaupe.y=ypos;
    }

/*    public void changeTaupe(int screenWidth, int screenHeight) {

        double k =Math.random()-Math.random();
        double u =Math.random()-Math.random();
        float xpos = (float) ((screenWidth*Math.random()*0.7) * (k/Math.abs(k)) );
        float ypos = (float) (-(screenHeight*Math.random()*0.7) * (u/Math.abs(u)) );
        //color sera 3,2,1 ou 0. on s'assure que chaque valeur ai autant de chance d'apparaitre.
        int colo = (int)Math.floor(0.50001 + Math.random()*3.9999) - 1;

        globalTaupe.x=xpos;
        globalTaupe.y=ypos;
        globalTaupe.color=colo;
    }*/

    public void decideTaupe(float x, float y) {

        globalTaupe.x=x;
        globalTaupe.y=y+120;
    }






}

