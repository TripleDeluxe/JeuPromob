package com.iot.jeupromob.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iot.jeupromob.R;

public class EndFragment extends Fragment {
    private MediaPlayer mEndMusic = null;
    private Button buttonReturn = null;

    public EndFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEndMusic = MediaPlayer.create(getContext(), R.raw.end_game_music);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mEndMusic.start();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        buttonReturn = getView().findViewById(R.id.fragment_end_button_return);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndMusic.stop();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new GameMenuFragment()).commit();
            }
        });
    }
}
