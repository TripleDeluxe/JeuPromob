package com.iot.jeupromob.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.WifiP2PBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new GameMenuFragment()).commit();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
