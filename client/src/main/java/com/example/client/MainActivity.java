package com.example.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.liuwei.ipcserver.IAddAidlInterface;
import com.example.liuwei.ipcserver.ICallback;
import com.example.liuwei.ipcserver.bean.Person;

public class MainActivity extends AppCompatActivity {

    private Button addPerson;
    private IAddAidlInterface iAddAidlInterface;

    ICallback callback = new ICallback.Stub() {
        @Override
        public void onChange(Person person) throws RemoteException {
            Log.d("tag", "=！！！！！！！====onChange=====name: "+person.getName()+ " age: "+person.getAge());
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPerson = findViewById(R.id.add_person);

        startServices();


        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for (int i = 0; i < 1; i++) {
                        int liuwei = iAddAidlInterface.addPerson(new Person("liurtwffffffffwei: "+i, 32 + 2*i));
                        Log.d("tag", "===liuwei size=="+liuwei);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void startServices() {
        Intent intent = new Intent("com.intent.addperson");
        intent.setPackage("com.example.liuwei.ipcserver");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
}
