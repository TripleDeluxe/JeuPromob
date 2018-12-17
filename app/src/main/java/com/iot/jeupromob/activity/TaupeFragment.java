package com.iot.jeupromob.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Vibrator;


import com.iot.jeupromob.R;
import com.iot.jeupromob.util.GameManager;

import static android.content.Context.VIBRATOR_SERVICE;
import static com.iot.jeupromob.util.Taupe.globalTaupe;





public class TaupeFragment extends Fragment {

    /**taille de la zone d'apparition des points definie à on create*/
    public int layWidth;
    public int layHeight;

    /** score et timer*/
    public CountDownTimer tiktak;
    public CountDownTimer tiktakFinal;
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
    public float bandeHaute;
    public float bandeCote;
    float xpos;
    float ypos;

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

            float x =  event.getX();
            float y =  event.getY();

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



                    int compX = (int)Math.floor(Math.abs(x-xpos));
                    int compY= (int)Math.floor(Math.abs(y-ypos));

                    Log.d("www","x: " + Double.toString(x));
                    Log.d("www","getx: " + Double.toString(xpos));
                    Log.d("www","compx: " + Integer.toString(compX));

                    Log.d("www","y: " + Double.toString(y));
                    Log.d("www","gety: " + Double.toString(ypos));
                    Log.d("www","compy: " + Integer.toString(compY));



                    if((compX<35)&&(compY<35)){

                        float tempX = xpos;
                        float tempY = ypos;

                        xpos = (float)((layWidth*Math.random()*0.8));
                        ypos = (float)((layHeight*Math.random()*0.8));

                        animate2 = new TranslateAnimation(tempX, xpos-65,tempY, ypos-65);
                        animate2.setDuration(10);
                        animate2.setFillAfter(true);
                        boarImage.startAnimation(animate2);

                        animate = new TranslateAnimation(tempX, xpos-65,tempY, ypos-65);
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


        ViewTreeObserver viewTreeObserver = getView().getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    layWidth = getView().findViewById(R.id.frag_Taupe_linear_layout).getWidth();
                    layHeight = getView().findViewById(R.id.frag_Taupe_linear_layout).getHeight();

                    float viewHeight = getView().getHeight();
                    float viewWidth = getView().getWidth();

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                    float screenHeight = displayMetrics.heightPixels;
                    float screenWidth = displayMetrics.widthPixels;

                    bandeHaute = screenHeight - viewHeight;
                    bandeCote = (screenWidth-layWidth)/2;



                    Log.d("wwwww","" + layWidth + "height " + layHeight);
                    Log.d("wwwww","view" + viewHeight + "height " + viewWidth);
                    Log.d("wwwww","screen" + screenHeight + "height " + screenWidth);

                    scoreText = getView().findViewById(R.id.scoreTaupe);
                    timeText = getView().findViewById(R.id.timeTaupe);
                    taupeImage = getView().findViewById(R.id.imageView1);

                    taupeImage.setImageResource(R.drawable.target);

                    boarImage = getView().findViewById(R.id.imageViewBoar);
                    boarImage.setImageResource(R.drawable.boar);

                    score = 0;

                    xpos = layWidth/2 ;
                    ypos =layHeight/2 ;
                    animate2 = new TranslateAnimation(xpos-65, xpos-65,ypos-65, ypos-65);
                    animate2.setDuration(10);
                    animate2.setFillAfter(true);
                    boarImage.startAnimation(animate2);

                    animate = new TranslateAnimation(xpos-65, xpos-65,ypos-65, ypos-65);
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
                    tiktak = new CountDownTimer(12 * 1000,10) {
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
                            isPlaying= false;
                            timeText.setText("FINI!");


                            GameManager.getInstance().user.addScore(score);
                            tiktakFinal = new CountDownTimer(1 * 1000,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    GameManager.getInstance().nextGame((MainActivity) getActivity());
                                }

                            };
                            tiktakFinal.start();


                        }
                    };

                    tiktak.start();
                }
            });
        }



    }









}



