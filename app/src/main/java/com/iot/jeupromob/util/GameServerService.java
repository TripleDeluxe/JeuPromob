package com.iot.jeupromob.util;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class GameServerService extends Service {
    public GameServerService() {
    }

    // This is the object that receives interactions from clients.
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public GameServerService getService() {
            return GameServerService.this;
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
