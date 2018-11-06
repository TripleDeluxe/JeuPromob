package com.iot.jeupromob;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private Button mPlay;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlay = findViewById(R.id.activity_main_button_play);
        mPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GameManager.getInstance().start(MainActivity.this);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
