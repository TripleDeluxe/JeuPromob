package com.iot.jeupromob.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.GameManager;

public class TrainingFragment extends Fragment {
    //Array des fragments des jeux
    private Fragment[] mGamesFragments = null;


    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        mGamesFragments = GameManager.getInstance().getMiniGamesFragments();

        Button mButtonQuizzGame = getActivity().findViewById(R.id.fragment_training_button_play_quizz);
        mButtonQuizzGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new QuizzGameFragment()).commit();
            }
        });

        Button mButtonTaupeGame = getActivity().findViewById(R.id.fragment_training_button_play_taupe);
        mButtonTaupeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new TaupeFragment()).commit();
            }
        });

        Button mButtonShapeGame = getActivity().findViewById(R.id.fragment_training_button_play_shape);
        mButtonShapeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new ShapeGameFragment()).commit();
            }
        });

        Button mButtonReturn = getActivity().findViewById(R.id.frag_training_button_return);
        mButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new GameMenuFragment()).commit();
            }
        });
    }

}
