package com.iot.jeupromob.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.GameServerService;

public class MultiplayerLobbyFragment extends Fragment {
    private boolean isServiceBound = false;

    private GameServerService mServerService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has
            // been established, giving us the service object we can use
            // to interact with the service.  Because we have bound to a
            // explicit service that we know is running in our own
            // process, we can cast its IBinder to a concrete class and
            // directly access it.
            mServerService = ((GameServerService.LocalBinder) service).getService();

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has
            // been unexpectedly disconnected -- that is, its process
            // crashed. Because it is running in our same process, we
            // should never see this happen.
            mServerService = null;
        }

        ;

        void doBindService() {
            // Establish a connection with the service.  We use an explicit
            // class name because we want a specific service implementation
            // that we know will be running in our own process (and thus
            // won't be supporting component replacement by other
            // applications).
//            bindService(new Intent(Binding.this, GameServerService.class),
//                    mConnection,
//                    Context.BIND_AUTO_CREATE);
            isServiceBound = true;
        }

        void doUnbindService() {
            if (isServiceBound) {
                // Detach our existing connection.
                //unbindService(mConnection);
                isServiceBound = false;
            }
        }

//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            doUnbindService();
//        }
    };

    public MultiplayerLobbyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mConnection.onServiceConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiplayer_lobby, container, false);
    }
}
