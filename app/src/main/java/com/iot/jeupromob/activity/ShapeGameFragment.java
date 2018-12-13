package com.iot.jeupromob.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.PaintView;

public class ShapeGameFragment extends Fragment {
    private PaintView mPaintView;

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

        mPaintView = (PaintView) getActivity().findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mPaintView.init(metrics);
    }
}
