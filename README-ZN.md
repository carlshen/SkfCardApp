# SkfCardApp

这是超级SIM卡SKF接口SDK的例子程序。

开发环境：

1 java jdk8;

2 Android Studio 3.0.0(以上);

3 Android sdk 26;(ndk没有使用);

4 目前SDK支持20个接口，使用异步回调的方式:

  在调用所有接口前，请先调用SkfInterface.getSkfInstance().SkfCallback()设置SkfCallback回调接口，否则将不会收到任何反馈；

  1）枚举设备：SkfInterface.getSkfInstance().SKF_EnumDev(getApplicationContext()); // 返回设备名称，用于下面的连接等调用；

     回调函数onEnumDev(String result);

  2）连接设备：SkfInterface.getSkfInstance().SKF_ConnectDev("device"); // 连接设备，传入枚举设备获得的"device"；

     回调函数onConnectDev(String result);

  3）获取设备信息：SkfInterface.getSkfInstance().SKF_GetDevInfo("device"); // 获取设备信息，传入枚举设备获得的"device"，返回设备信息;

     回调函数onGetDevInfo(String result);

  4）断开连接：SkfInterface.getSkfInstance().SKF_DisconnectDev("device"); // 断开连接，传入枚举设备获得的"device"; 

     回调函数onDisconnectDev(String result);

  5）创建应用：SkfInterface.getSkfInstance().SKF_CreateApplication("device")； // 暂时可以不调用，缺省已经创建
  
     回调函数onCreateApplication(String result);
  
  6）打开应用：SkfInterface.getSkfInstance().SKF_OpenApplication("device")； // 暂时可以不调用，缺省已经打开
  
     回调函数OpenApplication(String result);

  7）创建容器：SkfInterface.getSkfInstance().SKF_CreateContainer("device")； // 暂时可以不调用，缺省已经创建
  
     回调函数onCreateContainer(String result);
	 
  8）导入会话密钥：SkfInterface.getSkfInstance().SKF_SetSymmKey(String device, String key, int AlgID)； 

     传入枚举设备获得的"device"；密钥key(128bit，即16字节长度的字符串)；算法AlgID(1025表示ECB算法，1026表示CBC算法，其它暂时不支持);

     回调函数onSetSymmKey(String result);

	 返回Json格式的字符串，code: 0表示成功，data: "xxxxxxx"返回密钥的句柄，用于后面具体的加密解密操作；
	 
  9）当前密钥的设置状态：SkfInterface.getSkfInstance().SKF_CheckSymmKey(String device)； 

     传入枚举设备获得的"device";

     回调函数onCheckSymmKey(String result);

	 返回Json格式的字符串，code: 0表示成功，已经设置密钥；其它值是失败，没有设置密钥；

 10）加密初始化：SkfInterface.getSkfInstance().SKF_EncryptInit(String key)；

     传入密钥的句柄"key"；

     回调函数onEncryptInit(String result);

 11）单组数据加密：SkfInterface.getSkfInstance().SKF_Encrypt(String key, String data)；

     传入密钥的句柄"key"；要加密的数据data；
  
     回调函数onEncrypt(String result);

	 返回Json格式的字符串，code: 0表示成功，data: "xxxxxxx"表示返回加密数据的结果；

 12）解密初始化：SkfInterface.getSkfInstance().SKF_DecryptInit(String key)；

     传入密钥的句柄"key"；

     回调函数onDecryptInit(String result);

 13）单组数据解密：SkfInterface.getSkfInstance().SKF_Decrypt(String key, String data)；

     传入密钥的句柄"key"；要解密的数据data；要解密的数据长度len；
  
     回调函数onDecrypt(String result);

	 返回Json格式的字符串，code: 0表示成功，data: "xxxxxxx"表示返回解密数据的结果；

 14）文件流数据加密：SkfInterface.getSkfInstance().SKF_EncryptFile(String key, File inputFile, File outputFile)；

     传入密钥的句柄"key"；要加密的文件inputFile；加密后的文件outputFile；

	 注意：由于文件流比较耗时，最好在子线程中调用这个接口；
  
     回调函数onEncryptFile(String result);

	 返回Json格式的字符串，code: 0表示成功，outputFile表示返回加密数据的结果；

 15）文件流数据解密：SkfInterface.getSkfInstance().SKF_DecryptFile(String key, File inputFile, File outputFile)；

     传入密钥的句柄"key"；要解密的文件inputFile；解密后的文件outputFile；

	 注意：由于文件流比较耗时，最好在子线程中调用这个接口；

     回调函数onDecryptFile(String result);

	 返回Json格式的字符串，code: 0表示成功，outputFile表示返回解密数据的结果；

 16）密码杂凑初始化，支持SM3：SkfInterface.getSkfInstance().SKF_DigestInit(String device)；

     传入枚举设备获得的"device";

     回调函数onDigestInit(String result);

	 返回Json格式的字符串，code: 0表示成功；

 17）单组数据密码杂凑，支持SM3：SkfInterface.getSkfInstance().SKF_Digest(String data)；

     传入枚举设备获得的"device"，需要摘要的数据;

     回调函数onDigest(String result);

	 返回Json格式的字符串，code: 0表示成功，data："xxxx"中是数据摘要的结果；

 18）生成ECC密钥对，支持SM2：SkfInterface.getSkfInstance().SKF_GenECCKeyPair(String device)；

     传入枚举设备获得的"device";

     回调函数onGenECCKeyPair(String result);

	 返回Json格式的字符串，code: 0表示成功，data："xxxx"中是64字节公钥值；

 19）ECC签名，支持SM2：SkfInterface.getSkfInstance().SKF_ECCSignData(String key, String data)；

     传入上面获得的公钥值，需要签名的数据;

     回调函数onECCSignData(String result);

	 返回Json格式的字符串，code: 0表示成功，data："xxxx"中是数据的64字节签名值；

 20）ECC验签，支持SM2：SkfInterface.getSkfInstance().SKF_ECCVerify(String key, String sign, String data)；

     传入上面获得的公钥值，签名值，需要验证签名的数据;

     回调函数onECCVerify(String result);

	 返回Json格式的字符串，code: 0表示验证签名成功，其它表示验证签名失败；


  返回的结果String result是Json格式的，这样能提供更详细的信息，具体格式如下：  

      {code: 0, tips: "ok"; data: "xxxxxxx" }
	  
      {code: 1, tips: "参数错误"; data: "xxxxxxx" }
	  
      {code: 2, tips: "没有连接"; data: "xxxxxxx" }
	  
      {code: 3, tips: "处理错误"; data: "xxxxxxx" }

   注意：code返回0就是成功，其它值是失败；data里是具体的返回的数据，比如设置密钥返回的句柄，加密返回的密文等。
   
   注意：接口调用需要一定的顺序，比如必须先调用SKF_EnumDev返回设备名称，然后用返回的设备名称做为参数调用后面的连接等操作；
   
         连接成功后，才能调用SKF_SetSymmKey设置密钥和算法，返回密钥的句柄，用于后面的加密解密等操作；
		 
		 设置好密钥和算法后，加密解密的时候，必须先初始化，然后才能加密解密操作；比如先调用SKF_EncryptInit后，再调用SKF_Encrypt进行数据块的加密；
		 
		 文件流加密必须先调用SKF_EncryptInit后，再调用SKF_EncryptFile进行文件流的加密；
		 文件流解密也必须先调用SKF_DecryptInit后，再调用SKF_DecryptFile进行文件流的解密。
	
	另外：通过SkfInterface.getSkfInstance().getConnectionStatus()可以获得当前连接状态的信息。true连接成功，false没有连接。
	
	通过SkfInterface.getSkfInstance().setDebugFlag(true/false)可以控制是否打印SDK的日志，用于调试。


5 本sdk是CardEmulation-1.2.0.aar文件，请在项目里建立libs目录，把文件CardEmulation-1.2.0.aar放在libs目录下面;

  并且在编译文件build.gradle中加入下面的脚本：  

repositories {

    flatDir {
	
        dirs 'libs'
		
    }
	
}

dependencies {

    compile (name:'CardEmulation-1.2.0', ext:'aar')
	
}

6 请参考本sdk使用的例子项目： https://github.com/carlshen/SkfCardApp

    EncryptUtil.java文件中有生成密钥的函数，具体调用请参考例子中的代码。


7 如果有任何问题，请联系我。

