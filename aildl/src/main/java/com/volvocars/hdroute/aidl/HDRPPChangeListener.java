package com.volvocars.hdroute.aidl;

import com.volvocars.hdroute.aildl.IGNSSChangeListener;

public interface  HDRPPChangeListener {
    byte[] onHDFusedPointChange(byte[] data);

    byte[] onHDRawPointChange(byte[] data);

    void onSDPointChange(byte[] data);

    void onSDIMUChange(byte[] data);

    byte[] onRoutLinkChange(byte[] data);
}
