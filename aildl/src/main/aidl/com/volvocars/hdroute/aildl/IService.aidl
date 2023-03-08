// IService.aidl
package com.volvocars.hdroute.aildl;

// Declare any non-default types here with import statements
import com.volvocars.hdroute.aildl.IGNSSChangeListener;
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
