#首先安装sdkApp文件夹下的sdkApp.apk




#连接和断开均为显式广播sendBroadcast
#暂定参数param 1: 连接，2:断开，具体参见MainActivity代码

#连接vpn

```Java
                Intent intent = new Intent("com.xiaoyu.whale.sdk.VPN_OPERATE");
                intent.setComponent(new ComponentName("com.xiaoyu.whale",
                        "com.xiaoyu.whale.receiver.VpnOperateReceiver"));
                intent.putExtra("type", 1); //连接VPN
                intent.putExtra("province", "四川省"); //可选，指定连接vpn的省份
                intent.putExtra("city", "成都市"); //可选，指定连接vpn的城市
                intent.putExtra("username", "testuser"); //可选，如果sdkAPP已登录则不需要
                intent.putExtra("password", "testpwd"); //可选，如果sdkAPP已登录则不需要
                sendBroadcast(intent);
```
                   
#断开vpn
```Java
                Intent intent = new Intent("com.xiaoyu.whale.sdk.VPN_OPERATE");
                intent.setComponent(new ComponentName("com.xiaoyu.whale",
                        "com.xiaoyu.whale.receiver.VpnOperateReceiver"));
                intent.putExtra("type", 2); //断开vpn
                sendBroadcast(intent);
```                        
                        
#结果由广播方式回传action:com.shadowsocks.state
```Java
    public class StateReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
           int state=intent.getIntExtra("VpnResult", 0);
           //state 当前vpn或者错误状态
        }
        
     }   
```

参数state
```Java
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
```


