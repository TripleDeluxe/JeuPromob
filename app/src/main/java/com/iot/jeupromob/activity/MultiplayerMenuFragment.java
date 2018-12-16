package com.iot.jeupromob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.GameManager;
import com.iot.jeupromob.util.WifiP2PBroadcastReceiver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class MultiplayerMenuFragment extends Fragment {
    private boolean mPseudo = false;

    /**************************************************************/
    /** UI *************************************/
    /**************************************************************/
    private Button mButtonConnect = null;
    private Button mButtonNameValidate = null;
    private Button mButtonReturn = null;
    private TextView mTextViewName =  null;
    private TextInputEditText mTextInputName = null;
    private ScrollView mScrollView = null;

    /**************************************************************/
    /** Wifi P2P *************************************/
    /**************************************************************/

    //IntentFilter pour les events P2P
    private final IntentFilter mIntentFilter = new IntentFilter();
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mWifiP2PManager;
    private WifiP2PBroadcastReceiver mWifiP2PReceiver;

    private boolean mIsWifiP2PEnabled = false;
    public void setIsWifiP2pEnabled(boolean value){
        mIsWifiP2PEnabled = value;
        //TO DO : Prevenir l'user sur l'UI si le wiki P2P est désactivé
    }
    //Liste des voisins découverts
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    //Listener pour mettre a jour la liste des voisins WIfi P2P
    private WifiP2pManager.PeerListListener mPeerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            Collection<WifiP2pDevice> refreshedPeers = peerList.getDeviceList();
            if (!refreshedPeers.equals(peers)) {
                peers.clear();
                peers.addAll(refreshedPeers);

                // If an AdapterView is backed by this data, notify it
                // of the change. For instance, if you have a ListView of
                // available peers, trigger an update.
                for(int i=0; i < peers.size(); i++){
                    TextView mTextView = new TextView(getContext());
                    mTextView.setText(peers.get(i).deviceName);
                    mScrollView.addView(mTextView);
                }

                // Perform any other updates needed based on the new list of
                // peers connected to the Wi-Fi P2P network.
            }

            if (peers.size() == 0) {
                TextView mTextView = new TextView(getContext());
                mTextView.setText("Ïl n'y a pas de voisins découvrable par Wi-Fi");
                mScrollView.addView(mTextView);
                return;
            }
        }
    };

    /**************************************************************/
    /** Fonctions privées *************************************/
    /**************************************************************/

    private void InitP2P(){

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

            mWifiP2PManager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
            mChannel =  mWifiP2PManager.initialize(getContext(), getActivity().getMainLooper(), null);
            mWifiP2PReceiver = new WifiP2PBroadcastReceiver(mWifiP2PManager, mChannel, (MainActivity) getActivity(), mPeerListListener);

            Log.d("dd", "Wifi P2P autorisé ");
            DiscoverNeighbors();
        }else{
            Log.d("dd", "WIFI P2P non autorisé");

            try {
                Method method1 = mWifiP2PManager.getClass().getMethod("enableP2p", WifiP2pManager.Channel.class);
                method1.invoke(mWifiP2PManager, mChannel);
                Toast.makeText(getActivity(), "method found",
                      Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "method did not found",
                   Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void DiscoverNeighbors(){
        mWifiP2PManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
            }

            @Override
            public void onFailure(int reason) {
                //Prévenir l'user que l'on arrive pas à découvrir les voisins
            }
        });
    }

    /**************************************************************/
    /** Cycle de vie *************************************/
    /**************************************************************/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiplayer_menu, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        InitP2P();
        //DiscoverNeighbors();

        mTextInputName = getActivity().findViewById(R.id.frag_multi_input_name);

        mScrollView = getActivity().findViewById(R.id.frag_multi_scrollview);

        mTextViewName = getActivity().findViewById(R.id.frag_multi_textview_name);
        mTextViewName.setText("Pseudonyme : ");

        mButtonConnect = getActivity().findViewById(R.id.frag_multi_button_connect);
        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameManager.getInstance().user.name == ""){
                    // Message d'erreur : vous n'avez pas de pseudo
                }else{

                }
            }
        });

        mButtonNameValidate = getActivity().findViewById(R.id.frag_multi_button_validate);
        mButtonNameValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mTextInputName.getText().toString();
                GameManager.getInstance().user.name = userName;
                mTextViewName.setText("Pseudonyme : " + mTextInputName.getText().toString());
                mTextInputName.setText("");
            }
        });

        mButtonReturn = getActivity().findViewById(R.id.frag_multi_button_return2);
        mButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new GameMenuFragment()).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mWifiP2PReceiver = new WifiP2PBroadcastReceiver(mWifiP2PManager, mChannel, (MainActivity) getActivity(), mPeerListListener);
        getActivity().registerReceiver(mWifiP2PReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mWifiP2PReceiver);
    }

}
