# SkfCardApp

This is the SKF SIM card application to example the SKF library.

Next is the develop environment:

1 java jdk8;

2 Android Studio 3.0.0(above);

3 Android sdk 26;(ndk not used);

4 Current supportted 4 interfaces, which use asynchronous callback:
  
  Please set SkfCallback() before you call any function, otherwise you can't get any feedback;  

  1) SkfInterface.getSkfInstance().SKF_EnumDev(getApplicationContext()); // enum device and init environment, return "device" for connection;
  
  Callback function is onEnumDev(String result);
  
  2) SkfInterface.getSkfInstance().SKF_ConnectDev("device"); // Connect Device by parameter "device", return true for success, false for failure;
  
  Callback function is onConnectDev(String result);
  
  3) SkfInterface.getSkfInstance().SKF_GetDevInfo("device"); // Get Device Info by parameter "device", return device info data;
  
  Callback function is onDisconnectDev(String result);
  
  4) SkfInterface.getSkfInstance().SKF_DisconnectDev("device"); // Disconnect Device by parameter "device", return true for success, false for failure;
  
  Callback function is onGetDevInfo(String result);
  
  NOte: The String result is Json format, which will provide more information, such as:
       
	  code 0 represents success, other value is failure.

      {code: 0, tips: "ok"; data: "xxxxxxx" }
	  
      {code: 1, tips: "参数错误"; data: "xxxxxxx" }
	  
      {code: 2, tips: "没有连接"; data: "xxxxxxx" }
	  
      {code: 3, tips: "处理错误"; data: "xxxxxxx" }

5 Sdk is CardEmulation-1.0.2.aar file, please create libs directory in project, and place the CardEmulation-1.0.2.aar library in the libs directory;

  Add next in project build.gradle file: 
  
repositories {

    flatDir {
	
        dirs 'libs'
		
    }
	
}

dependencies {

    compile (name:'CardEmulation-1.0.2', ext:'aar')
	
}

6 Please refer the example project: https://github.com/carlshen/SkfCardApp

7 Any question, please contact me.
