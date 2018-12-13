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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeerGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeerGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**************************************************************/
    /** UI *************************************/
    /**************************************************************/

    private OnFragmentInteractionListener mListener;

    private Button buttonPassGame = null;
    private TextView mResX = null;
    private TextView mResY = null;

    public BeerGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeerGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeerGameFragment newInstance(String param1, String param2) {
        BeerGameFragment fragment = new BeerGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        buttonPassGame = getView().findViewById(R.id.fragment_beer_game_pass_button);
        buttonPassGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.getInstance().nextGame((AppCompatActivity) getActivity());
            }
        });
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
