package com.iot.jeupromob.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import static com.iot.jeupromob.util.Player.globalPlayer;

public class MultiplayerMenuFragment extends Fragment {

    /**************************************************************/
    /** UI *************************************/
    /**************************************************************/
    private Button mButtonConnect = null;
    private Button mButtonNameValidate = null;
    private Button mButtonReturn = null;
    private TextView mTextViewName =  null;
    private TextInputEditText mTextInputName = null;
    private LinearLayout mPeersViewList = null;

    /**************************************************************/
    /** Wifi P2P *************************************/
    /**************************************************************/

    //IntentFilter pour les events P2P
    private final IntentFilter mIntentFilter = new IntentFilter();
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mWifiP2PManager;
    private WifiP2PBroadcastReceiver mWifiP2PReceiver;

    private boolean mIsWifiP2PEnabled = true;
    public void setIsWifiP2pEnabled(boolean value){
        mIsWifiP2PEnabled = value;
        //TO DO : Prevenir l'user sur l'UI si le wiki P2P est désactivé
    }
    //Liste des voisins découverts
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private WifiP2pDevice mDeviceToConnect;

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
                    final TextView mTextView = new TextView(getContext());
                    mTextView.setText(peers.get(i).deviceName);
                    mTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTextView.setBackgroundColor(Color.GRAY);
                            for(int j=0; j < peers.size(); j++){
                                if(peers.get(j).deviceName == mTextView.getText()){
                                    mDeviceToConnect = peers.get(j);
                                }
                            }
                        }
                    });
                    mPeersViewList.addView(mTextView);
                }

                // Perform any other updates needed based on the new list of
                // peers connected to the Wi-Fi P2P network.
            }

            if (peers.size() == 0) {
                TextView mTextView = new TextView(getContext());
                mTextView.setText("Ïl n'y a pas de voisins Wi-Fi");
                mPeersViewList.addView(mTextView);
                return;
            }
        }
    };

    /**************************************************************/
    /** Fonctions privées *************************************/
    /**************************************************************/

    private void InitP2P(){
        WifiManager mWifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
            Toast.makeText(getActivity(), "Le wifi vient d'être autorisé", Toast.LENGTH_SHORT).show();
        }

        //Vérifie si le Wifi P2P est bien activé
        if(WifiP2pManager.EXTRA_WIFI_STATE.equals(WifiP2pManager.WIFI_P2P_STATE_ENABLED)){

            mWifiP2PManager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
            mChannel =  mWifiP2PManager.initialize(getActivity().getApplicationContext(), getActivity().getMainLooper(), null);
            mWifiP2PReceiver = new WifiP2PBroadcastReceiver(mWifiP2PManager, mChannel, (MainActivity) getActivity(), mPeerListListener);

            // Indicates a change in the Wi-Fi P2P status.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
            // Indicates a change in the list of available peers.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
            // Indicates the state of Wi-Fi P2P connectivity has changed.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
            // Indicates this device's details have changed.
            mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

            DiscoverNeighbors();

            Log.d("dd", "Wifi P2P autorisé ");

        }else{
            Toast.makeText(getActivity(),"Wifi Direct non autorisé", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), "Problème Wi-fi Direct : impossible de découvrir les voisins", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ConnectToP2PDevice(final WifiP2pDevice device){
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        //Sert a donner le mot de passe si l'appareil auquel on souhaite se connecter ne supporte pas Wifi Direct
        mWifiP2PManager.requestGroupInfo(mChannel, new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup group) {
                String groupPassword = group.getPassphrase();
            }
        });

        mWifiP2PManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
                Toast.makeText(getActivity(), "Vous êtes connecté avec " + device.deviceName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(getActivity(), "La connexion n'a pas réussi, réessayer",
                        Toast.LENGTH_SHORT).show();
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

        //Initialisation de la liste des voisins avec le serveur test
        mPeersViewList = getActivity().findViewById(R.id.frag_multi_scrollview_linearlayout);
        final TextView mTextView = new TextView(getContext());
        mTextView.setText("Serveur Test");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setBackgroundColor(Color.GRAY);
            }
        });
        mPeersViewList.addView(mTextView);

        mTextViewName = getActivity().findViewById(R.id.frag_multi_textview_name);
        mTextViewName.setText("Pseudonyme : ");

        mButtonConnect = getActivity().findViewById(R.id.frag_multi_button_connect);
        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(GameManager.getInstance().user.name == ""){
//                    Toast.makeText(getActivity(), "Vous n'avez pas de pseudonyme", Toast.LENGTH_SHORT).show();
//                }else if(mDeviceToConnect == null){
//                    Toast.makeText(getActivity(), "Vous n'avez pas sélectionner de voisin à se connecter", Toast.LENGTH_SHORT).show();
//                }else{
//                    ConnectToP2PDevice(mDeviceToConnect);
//                }

            }
        });

        mButtonNameValidate = getActivity().findViewById(R.id.frag_multi_button_validate);
        mButtonNameValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mTextInputName.getText().toString();
                globalPlayer.name = userName;
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
