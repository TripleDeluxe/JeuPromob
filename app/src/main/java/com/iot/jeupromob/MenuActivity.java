package com.iot.jeupromob;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity
{
    private Button mPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mPlay = findViewById(R.id.activity_menu_button_play);
        mPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GameManager.getInstance().start(MenuActivity.this);
            }
        });


    }
}
