/*
 * Copyright 2022 Volvo Car Corporation
 * This file is covered by LICENSE file in the root of this project
 */

package com.volvocars.hdrppservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShiftService extends Service {
    private final String TAG = ShiftService.this.getClass().getSimpleName();
    private ShiftServiceBinder mBinder;
//    private static ShiftFactory mShiftFactory;

    public ShiftService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: "+this);
        /* init ShiftFactory, kick off udp receive thread. */
//        mShiftFactory = ShiftFactory.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        if (null == mBinder) {
            mBinder = new ShiftServiceBinder();
        }
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}