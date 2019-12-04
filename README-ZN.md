# SkfCardApp

这是超级SIM卡SKF接口的例子程序。

开发环境：

1 java jdk8;

2 Android Studio 3.0.0(以上);

3 Android sdk 26;(ndk没有使用);

4 目前支持4个接口，使用异步回调的方式:

  在调用所有接口前，请先回调SkfCallback()接口，否则将不会收到任何反馈；

  1）枚举设备：SkfInterface.getSkfInstance().SKF_EnumDev(getApplicationContext()); 返回设备名称，用于下面的连接等调用；
  
     回调函数onEnumDev(String result);
  
  2）连接设备：SkfInterface.getSkfInstance().SKF_ConnectDev("device"); 连接设备，传入枚举设备获得的"device"，成功返回true，失败返回false;

     回调函数onConnectDev(String result);
  
  3）获取设备信息：SkfInterface.getSkfInstance().SKF_GetDevInfo("device"); 获取设备信息，传入枚举设备获得的"device"，返回设备信息;

     回调函数onDisconnectDev(String result);
  
  4）断开连接：SkfInterface.getSkfInstance().SKF_DisconnectDev("device"); 断开连接，传入枚举设备获得的"device"，成功返回true，失败返回false; 

     回调函数onGetDevInfo(String result);

  注意：返回的结果String result是Json格式的，这样能提供更详细的信息，具体格式如下，可以参考本项目使用的例子：
  
      code返回0就是成功，其它值是失败，具体参考下面的说明；
  
      {code: 0, tips: "ok"; data: "xxxxxxx" }
	  
      {code: 1, tips: "参数错误"; data: "xxxxxxx" }
	  
      {code: 2, tips: "没有连接"; data: "xxxxxxx" }
	  
      {code: 3, tips: "处理错误"; data: "xxxxxxx" }

5 本sdk是CardEmulation-1.0.2.aar文件，请在项目里建立libs目录，把文件CardEmulation-1.0.2.aar放在libs目录下面;

  并且在编译文件build.gradle中加入下面的脚本：  
  
repositories {

    flatDir {
	
        dirs 'libs'
		
    }
	
}

dependencies {

    compile (name:'CardEmulation-1.0.2', ext:'aar')
	
}

6 请参考本sdk使用的例子项目： https://github.com/carlshen/SkfCardApp

7 如果有任何问题，请联系我。
