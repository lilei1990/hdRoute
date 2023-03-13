package com.volvocars.testhdmap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.volvocars.hdroute.aidl.HDRPPChangeListener;
import com.volvocars.hdroute.aidl.HDRouteApi;
import com.volvocars.hdroute.aidl.Route;
import com.volvocars.hdroute.aidl.SDPointInfo;

public class MainActivity extends AppCompatActivity {


    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initServices();
        View bt1 = findViewById(R.id.btClick1);
        View bt2 = findViewById(R.id.btClick2);
        View bt3 = findViewById(R.id.btClick3);

    }

    private void initServices() {
        HDRouteApi.init(Utils.getApp());
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "run: init ok");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HDRouteApi.addHDRPPChangeListener("AutoSDK", new HDRPPChangeListener() {
                    @Override
                    public byte[] onHDFusedPointChange(byte[] data) {
                        Log.d(TAG, "onHDFusedPointChange: " + ConvertUtils.bytes2String(data));
                        return new byte[0];
                    }

                    @Override
                    public byte[] onHDRawPointChange(byte[] data) {
                        Log.d(TAG, "onHDRawPointChange: " + ConvertUtils.bytes2String(data));
                        return new byte[0];
                    }

                    @Override
                    public void onSDPointChange(byte[] data) {
                        try {
                            String s = SDPointInfo.parseFrom(data).toString();
                            Log.d(TAG, "onSDPointChange: " + s);
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSDIMUChange(byte[] data) {
                        Log.d(TAG, "onSDIMUChange: " + ConvertUtils.bytes2String(data));
                    }

                    @Override
                    public byte[] onRoutLinkChange(byte[] data) {
                        try {
                            Route route = Route.parseFrom(data);
                            Log.d(TAG, "onRoutLinkChange: " + route.toString());
                            return route.toByteArray();
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onRoutLinkChange: " + ConvertUtils.bytes2String(data));
                        return null;
                    }

                });
            }
        }).start();

    }

}