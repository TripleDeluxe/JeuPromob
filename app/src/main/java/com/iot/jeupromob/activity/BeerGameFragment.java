package com.iot.jeupromob.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.TextView;

import com.iot.jeupromob.util.GameManager;
import com.iot.jeupromob.R;


public class BeerGameFragment extends Fragment implements SensorEventListener {
    /*************************************************************************************/
    /** Sensors and co *******************************************************************/
    /*************************************************************************************/

    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer;
    private Sensor mGravity;
    private Sensor mLinearAcc;
    private Display mDisplay;

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

    public BeerGameFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_beer_game, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        // UI
        mResX = (TextView) getActivity().findViewById(R.id.frag_beer_text_resX);
        mResY = (TextView) getActivity().findViewById(R.id.frag_beer_text_resY);
    }

    @Override
    public void onPause() {
        // désenregistrer tous le monde
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mGravity);
        mSensorManager.unregisterListener(this, mLinearAcc);
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
        float mAccelerationX = 0;
        float mAccelerationY = 0;
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
            // faire quelque chose

            mResX.setText("Accelerometre X : " + mAccelerationX);
            mResY.setText("Accelerometre Y : " + mAccelerationY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
