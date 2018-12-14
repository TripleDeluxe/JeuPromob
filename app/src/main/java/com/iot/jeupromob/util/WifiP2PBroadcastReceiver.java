package com.iot.jeupromob.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

import com.iot.jeupromob.activity.MainActivity;

public class WifiP2PBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mWifiP2PManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mainActivity;

    public WifiP2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity){
        mWifiP2PManager = manager;
        mChannel = channel;
        mainActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String mAction = intent.getAction();

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(mAction)){
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mainActivity.setIsWifiP2pEnabled(true);
            } else {
                mainActivity.setIsWifiP2pEnabled(false);
            }
        }else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(mAction)) {

            // The peer list has changed! We should probably do something about
            // that.

        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(mAction)) {

            // Connection state changed! We should probably do something about
            // that.

        }
    }
}
