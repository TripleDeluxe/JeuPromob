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

    private Button mPlaySoloButton = null;
    private Button mPlayMultiButton = null;
    private Button mPlayTrainingButton = null;

    public GameMenuFragment() {
        // Required empty public constructor
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
        mPlaySoloButton = getActivity().findViewById(R.id.fragment_menu_button_play_solo);
        mPlaySoloButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GameManager.getInstance().startSoloGame((AppCompatActivity) getActivity());
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
