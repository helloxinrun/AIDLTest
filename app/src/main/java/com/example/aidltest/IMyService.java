package com.example.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by BigRun on 2016/7/31.
 */
public class IMyService extends Service {

    /**
     * 当客户端绑定到该服务的时候，执行此方法。
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e("服务端","收到了客户端的请求，输入的参数是："+num1+"和"+num2);
            return num1+num2;
        }
    };
}
