package com.iot.jeupromob.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.iot.jeupromob.activity.MainActivity;

public class WifiP2PBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mWifiP2PManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mainActivity;
    private WifiP2pManager.PeerListListener mPeerListListener;

    public WifiP2PBroadcastReceiver(){

    }

    public WifiP2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity, WifiP2pManager.PeerListListener mPeerListener){
        mWifiP2PManager = manager;
        mChannel = channel;
        mainActivity = activity;
        mPeerListListener = mPeerListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String mAction = intent.getAction();

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(mAction)){
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //setIsWifiP2pEnabled(true);
            } else {
                //setIsWifiP2pEnabled(false);
            }
        }else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(mAction)) {

            // The peer list has changed! We should probably do something about
            // that.

        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(mAction)) {

            // Connection state changed! We should probably do something about
            // that.

        }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(mAction)){
            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (mWifiP2PManager != null) {
                mWifiP2PManager.requestPeers(mChannel, mPeerListListener);
            }
            Log.d("BROADCAST RECEIVER", "P2P peers changed");
        }
    }
}
