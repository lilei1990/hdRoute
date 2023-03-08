package com.volvocars.sdmap;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.volvocars.hdroute.aidl.IRouteApi;
import com.volvocars.hdroute.aidl.MemoryFileUtils;
import com.volvocars.hdroute.aidl.Route;
import com.volvocars.hdroute.aildl.IService;

import java.io.FileDescriptor;
import java.io.IOException;

public class SDRouteApi extends IRouteApi {
    //    private GNSSChangeListener listener;
    private static String TAG = "SDRouteApi";


    public static void sendRouteLink(Route routeLink) {
        sendRouteLink(routeLink.toByteArray());
    }

    public static void sendRouteLink(byte[] routeLink) {
        /**
         * 创建MemoryFile
         */
        MemoryFile memoryFile = null;
        try {
            memoryFile = new MemoryFile("routeLink", routeLink.length);


            /**
             * 向MemoryFile中写入字节数组
             */
            memoryFile.writeBytes(routeLink, 0, 0, routeLink.length);

            /**
             * 获取MemoryFile对应的FileDescriptor
             */
            FileDescriptor fd = MemoryFileUtils.getFileDescriptor(memoryFile);

            /**
             * 根据FileDescriptor创建ParcelFileDescriptor
             */
            ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(fd);

            /**
             * 发送数据
             */
            SDRouteApi.sendRouteLink(pfd);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sendRouteLink(ParcelFileDescriptor routeLink) {
        try {
            myService.sendRouteLink(routeLink);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: "+e.getMessage());
        }
    }


    public static void onLocationChange(byte[] location) {
        try {
            myService.onLocationChange(location);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: "+e.getMessage());
        }
    }
}
