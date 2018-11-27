#首先安装sdkApp文件下的app




#连接和断开均为启动service方式
#暂定参数param 1: 连接，2:断开，具体参见MainActivity代码

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
                        
                        
#结果由广播方式回传action:com.shadowsocks.state

参数state

    private final int IDLE = 0;
    private final int CONNECTING = 1;
    private final int CONNECTED = 2; //已连接
    private final int STOPPING = 3;
    private final int STOPPED = 4; //已断开
    private final int NoServer = 11;  //没有获取到ss服务器
    private final int UnknownError = 12;  



