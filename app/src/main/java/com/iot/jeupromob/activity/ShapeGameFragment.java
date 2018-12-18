package com.iot.jeupromob.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.FingerPath;
import com.iot.jeupromob.util.GameManager;
import com.iot.jeupromob.util.PaintView;
import com.iot.jeupromob.util.Random;

import java.util.ArrayList;

public class ShapeGameFragment extends Fragment {
    private PaintView mPaintView;
    private int mNumberRound = 2;
    private int mCurrentRoundIndex = -1;


    public ShapeGameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shape_game, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        mPaintView = getActivity().findViewById(R.id.frag_shape_paintview_user);

        final ShapeGameFragment temp = this;

        //On attends que le layout global soit initialisé pour avoir la taille de la view (cf init() )
        ViewTreeObserver viewTreeObserver = getView().getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mPaintView.init(temp);
                    nextRound();
                }
            });
        }

    }

    public void nextRound(){
        mCurrentRoundIndex++;

        if(mCurrentRoundIndex == mNumberRound){
            GameManager.getInstance().nextGame((MainActivity) getActivity());
        }else{
            mPaintView.initRound();
        }
    }

}
