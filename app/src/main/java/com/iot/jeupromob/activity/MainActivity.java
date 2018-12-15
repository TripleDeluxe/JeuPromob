package com.iot.jeupromob.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.MonsterView;
import com.iot.jeupromob.util.WifiP2PBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    //Listener pour les events P2P
    private final IntentFilter mIntentFilter = new IntentFilter();
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mWifiP2PManager;
    private WifiP2PBroadcastReceiver mWifiP2PReceiver;

    private boolean mIsWifiP2PEnabled = false;
    public void setIsWifiP2pEnabled(boolean value){
        mIsWifiP2PEnabled = value;
        //TO DO : Prevenir l'user sur l'UI
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new GameMenuFragment()).commit();

        //Vérifie si le Wifi P2P est bien activé
        if(WifiP2pManager.EXTRA_WIFI_STATE.equals(WifiP2pManager.WIFI_P2P_STATE_ENABLED)){
            mIsWifiP2PEnabled = true;
            // Indicates a change in the Wi-Fi P2P status.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
            // Indicates a change in the list of available peers.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
            // Indicates the state of Wi-Fi P2P connectivity has changed.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
            // Indicates this device's details have changed.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

            mWifiP2PManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
            mChannel = mWifiP2PManager.initialize(this, getMainLooper(), null);
            mWifiP2PReceiver = new WifiP2PBroadcastReceiver(mWifiP2PManager, mChannel, this);
        }


    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    /** register the BroadcastReceiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();
        mWifiP2PReceiver = new WifiP2PBroadcastReceiver(mWifiP2PManager, mChannel, this);
        registerReceiver(mWifiP2PReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mWifiP2PReceiver);
    }
}
