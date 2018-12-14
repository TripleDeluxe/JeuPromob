package com.iot.jeupromob.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.GameManager;

public class TrainingFragment extends Fragment {
    private Fragment[] mGamesFragments = null;

    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mGamesFragments = GameManager.getInstance().getMiniGamesFragments();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

}
