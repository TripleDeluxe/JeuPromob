package com.iot.jeupromob.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    //Initialisée a -1 car chaque appel à checkResult() incrémente cette variable
    private int mCurrentRoundIndex = 0;
    private String mShape;
    private String[] mShapes = new String[]{"circle", "triangle", "square"};

    private TextView mTextViewX;
    private TextView mTextViewY;

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

        mTextViewX = getActivity().findViewById(R.id.frag_shape_acc_x);
        mTextViewY = getActivity().findViewById(R.id.frag_shape_acc_y);

        mShape = mShapes[Random.randomNumber(mShapes.length - 1)];

        mPaintView = getActivity().findViewById(R.id.frag_shape_paintview_user);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mPaintView.init(metrics, mTextViewX, mTextViewY);

        Button mButtonValidate = getActivity().findViewById(R.id.frag_shape_button_validate);
        mButtonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkResult();
            }
        });


    }

    private void checkResult(){
        //TO DO : Verifier si la forme se rapproche de la forme à copier
        ArrayList<FingerPath> mPath = mPaintView.mFingerPaths;

        for(int i=0; i < mPath.size(); i++){
            if(mShape == "circle"){

            }
        }


        nextRound();
    }

    private void nextRound(){
        mCurrentRoundIndex++;

        if(mCurrentRoundIndex == mNumberRound){
            GameManager.getInstance().nextGame((MainActivity) getActivity());

        }else{
            mPaintView.clear();
        }
    }
}
