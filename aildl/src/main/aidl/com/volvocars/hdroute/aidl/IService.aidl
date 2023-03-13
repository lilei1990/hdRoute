// IService.aidl
package com.volvocars.hdroute.aidl;

// Declare any non-default types here with import statements
import com.volvocars.hdroute.aidl.IGNSSChangeListener;
interface IService {

    /**
     * Add a gnss change listener
     */
    void addHDRPPChangeListener(String tag,IGNSSChangeListener listener);
        /**
         * remove a gnss change listener
         */
    void removeHDRPPChangeListener(String tag);


    void sendRouteLink(inout  ParcelFileDescriptor routeLink);

    void onLocationChange(inout byte[] location);
}
