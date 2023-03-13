package com.volvocars.testsdmap;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.volvocars.hdroute.aidl.Route;
import com.volvocars.hdroute.aidl.SDRouteApi;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bt1 = findViewById(R.id.btClick1);
        View bt2 = findViewById(R.id.btClick2);
        View bt3 = findViewById(R.id.btClick3);
        SDRouteApi.init(Utils.getApp());
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLargeData("Route_sample.txt");
                SDRouteApi.onLocationChange(1.0,2.0,3.0);

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route route = Route.newBuilder()
                        .setLength(112552).build();
                SDRouteApi.sendRouteLink(route);
            }
        });
//        CrRoute.newBuilder().
//        Route.newBuilder().addAllLinks().clearLinkNumber()
    }


    private void sendLargeData(String s) {
//        String s = ResourceUtils.readAssets2String("large.jpg");
        /**
         * 读取assets目录下文件
         */
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 将inputStream转换成字节数组
         */
        byte[] byteArray = ConvertUtils.inputStream2Bytes(inputStream);
        SDRouteApi.sendRouteLink(byteArray);


    }

    private void initServices() {

    }
}