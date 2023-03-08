package com.volvocars.hdrppservice;

import android.os.IBinder;

interface IBinderFactory {
    IBinder generateBinder(int binderType);
}