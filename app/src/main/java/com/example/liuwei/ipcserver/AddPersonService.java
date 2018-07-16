package com.example.liuwei.ipcserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.liuwei.ipcserver.bean.Person;

import java.util.ArrayList;

public class AddPersonService extends Service {
    private ArrayList<Person> peoples;

    RemoteCallbackList<ICallback> callbackList;

    IAddAidlInterface.Stub ibinder = new IAddAidlInterface.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return 0;
        }

        @Override
        public int addPerson(Person person) throws RemoteException {
            peoples.add(person);
            // 添加回调， 将数据变化通知到所有注册过的地方
            startCallback(person);
            Log.d("tag", "=add person=======name:"+person.getName()+ " age:"+person.getAge());
            return peoples.size();
        }

        @Override
        public void registerCallback(ICallback callback) throws RemoteException {
            if (callback != null) {
                callbackList.register(callback);
            }
        }

        @Override
        public void unregisterCallback(ICallback callback) throws RemoteException {
            if (callback != null) {
                callbackList.unregister(callback);
            }
        }
    };

    private void startCallback(Person person) {
        int i = callbackList.beginBroadcast();
        for (int j = 0; j < i; j++) {
            try {
                callbackList.getBroadcastItem(j).onChange(person);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        callbackList.finishBroadcast();
    }


    public AddPersonService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        peoples = new ArrayList<>();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        callbackList =  new RemoteCallbackList<ICallback>();
        return  ibinder;
    }
}
