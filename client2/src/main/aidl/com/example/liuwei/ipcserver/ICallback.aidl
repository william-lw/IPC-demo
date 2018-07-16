// ICallback.aidl
package com.example.liuwei.ipcserver;

// Declare any non-default types here with import statements
import com.example.liuwei.ipcserver.bean.Person;
interface ICallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onChange(in Person person);
}
