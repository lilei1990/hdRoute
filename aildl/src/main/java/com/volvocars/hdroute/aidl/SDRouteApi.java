package com.volvocars.hdroute.aidl;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;

public class SDRouteApi extends IRouteApi {
    //    private GNSSChangeListener listener;
    private static String TAG = "SDRouteApi";


    public static void sendRouteLink(Route routeLink) {
        sendRouteLink(routeLink.toByteArray());
    }

    public static void sendRouteLink2(Route routeLink) {
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
            sendRouteLink(pfd);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sendRouteLink(ParcelFileDescriptor routeLink) {
        try {
            if (myService != null) {
                myService.sendRouteLink(routeLink);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: " + e.getMessage());
        }
    }


    public static void onLocationChange(byte[] location) {
        try {
            if (myService != null) {
                myService.onLocationChange(location);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: " + e.getMessage());
        }
    }

    public static void onLocationChange(double lon, double lat, double heading) {
        try {
            if (myService != null) {
                SDPointInfo build = SDPointInfo.newBuilder().setLat(lon).setLat(lat).setHeading(heading).build();
                myService.onLocationChange(build.toByteArray());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: " + e.getMessage());
        }
    }

    public static void onLocationChange(SDPointInfo location) {
        try {
            if (myService != null) {
                myService.onLocationChange(location.toByteArray());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: " + e.getMessage());
        }
    }
}
