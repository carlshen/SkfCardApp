# SkfCardApp

这是超级SIM卡SKF接口的例子程序。

开发环境：
1 java jdk8;
2 Android Studio 3.0.0(以上);
3 Android sdk 26;(ndk没有使用);
4 目前支持4个接口:
  1）枚举设备：SkfInterface.getSkfInstance().SKF_EnumDev(getApplicationContext()); 返回设备名称，用于下面的连接等调用；
  2）连接设备：SkfInterface.getSkfInstance().SKF_ConnectDev("device"); 连接设备，传入枚举设备获得的"device"，成功返回true，失败返回false;
  3）获取设备信息：SkfInterface.getSkfInstance().SKF_GetDevInfo("device"); 获取设备信息，传入枚举设备获得的"device"，返回设备信息;
  4）断开连接：SkfInterface.getSkfInstance().SKF_DisconnectDev("device"); 断开连接，传入枚举设备获得的"device"，成功返回true，失败返回false;

5 本sdk是CardEmulation-1.0.0.aar文件，请在项目里建立libs目录，把文件CardEmulation-1.0.0.aar放在libs目录下面;
  并且在编译文件build.gradle中加入下面的脚本：  
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile (name:'CardEmulation-1.0.0', ext:'aar')
}

6 请参考本sdk使用的例子项目： https://github.com/carlshen/SkfCardApp
7 如果有任何问题，请联系我。
