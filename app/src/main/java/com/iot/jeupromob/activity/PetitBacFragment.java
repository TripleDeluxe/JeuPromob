package com.iot.jeupromob.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iot.jeupromob.util.AsyncHTTPGetJSON;
import com.iot.jeupromob.util.GameManager;
import com.iot.jeupromob.R;

public class PetitBacFragment extends Fragment {
    private Button buttonPassGame = null;

    public PetitBacFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_petit_bac, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        buttonPassGame = getView().findViewById(R.id.fragment_petit_bac_pass_button2);
        buttonPassGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.getInstance().nextGame((AppCompatActivity) getActivity());
            }
        });
    }
}
