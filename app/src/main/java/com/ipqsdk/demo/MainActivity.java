package com.ipqsdk.demo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ipqsdk.demo.R;


public class MainActivity extends AppCompatActivity
{
    TextView tv_state;
    StateReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 参数json
     * {"code":0}
     * code--1: 连接，2:断开
     */

    private void init()
    {
        tv_state = findViewById(R.id.tv_state);
        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.putExtra("param", "{\"code\":1}");
                intent.setComponent(new ComponentName("com.github.shadowsocksdemo", "com.github.shadowsocksdemo.service.CentreService"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    startForegroundService(intent);
                } else
                {
                    startService(intent);
                }
            }
        });
        findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.e("MainActivity", "onClick");
                Intent intent = new Intent();
                intent.putExtra("param", "{\"code\":2}");
                intent.setComponent(new ComponentName("com.github.shadowsocksdemo", "com.github.shadowsocksdemo.service.CentreService"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    startForegroundService(intent);
                } else
                {
                    startService(intent);
                }
            }
        });
        receiver = new StateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.shadowsocks.state");
        registerReceiver(receiver, filter);
    }


    @Override
    protected void onResume()
    {
        if (!checkSsExist())
            findViewById(R.id.tv_hint).setVisibility(View.VISIBLE);
        else findViewById(R.id.tv_hint).setVisibility(View.GONE);
        super.onResume();
    }

    private final int IDLE = 0;
    private final int CONNECTING = 1;
    private final int CONNECTED = 2;
    private final int STOPPING = 3;
    private final int STOPPED = 4;
    private final int PermissionDenied = 10;
    private final int NoServer = 11;  //没有获取到ss服务器
    private final int UnknownError = 12;

    public class StateReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int state = intent.getIntExtra("state", IDLE);
            switch (state)
            {
                case IDLE:
                    tv_state.setText("空闲");
                    break;
                case CONNECTING:
                    tv_state.setText("连接中");
                    break;
                case CONNECTED:
                    tv_state.setText("已连接===================");
                    break;
                case STOPPING:
                    tv_state.setText("断开中");
                    break;
                case STOPPED:
                    tv_state.setText("已断开");
                    break;
                case PermissionDenied:
                    tv_state.setText("权限不足");
                    break;
                case NoServer:
                    tv_state.setText("没有获取到服务器");
                    break;
                case UnknownError:
                    tv_state.setText("未知错误");
                    break;
            }
        }

    }


    public boolean checkApkExist(Context context, String packageName)
    {
        if (TextUtils.isEmpty(packageName))
            return false;
        try
        {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    public boolean checkSsExist()
    {
        return checkApkExist(this, "com.github.shadowsocksdemo");
    }

}
