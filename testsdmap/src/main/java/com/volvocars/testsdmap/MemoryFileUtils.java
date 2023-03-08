package com.volvocars.testsdmap;

import android.os.MemoryFile;

import java.io.FileDescriptor;
import java.io.IOException;

public class MemoryFileUtils {
    public static MemoryFile createMemoryFile(String name, int length){
        try {
            return new MemoryFile(name, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileDescriptor getFileDescriptor(MemoryFile memoryFile){
        return (FileDescriptor) ReflectUtils.invoke(
                "android.os.MemoryFile",
                memoryFile,
                "getFileDescriptor"
        );
    }
}
