package com.volvocars.hdrppservice;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import com.blankj.utilcode.util.ConvertUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.volvocars.hdroute.aidl.Route;
import com.volvocars.hdroute.aildl.IGNSSChangeListener;
import com.volvocars.hdroute.aildl.IService;

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

//    @Override
//    public void sendRouteLink(byte[] routeLink) {
//        Log.d(TAG, "sendRouteLink: " + routeLink.length);
//        try {
//            if (listener != null) {
//                listener.onRoutLinkChange(routeLink);
//                // TODO: 2023/2/28  把转化的数据发送给hal层
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            Log.d(TAG, "sendRouteLinkErr: " + e.getMessage());
//        }
//    }

    @Override
    public void sendRouteLink(ParcelFileDescriptor sdRouteLink) throws RemoteException {

        try {
            ParcelFileDescriptor hdRouteLink = listener.onRoutLinkChange(sdRouteLink);
            FileInputStream fileInputStream = new FileInputStream(hdRouteLink.getFileDescriptor());
            byte[] bytes = ConvertUtils.inputStream2Bytes(fileInputStream);
            Log.d(TAG, "sendRouteLink: " + Route.parseFrom(bytes).toString());
        } catch (Exception e) {
            Log.d(TAG, "sendRouteLink: "+e.getMessage());
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
