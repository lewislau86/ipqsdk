#安装sdkApp文件下的app




#连接和断开均为启动service方式
#暂定参数param 1: 连接，2:断开，具体参见MainActivity代码

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
                        
                        
#结果由广播方式回传action:com.shadowsocks.state



