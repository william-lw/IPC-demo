// IAddAidlInterface.aidl
package com.example.liuwei.ipcserver;

// Declare any non-default types here with import statements
import com.example.liuwei.ipcserver.bean.Person;
import com.example.liuwei.ipcserver.ICallback;
interface IAddAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int a, int b);
    int addPerson(in Person person);
    void registerCallback(in ICallback callback);
    void unregisterCallback(in ICallback callback);
}
