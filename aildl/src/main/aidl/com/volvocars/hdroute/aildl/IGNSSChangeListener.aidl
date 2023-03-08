// IGNSSChangeListener.aidl
package com.volvocars.hdroute.aildl;

// Declare any non-default types here with import statements

interface IGNSSChangeListener {

                byte[] onHDFusedPointChange(inout byte[] data);
                byte[] onHDRawPointChange(inout byte[] data);
                void onSDPointChange(inout byte[] data);
                void onSDIMUChange(inout byte[] data);
                ParcelFileDescriptor onRoutLinkChange(inout ParcelFileDescriptor data);
}