package com.volvocars.hdroute.aidl;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public abstract class IRouteApi {

    protected static IService myService;

    private static final String TAG = IRouteApi.class.getName();
    private static ServiceConnection con = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = IService.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: " + myService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + name);
        }
    };

    public static void init(Application application) {
        Intent intent = new Intent();
        /* action name of service to be bound. server side.*/
        intent.setAction("com.volvocars.hdrppservice.Shifting");
        /* package name of service to be bound.server side.*/
        intent.setPackage("com.volvocars.hdrppservice");
        boolean success = application.bindService(intent, con,
                Context.BIND_AUTO_CREATE); // bind take time 2s.
        Log.d(TAG, "init: " + success);
    }

}
