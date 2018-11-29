######首先安装sdkApp文件夹下的sdkApp.apk




#连接和断开均为启动service方式
#暂定参数param 1: 连接，2:断开，具体参见MainActivity代码

#连接vpn
                   Intent intent = new Intent();
                   intent.putExtra("type", 1); //连接VPN
                   intent.putExtra("province", "四川省"); //可选，指定连接vpn的省份
                   intent.putExtra("city", "成都市"); //可选，指定连接vpn的城市
                   intent.putExtra("username", ""); //
                   intent.putExtra("key", ""); //
                   intent.setComponent(new ComponentName("com.github.shadowsocksdemo", "com.github.shadowsocksdemo.service.CentreService"));
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                   {
                       startForegroundService(intent);
                   } else
                   {
                       startService(intent);
                   }

#断开vpn
                Intent intent = new Intent();
                intent.putExtra("type", 2); //断开vpn
                intent.setComponent(new ComponentName("com.github.shadowsocksdemo", "com.github.shadowsocksdemo.service.CentreService"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    startForegroundService(intent);
                } else
                {
                    startService(intent);
                }
                        
                        
#结果由广播方式回传action:com.shadowsocks.state
    public class StateReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
           int state=intent.getIntExtra("state", 0);
        }
        
     }   

参数state

    private final int IDLE = 0; //
    private final int CONNECTING = 1; //连接中
    private final int CONNECTED = 2; //已连接
    private final int STOPPING = 3;  //断开中
    private final int STOPPED = 4; //已断开
    private final int UserError=10; //用户权限不足 
    private final int NoServer = 11;  //没有获取到ss服务器
    private final int UnknownError = 12;  //位置错误



