package com.example.appclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidltest.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private EditText num1_et,num2_et;
    private Button result_bt;
    private TextView result_tv;

    IMyAidlInterface iMyAidlInterface;

    private ServiceConnection conn = new ServiceConnection() {

        //绑定远程服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        //断开远程服务的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //回收资源
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        result_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = num1_et.getText().toString().trim();
                String num2 = num2_et.getText().toString().trim();
                if (TextUtils.isEmpty(num1)||TextUtils.isEmpty(num2)){
                    Toast.makeText(MainActivity.this,"参数不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        //调用远程服务获取结果
                        int res = iMyAidlInterface.add(Integer.valueOf(num1),Integer.valueOf(num2));
                        result_tv.setText(res+"");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"获取结果失败，检查参数格式是否正确。",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //绑定服务端的service
        bindService();
    }

    private void initView() {
        num1_et = (EditText) findViewById(R.id.num1_et);
        num2_et = (EditText) findViewById(R.id.num2_et);
        result_bt = (Button) findViewById(R.id.result_bt);
        result_tv = (TextView) findViewById(R.id.result_tv);
    }

    private void bindService() {
        //绑定服务端的服务,由于服务端和客户端不在一个进程，所以我们需要直接指定服务端的包名和类名，注意类名要写全。
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.aidltest","com.example.aidltest.IMyService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开服务端的service
        unbindService(conn);
    }
}
