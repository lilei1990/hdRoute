package com.volvocars.hdmap;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import com.blankj.utilcode.util.ConvertUtils;
import com.volvocars.hdroute.aidl.HDRPPChangeListener;
import com.volvocars.hdroute.aidl.IRouteApi;
import com.volvocars.hdroute.aidl.MemoryFileUtils;
import com.volvocars.hdroute.aildl.IGNSSChangeListener;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class HDRouteApi extends IRouteApi {
    private static HDRPPChangeListener listener=null;
    private static IGNSSChangeListener ignssChangeListener = new IGNSSChangeListener.Stub() {
        @Override
        public byte[] onHDFusedPointChange(byte[] data) throws RemoteException {
            if (listener != null) {
                return listener.onHDFusedPointChange(data);
            }
            return null;
        }

        @Override
        public byte[] onHDRawPointChange(byte[] data) throws RemoteException {
            if (listener != null) {
                return listener.onHDRawPointChange(data);
            }
            return null;
        }

        @Override
        public void onSDPointChange(byte[] data) throws RemoteException {
            if (listener != null) {
                listener.onSDPointChange(data);
            }
        }

        @Override
        public void onSDIMUChange(byte[] data) throws RemoteException {
            if (listener != null) {
                listener.onSDIMUChange(data);
            }
        }

        @Override
        public ParcelFileDescriptor onRoutLinkChange(ParcelFileDescriptor data) throws RemoteException {
            if (listener != null) {
                FileInputStream fileInputStream = new FileInputStream(data.getFileDescriptor());
                byte[] linkChange = ConvertUtils.inputStream2Bytes(fileInputStream);
                /**
                 * 创建MemoryFile
                 */
                MemoryFile memoryFile = null;
                try {
                    memoryFile = new MemoryFile("routeLink", linkChange.length);


                    /**
                     * 向MemoryFile中写入字节数组
                     */
                    memoryFile.writeBytes(linkChange, 0, 0, linkChange.length);

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
                    return pfd;

                } catch (IOException e) {
                    e.printStackTrace();
                };
            }
            return null;
        }
    };

    public static void sendRouteLink(byte[] routeLink) {


    }
    public static void addHDRPPChangeListener(String tag, HDRPPChangeListener ls) {
        listener = ls;
        try {
            if (myService != null) {
                myService.addHDRPPChangeListener(tag, ignssChangeListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void removeHDRPPChangeListener(String tag) {
        try {
            if (myService != null) {
                myService.removeHDRPPChangeListener("AutoSDK");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
