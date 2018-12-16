package com.iot.jeupromob.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
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
import android.os.Vibrator;


import org.xmlpull.v1.XmlPullParser;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.Taupe;
import com.iot.jeupromob.util.TaupeView;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;
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
    public ImageView boarImage;
    public ImageView cyclingImage;
    public TranslateAnimation animate;
    public TranslateAnimation animate2;
    public View myView;
    public boolean isPlaying;

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
            if(!isPlaying){return false;}

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



                    int compX = (int)Math.floor(Math.abs(x-globalTaupe.getX()));
                    int compY= (int)Math.floor(Math.abs(y-globalTaupe.getY()));

                    Log.d("x: ",Double.toString(x));
                    Log.d("getx: ",Double.toString(globalTaupe.getX()));
                    Log.d("compx: ",Integer.toString(compX));

                    Log.d("y: ",Double.toString(y));
                    Log.d("gety: ",Double.toString(globalTaupe.getY()));
                    Log.d("compy: ",Integer.toString(compY));


                    if((compX<35)&&(compY<35)){

                        int tempX = (int)globalTaupe.getX();
                        int tempY = (int)globalTaupe.getY();

                        globalTaupe.changeTaupe(layWidth,layHeight);
                        animate2 = new TranslateAnimation(tempX, globalTaupe.getX(),tempY, globalTaupe.getY());
                        animate2.setDuration(05);
                        animate2.setFillAfter(true);
                        boarImage.startAnimation(animate2);

                        animate = new TranslateAnimation(tempX, globalTaupe.getX(),tempY, globalTaupe.getY());
                        animate.setDuration(200);
                        animate.setFillAfter(true);

                        taupeImage.startAnimation(animate);
                        score++;
                        scoreText.setText(Integer.toString(score));
                    }
                    else{
                        score-=5;
                        scoreText.setText(Integer.toString(score));
                        ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(150);
                    }


                    break;
            }

            return true;
        }
    };




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
        isPlaying= true;

        scoreText = getView().findViewById(R.id.scoreTaupe);
        timeText = getView().findViewById(R.id.timeTaupe);
        taupeImage = getView().findViewById(R.id.imageView1);
        taupeImage.setImageResource(R.drawable.target);

        cyclingImage = getView().findViewById(R.id.imageViewBack);
        cyclingImage.setImageResource(R.drawable.cycling);

        boarImage = getView().findViewById(R.id.imageViewBoar);
        boarImage.setImageResource(R.drawable.boar);

        score = 0;

        globalTaupe.changeTaupe(layWidth,layHeight);

        animate2 = new TranslateAnimation(0, globalTaupe.getX(),0, globalTaupe.getY());
        animate2.setDuration(05);
        animate2.setFillAfter(true);
        boarImage.startAnimation(animate2);

        animate = new TranslateAnimation(0, globalTaupe.getX(),0, globalTaupe.getY());
        animate.setDuration(200);
        animate.setFillAfter(true);
        taupeImage.startAnimation(animate);


        /**TIMER
         *
         * lorsque le timer est fini:
         *
         * on charge le score dans totalscore
         *
         *
         **/
        tiktak = new CountDownTimer(8 * 1000,10) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished< 3000){
                    timeText.setTextColor(Color.parseColor("#E60000"));

                    timeText.setText("" + millisUntilFinished/ 1000 +":" + (millisUntilFinished/ 10 - (millisUntilFinished/ 1000)*100));
                }
                else{
                    timeText.setText("" + millisUntilFinished/ 1000 +":" + (millisUntilFinished/ 10 - (millisUntilFinished/ 1000)*100));
                }
            }
            @Override
            public void onFinish() {
                timeText.setText("FINI");
                isPlaying= false;
                //playerActuel.addscore(score);
                //wait(500);
                //lancer jeu suivant

            }
        };

        tiktak.start();

    }









}



