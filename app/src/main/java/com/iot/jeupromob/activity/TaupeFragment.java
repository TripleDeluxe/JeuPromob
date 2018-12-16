package com.iot.jeupromob.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.Taupe;
import com.iot.jeupromob.util.TaupeView;
import static com.iot.jeupromob.util.Taupe.globalTaupe;





public class TaupeFragment extends Fragment {

    /**taille de la zone d'apparition des points definie à on create*/
    public static int layWidth=368;
    public static int layHeight=372;

    /** score et timer*/
    public CountDownTimer tiktak;
    public TextView timeText;
    public static int score = 0;
    public TextView scoreText;
    public TextView passText;
    public ImageView taupeImage;
    public TranslateAnimation animate;
    public View myView;

    /**
     * prend en parametre le monstre
     * récupere la position du doigt
     * la compare avec celle du monstre
     * donne un bonus ou un malus
     * recréé un monstre random en remplacant le monstre actuel
     * On modifie donc pas le parametre myTaupe mais l'instance globalTaupe
     *
     * */



    //version simplifiée
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            float x =  event.getX() - layWidth/2 - 85;
            float y =  event.getY() - layHeight - 65;

            //float x =  event.getX();
            //float y =  event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //affiche la valeur dans le score

                    /** ANALYSE ET COMPARAISON
                    Probleme: les valeurs sont en float, elle ne seront quasiment jamais similaires
                    Solution: arrondir
                    Probleme2: 1,49 ~ 1,51 or l'arrondi donnera 1 et 2
                    Solution2: on fait une fraction, si le restultat est proche de 1, la position est validée
                        */


                    //fraction
                    double fractionX = x / globalTaupe.getX();
                    //Arrondi du float avec floor puis cast en int
                    int compX = (int)Math.floor(fractionX);

                    Log.d("x: ",Double.toString(x));
                    Log.d("getx: ",Double.toString(globalTaupe.getX()));
                    Log.d("compx: ",Integer.toString(compX));

                    //pareil en y
                    double fractionY = y / globalTaupe.getY();
                    int compY = (int)Math.floor(fractionY);

                    Log.d("x: ",Double.toString(x));
                    Log.d("getx: ",Double.toString(globalTaupe.getX()));
                    Log.d("compx: ",Integer.toString(compX));

                    Log.d("y: ",Double.toString(y));
                    Log.d("gety: ",Double.toString(globalTaupe.getY()));
                    Log.d("compy: ",Integer.toString(compY));


                    if((compX==1)&&(compY==1)){

                        int tempX = (int)globalTaupe.getX();
                        int tempY = (int)globalTaupe.getY();

                        globalTaupe.changeTaupe(layWidth,layHeight);
                        animate = new TranslateAnimation(tempX, globalTaupe.getX(),tempY, globalTaupe.getY());
                        animate.setDuration(50);
                        animate.setFillAfter(true);
                        taupeImage.startAnimation(animate);
                        score++;
                        scoreText.setText(Integer.toString(score));
                    }
                    else{
                        score--;
                        scoreText.setText(Integer.toString(score));

                    }


                    break;
            }

            return true;
        }
    };







/*    private class TrackingTouchListener implements View.OnTouchListener {

        private Taupe myTaupe;
        int track;

        @SuppressLint("SetTextI18n")
        @Override public boolean onTouch(final View v, final MotionEvent evt) {
            scoreText.setText(Integer.toString(6));
            final int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    scoreText.setText(Integer.toString(7));
                    final int idx1 = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    track = (evt.getPointerId(idx1));
                    break;
                default:
                    scoreText.setText(Integer.toString(8));
                    return false;
            }
            *//** ANALYSE ET COMPARAISON
            Probleme: les valeurs sont en float, elle ne seront quasiment jamais similaires
            Solution: arrondir
            Probleme2: 1,49 ~ 1,51 or l'arrondi donnera 1 et 2
            Solution2: on fait une fraction, si le restultat est proche de 1, la position est validée
            *//*

            //fraction
            double fractionX = evt.getX(track) / (v.getWidth() / 10);
            //Arrondi du float avec floor puis cast en int
            int compX = (int)Math.floor(fractionX);

            //pareil en y
            double fractionY = evt.getY(track) / (v.getHeight() / 10);
            int compY = (int)Math.floor(fractionY);



            if((compX==1)&&(compY==1)){

                int tempX = (int)globalTaupe.getX();
                int tempY = (int)globalTaupe.getY();

                if (myTaupe.getColor()==0){
                    score++;
                    scoreText.setText(Integer.toString(score));
                }
                else{
                    score++;
                    scoreText.setText(Integer.toString(score));
                }
                globalTaupe.changeTaupe(layWidth,layHeight);
                animate = new TranslateAnimation(tempX, globalTaupe.getX(),tempY, globalTaupe.getY());
                animate.setDuration(50);
                animate.setFillAfter(true);
                taupeImage.startAnimation(animate);
            }
            else{
                score--;
                scoreText.setText(Integer.toString(score));

            }

            return true;
        }
    }*/






    public void onCreate(Bundle state) {
        super.onCreate(state);


    }




    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        /*final View root=inflater.inflate(R.layout.fragment_taupe, container, false);

        root.post(new Runnable() {
            @Override
            public void run() {
                layHeight = root.getMeasuredHeight();
                layWidth= root.getMeasuredWidth();
                scoreText.setText(Integer.toString(layHeight));
            }
        });*/


        /*final LinearLayout layout = getView().findViewById(R.id.frag_Taupe_linear_layout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener();
                layWidth= layout.getMeasuredWidth();
                layHeight= layout.getMeasuredHeight();
            }
        });*/


        LinearLayout ll = new LinearLayout(this.getContext());
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);


        myView = inflater.inflate(R.layout.fragment_taupe, container, false);

        myView.setOnTouchListener(handleTouch);


        return myView;
    }







    @Override
    public void onStart(){
        super.onStart();





        scoreText = getView().findViewById(R.id.scoreTaupe);
        timeText = getView().findViewById(R.id.timeTaupe);
        taupeImage = getView().findViewById(R.id.imageView1);
       // taupeImage.setImageResource(R.drawable.point);



        globalTaupe.changeTaupe(layWidth,layHeight);

        animate = new TranslateAnimation(0, globalTaupe.getX(),0, globalTaupe.getY());
        animate.setDuration(50);
        animate.setFillAfter(true);







        /**TIMER
         *
         * lorsque le timer est fini:
         *
         * on charge le score dans totalscore
         *
         *
         **/
        tiktak = new CountDownTimer(20 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("0: " + millisUntilFinished/ 1000);



            }

            @Override
            public void onFinish() {
                timeText.setText("FINI");
                timeText.setTextColor(16719360);

            }
        };

        tiktak.start();

    }









}



