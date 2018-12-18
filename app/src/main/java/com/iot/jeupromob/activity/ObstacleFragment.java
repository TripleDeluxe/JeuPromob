package com.iot.jeupromob.activity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.GameManager;

import static android.content.Context.VIBRATOR_SERVICE;
import static com.iot.jeupromob.util.Boar.globalBoar;
import static com.iot.jeupromob.util.Player.globalPlayer;


public class ObstacleFragment extends Fragment implements SensorEventListener {
    /*************************************************************************************/
    /** Sensors and co *******************************************************************/
    /*************************************************************************************/

    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer;
    private Sensor mGravity;
    private Sensor mLinearAcc;
    private Display mDisplay;
    public CountDownTimer tiktak;
    public CountDownTimer tiktakFinal;
    public CountDownTimer tiktakBullet;
    public CountDownTimer tiktakDebut;
    public CountDownTimer tiktakCycliste;
    public MediaPlayer dring2 = null;
    public MediaPlayer timeout = null;

    public TextView timeText;
    public TextView goText;

    public ImageView boarPlayerImage;
    public ImageView bulletImage;
    public ImageView cyclisteImage;
    public TranslateAnimation animateBullet;
    public TranslateAnimation animateCycliste;
    public TranslateAnimation animate;
    public static int viewWidth=368;
    public static int viewHeight=372;
    public float mAccelerationX = 0;
    public float mAccelerationY = 0;
    float tempAngle=10;
    float bulletPos=1000;
    float cyclistePos=1000;
    public int score;
    public TextView scoreText;
    float k;

    /**************************************************************/
    /** Sensors Type Constant *************************************/
    /**************************************************************/

    // Le capteur sélectionné
    private int sensorType;
    // L'accéléromètre
    private static final int ACCELE = 0;
    // La Gravité
    private static final int Gravity = 1;
    // L'accéléromètre linéaire
    private static final int LINEAR_ACCELE = 2;

    /**************************************************************/
    /** UI *************************************/
    /**************************************************************/

    private TextView mResX = null;
    private TextView mResY = null;

