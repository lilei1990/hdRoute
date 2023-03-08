package com.volvocars.testhdmap;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.volvocars.hdmap.HDRouteApi;

import com.volvocars.hdroute.aidl.HDRPPChangeListener;
import com.volvocars.hdroute.aidl.Route;

public class MainActivity extends AppCompatActivity {


    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bt1 = findViewById(R.id.btClick1);
        View bt2 = findViewById(R.id.btClick2);
        View bt3 = findViewById(R.id.btClick3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HDRouteApi.init(Utils.getApp());
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HDRouteApi.addHDRPPChangeListener("AutoSDK", new HDRPPChangeListener() {
                    @Override
                    public byte[] onHDFusedPointChange(byte[] data)  {
                        Log.d(TAG, "onHDFusedPointChange: "+ ConvertUtils.bytes2String(data));
                        return new byte[0];
                    }

                    @Override
                    public byte[] onHDRawPointChange(byte[] data)  {
                        Log.d(TAG, "onHDRawPointChange: "+ConvertUtils.bytes2String(data));
                        return new byte[0];
                    }

                    @Override
                    public void onSDPointChange(byte[] data)  {
                        Log.d(TAG, "onSDPointChange: "+ConvertUtils.bytes2String(data));
                    }

                    @Override
                    public void onSDIMUChange(byte[] data)  {
                        Log.d(TAG, "onSDIMUChange: "+ConvertUtils.bytes2String(data));
                    }

                    @Override
                    public byte[] onRoutLinkChange(byte[] data)  {
                        try {
                            Route route = Route.parseFrom(data);
                            Log.d(TAG, "onRoutLinkChange: "+route.toString());
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onRoutLinkChange: "+ConvertUtils.bytes2String(data));
                        return new byte[0];
                    }

                });
            }
        });

    }

}