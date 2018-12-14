package com.iot.jeupromob.activity;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;

import com.iot.jeupromob.util.GameManager;
import com.iot.jeupromob.R;

public class GameMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button mPlaySoloButton = null;
    private Button mPlayMultiButton = null;
    private Button mPlayTrainingButton = null;

    public GameMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Volume du son
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getResources().getResourceName(R.raw.bonne_reponse);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }


    @Override
    public void onStart(){
        super.onStart();

        //On récupère les boutons de l'UI
        mPlaySoloButton = getActivity().findViewById(R.id.fragment_menu_button_play);
        mPlaySoloButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GameManager.getInstance().start((AppCompatActivity) getActivity());
            }
        });
        mPlayMultiButton = getActivity().findViewById(R.id.fragment_menu_button_play_multi);
        mPlayMultiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