    public ObstacleFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle state) {

        super.onCreate(state);
        dring2 = MediaPlayer.create(getContext(), R.raw.bell3);
        timeout = MediaPlayer.create(getContext(), R.raw.tictac);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Gérer les capteurs
        // Instancier le SensorManager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        // Instancier l'accéléromètre
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Instancier la gravité
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        // Instancier l'accélération linéaire
        mLinearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        // Et enfin instancier le display qui connaît l'orientation de l'appareil
        mDisplay = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mLinearAcc, SensorManager.SENSOR_DELAY_UI);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_obstacle, container, false);
    }



    @Override
    public void onPause() {
        // désenregistrer tous le monde
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mGravity);
        mSensorManager.unregisterListener(this, mLinearAcc);

        score=0;

        super.onPause();
    }

    @Override
    public void onResume() {
        /* Ce qu'en dit Google&#160;:
         * «&#160; Ce n'est pas nécessaire d'avoir les évènements des capteurs à un rythme trop rapide.
         * En utilisant un rythme moins rapide (SENSOR_DELAY_UI), nous obtenons un filtre
         * automatique de bas-niveau qui "extrait" la gravité  de l'accélération.
         * Un autre bénéfice étant que l'on utilise moins d'énergie et de CPU.&#160;»
         */
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mLinearAcc, SensorManager.SENSOR_DELAY_UI);

        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // update only when your are in the right case:
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            // Corriger les valeurs x et y en fonction de l'orientation de l'appareil
            switch (mDisplay.getRotation()) {
                case Surface.ROTATION_0:
                    mAccelerationX = event.values[0];
                    mAccelerationY = event.values[1];
                    break;
                case Surface.ROTATION_90:
                    mAccelerationX = -event.values[1];
                    mAccelerationY = event.values[0];
                    break;
                case Surface.ROTATION_180:
                    mAccelerationX = -event.values[0];
                    mAccelerationY = -event.values[1];
                    break;
                case Surface.ROTATION_270:
                    mAccelerationX = event.values[1];
                    mAccelerationY = -event.values[0];
                    break;
            }

            //mResX.setText("Accelerometre X : " + mAccelerationX);
            //mResY.setText("Accelerometre Y : " + mAccelerationY);

            float newPos = - mAccelerationX*viewWidth/12;
            float deltaAngle = tempAngle-mAccelerationX;

            //si l'angle ne varie qu'un peu,on ne bouge pas le boar
            if(Math.abs(deltaAngle)>0.2){
                    animate = new TranslateAnimation(globalBoar.getX(),newPos ,0.2f*viewHeight/2, 0.2f*viewHeight/2);
                    animate.setDuration(200);
                    animate.setFillAfter(true);
                    boarPlayerImage.startAnimation(animate);
                    globalBoar.decideBoar(newPos,0.2f*viewHeight/2);
                    tempAngle=mAccelerationX;

            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onStart(){
        super.onStart();

        ViewTreeObserver viewTreeObserver = getView().getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    viewHeight = getView().getHeight();
                    viewWidth = getView().getWidth();
                }
            });
        }











        // UI
        //mResX = (TextView) getActivity().findViewById(R.id.frag_beer_text_resX);
        //mResY = (TextView) getActivity().findViewById(R.id.frag_beer_text_resY);
        timeText = getView().findViewById(R.id.timeObstacle);



        boarPlayerImage = getView().findViewById(R.id.imageBoarPlayer);
        boarPlayerImage.setImageResource(R.drawable.boaricon);
        //boarPlayerImage.bringToFront();

        bulletImage = getView().findViewById(R.id.imageBullet);

        //bulletImage.bringToFront();

        cyclisteImage = getView().findViewById(R.id.imageCycliste);
        cyclisteImage.setImageResource(R.drawable.cycliste);
        bulletImage = getView().findViewById(R.id.imageBullet);
        bulletImage.setImageResource(R.drawable.bullet);
        //cyclisteImage.bringToFront();

        score = 0;
        scoreText = getView().findViewById(R.id.scoreObstacle);



        //TIMER

        tiktak = new CountDownTimer(15 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                goText.setX(1000);

                if(millisUntilFinished== 3000){
                    timeText.setTextColor(Color.parseColor("#E60000"));
                    timeText.setText("0: " + millisUntilFinished/ 1000);
                    timeout.start();

                }
                else{
                    scoreText.setTextColor(Color.parseColor("#000000"));
                    timeText.setText("0: " + millisUntilFinished/ 1000);
                }

                // newPos = ((int)(viewWidth/2 -mAccelerationX*viewWidth/2))/10;


            }
            @Override
            public void onFinish() {
                timeText.setText("FINI!");


                globalPlayer.addScore(score);
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




        tiktakBullet = new CountDownTimer(15 * 1000,710) {
            @Override
            public void onTick(long millisUntilFinished) {

                int compPos= (int)Math.floor(Math.abs(bulletPos-globalBoar.getX()));
                scoreText.setTextColor(Color.parseColor("#000000"));

                if(compPos<35){
                    ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(400);
                    score-=2;
                    scoreText.setText(Integer.toString(score));
                    scoreText.setTextColor(Color.parseColor("#E60000"));
                }
                bulletPos = (float) (Math.random()*viewWidth - Math.random()*viewWidth);
                animateBullet = new TranslateAnimation(bulletPos,bulletPos ,-viewHeight, 0.2f*viewHeight/2);
                animateBullet.setDuration(720);
                animateBullet.setFillAfter(true);
                bulletImage.startAnimation(animateBullet);


            }
            @Override
            public void onFinish() {
                bulletImage.setVisibility(View.INVISIBLE);
            }
        };





        tiktakCycliste = new CountDownTimer(15 * 1000,1200) {
            @Override
            public void onTick(long millisUntilFinished) {

                int compPos= (int)Math.floor(Math.abs(cyclistePos-globalBoar.getX()));


                scoreText.setTextColor(Color.parseColor("#000000"));
                if(compPos<35){
                    ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(100);
                    score++;
                    dring2.start();
                    scoreText.setText(Integer.toString(score));
                    scoreText.setTextColor(Color.parseColor("#669900"));

                }
                cyclistePos = (float) (Math.random()*viewWidth - Math.random()*viewWidth)*0.6f;
                animateCycliste = new TranslateAnimation(cyclistePos,cyclistePos ,-viewHeight, 0.2f*viewHeight/2);
                animateCycliste.setDuration(1200);
                animateCycliste.setFillAfter(true);
                cyclisteImage.startAnimation(animateCycliste);


            }
            @Override
            public void onFinish() {
                cyclisteImage.setVisibility(View.INVISIBLE);


            }
        };




        tiktakDebut = new CountDownTimer(1 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                goText = getView().findViewById(R.id.textGOobstacle);
                k = goText.getX();
                goText.setX(1000);

            }
            @Override
            public void onFinish() {
                tiktakCycliste.start();
                tiktakBullet.start();
                tiktak.start();
                goText.setX(k);
                cyclisteImage = getView().findViewById(R.id.imageCycliste);
                cyclisteImage.setImageResource(R.drawable.cycliste);
                bulletImage = getView().findViewById(R.id.imageBullet);
                bulletImage.setImageResource(R.drawable.bullet);

            }
        };

        tiktakDebut.start();





    }
}