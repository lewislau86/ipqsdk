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
                Intent intent = new Intent("com.xiaoyu.whale.sdk.VPN_OPERATE");
                intent.setComponent(new ComponentName("com.xiaoyu.whale",
                        "com.xiaoyu.whale.receiver.VpnOperateReceiver"));
                intent.putExtra("type", 1); //连接VPN
                intent.putExtra("province", "四川省"); //可选，指定连接vpn的省份
                intent.putExtra("city", "成都市"); //可选，指定连接vpn的城市
                intent.putExtra("username", "testuser"); //可选，如果sdkAPP已登录则不需要
                intent.putExtra("password", "testpwd"); //可选，如果sdkAPP已登录则不需要
                sendBroadcast(intent);
            }
        });
        findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent("com.xiaoyu.whale.sdk.VPN_OPERATE");
                intent.setComponent(new ComponentName("com.xiaoyu.whale",
                        "com.xiaoyu.whale.receiver.VpnOperateReceiver"));
                intent.putExtra("type", 2); //断开vpn
                sendBroadcast(intent);
            }
        });
        //注册接受vpn返回结果的广播
        receiver = new StateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xiaoyu.whale.api.VPN_RESULT");
        registerReceiver(receiver, filter);
    }


    @Override
    protected void onResume()
    {
        //判断是否安装sdkAPP
        if (!checkSsExist())
            findViewById(R.id.tv_hint).setVisibility(View.VISIBLE);
        else findViewById(R.id.tv_hint).setVisibility(View.GONE);
        super.onResume();
    }

    public final static int IDLE = 0; //
    public final static int CONNECTING = 1; //连接中
    public final static int CONNECTED = 2; //已连接
    public final static int STOPPING = 3;  //断开中
    public final static int STOPPED = 4; //已断开
    public final static int OtherState = 9; //其他vpn状态，可以忽略
    public final static int GetConfig = 10; //获取配置文件
    public final static int UserError = 20; //用户权限不足
    public final static int ConfigError = 21;  //没有获取到vpn配置，或者配置错误
    public final static int NotSupport = 22; //系统不支持vpn服务
    public final static int AuthDenied = 23; //权限不足...vpn请求对话框被决绝
    public final static int UnknownError = 100;  //其他错误

    public class StateReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int state = intent.getIntExtra("VpnResult", IDLE);
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
                case UserError:
                    tv_state.setText("用户权限不足");
                    break;
                case ConfigError:
                    tv_state.setText("没有获取到vpn配置，或者配置错误");
                    break;
                case GetConfig:
                    tv_state.setText("获取配置文件");
                    break;
                case NotSupport:
                    tv_state.setText("系统不支持");
                    break;
                case UnknownError:
                    tv_state.setText("未知错误");
                    break;
                case AuthDenied:
                    tv_state.setText("权限不足");
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
        return checkApkExist(this, "com.xiaoyu.whale");
    }

}
