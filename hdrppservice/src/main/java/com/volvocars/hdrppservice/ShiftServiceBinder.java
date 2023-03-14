package com.volvocars.hdrppservice;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import com.blankj.utilcode.util.ConvertUtils;
import com.volvocars.hdroute.aidl.IGNSSChangeListener;
import com.volvocars.hdroute.aidl.IService;
import com.volvocars.hdroute.aidl.Route;

import java.io.FileInputStream;


public class ShiftServiceBinder extends IService.Stub {

    private IGNSSChangeListener listener;
    private String TAG = "ShiftServiceBinder";

    public ShiftServiceBinder() {
    }


    @Override
    public void addHDRPPChangeListener(String tag, IGNSSChangeListener listener) throws RemoteException {
        this.listener = listener;
    }

    @Override
    public void removeHDRPPChangeListener(String tag) throws RemoteException {
        this.listener = null;
    }


    @Override
    public void sendRouteLink(ParcelFileDescriptor sdRouteLink) throws RemoteException {
        try {
            FileInputStream fileInputStream1 = new FileInputStream(sdRouteLink.getFileDescriptor());
            byte[] bytes1 = ConvertUtils.inputStream2Bytes(fileInputStream1);
            Log.d(TAG, "sendRouteLink1: " + Route.parseFrom(bytes1).toString());
            if (listener != null) {
                ParcelFileDescriptor hdRouteLink = listener.onRoutLinkChange(sdRouteLink);
                FileInputStream fileInputStream = new FileInputStream(hdRouteLink.getFileDescriptor());
                byte[] bytes = ConvertUtils.inputStream2Bytes(fileInputStream);
                Log.d(TAG, "sendRouteLink: " + Route.parseFrom(bytes).toString());
            }
        } catch (Exception e) {
            Log.d(TAG, "sendRouteLink: " + e.getMessage());
            e.printStackTrace();
        }
        // TODO: 2023/2/28  把转化的数据发送给hal层
//        Log.d(TAG, "sendRouteLink: " +bytes1.length+ ConvertUtils.bytes2String(bytes1));
    }


    /**
     * AMap实时的经纬度数据
     *
     * @param location
     * @throws RemoteException
     */
    @Override
    public void onLocationChange(byte[] location) {
        try {
            if (listener != null) {
                listener.onSDPointChange(location);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "sendRouteLinkErr: " + e.getMessage());
        }
    }
}
