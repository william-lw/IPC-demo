package com.example.client2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.liuwei.ipcserver.IAddAidlInterface;
import com.example.liuwei.ipcserver.ICallback;
import com.example.liuwei.ipcserver.bean.Person;

public class MainActivity extends AppCompatActivity {
    private IAddAidlInterface iAddAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServices();
    }


    private void startServices() {
        Intent intent = new Intent("com.intent.addperson");
        intent.setPackage("com.example.liuwei.ipcserver");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }


    ICallback callback = new ICallback.Stub() {
        @Override
        public void onChange(Person person) throws RemoteException {
            Log.d("tag", "=×××××××××&&&&&&&&&&&&====onChange=====name: "+person.getName()+ " age: "+person.getAge());
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iAddAidlInterface = IAddAidlInterface.Stub.asInterface(iBinder);
            try {
                iAddAidlInterface.registerCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                iAddAidlInterface.unregisterCallback(callback);
                iAddAidlInterface = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}
