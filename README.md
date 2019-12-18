# SkfCardApp

This is the SKF SIM card application to example the SKF SDK library.

Next is the develop environment:

1 java jdk8;

2 Android Studio 3.0.0(above);

3 Android sdk 26;(ndk not used);

4 Current SDK supportted 15 interfaces, which use asynchronous callback:
  
  Please use SkfInterface.getSkfInstance().SKF_SetCallback() to set the SkfCallback before you call any function, otherwise you can't get any feedback;  

  1) SkfInterface.getSkfInstance().SKF_EnumDev(getApplicationContext()); // enum device and init environment, return "device" for connection;
  
     Callback function is onEnumDev(String result);

	 return Json format string, code: 0 is ok, data: "xxxxxxx" is the device; other value is failure；
  
  2) SkfInterface.getSkfInstance().SKF_ConnectDev("device"); // Connect Device by parameter "device";
  
     Callback function is onConnectDev(String result);

	 return Json format string, code: 0 is ok; other value is failure；
  
  3) SkfInterface.getSkfInstance().SKF_GetDevInfo("device"); // Get Device Info by parameter "device";
  
     Callback function is onGetDevInfo(String result);

	 return Json format string, code: 0 is ok, data: "xxxxxxx" is the device info data; other value is failure；
  
  4) SkfInterface.getSkfInstance().SKF_DisconnectDev("device"); // Disconnect Device by parameter "device";

     Callback function is onDisconnectDev(String result);

	 return Json format string, code: 0 is ok; other value is failure；

  5）SkfInterface.getSkfInstance().SKF_CreateApplication("device")； // no need called, as default created;

     Callback function is onCreateApplication(String result);

	 return Json format string, code: 0 is ok; other value is failure；

  6）SkfInterface.getSkfInstance().SKF_OpenApplication("device")； // no need called, as default opened;

     Callback function is onOpenApplication(String result);

	 return Json format string, code: 0 is ok; other value is failure；

  7）SkfInterface.getSkfInstance().SKF_CreateContainer("device")； // no need called, as default created;

     Callback function is onCreateContainer(String result);

	 return Json format string, code: 0 is ok; other value is failure；

  8）SkfInterface.getSkfInstance().SKF_SetSymmKey(String device, String key, int AlgID)； // set encrypt key and algorithm;

     input device parameter "device", encrypt key parameter "key"(128bit, or 16 bytes string), algorithm parameter "AlgID"(1025 is ECB algorithm， 1026 is CBC algorithm, others not supported);

     Callback function is onSetSymmKey(String result);
	 
	 return Json format string, code: 0 is ok, data: "xxxxxxx" is the key handle, which be used in following steps；other value is failure；
	 
  9）SkfInterface.getSkfInstance().SKF_CheckSymmKey(String device)； // check the key set status;

     input device parameter "device";

     Callback function is onCheckSymmKey(String result);

	 return Json format string, code: 0 is ok, the cipher key is set; other value is failure, the key is not set yet;

 10）SkfInterface.getSkfInstance().SKF_EncryptInit(String key)；       // encrypt init

     input encrypt key parameter "key"(128bit, or 16 bytes string);

     Callback function is onEncryptInit(String result);

	 return Json format string, code: 0 is ok; other value is failure；

 11）SkfInterface.getSkfInstance().SKF_Encrypt(String key, String data)；  // encrypt data

     input encrypt key parameter "key"(128bit, or 16 bytes string), encrypt data;
  
     Callback function is onEncrypt(String result);

	 return Json format string, code: 0 is ok, data: "xxxxxxx" is the encrypt result；; other value is failure；

 12）SkfInterface.getSkfInstance().SKF_DecryptInit(String key)；    // decrypt init

     input encrypt key parameter "key"(128bit, or 16 bytes string);

     Callback function is onDecryptInit(String result);

	 return Json format string, code: 0 is ok; other value is failure；

 13）SkfInterface.getSkfInstance().SKF_Decrypt(String key, String data)； // decrypt data

     input decrypt key parameter "key"(128bit, or 16 bytes string), decrypt data;
  
     Callback function is onDecrypt(String result);

	 return Json format string, code: 0 is ok, data: "xxxxxxx" is the decrypt result; other value is failure；

 14）SkfInterface.getSkfInstance().SKF_EncryptFile(String key, File inputFile, File outputFile)；// file encrypt data

     input encrypt key parameter "key"(128bit, or 16 bytes string), encrypt input file, encrypt result file;

     Note: please put this function in sub-thread, as this will be take long time.
  
     Callback function is onEncryptFile(String result);

	 return Json format string, code: 0 is ok, outputFile is the encrypt result file；

 15）SkfInterface.getSkfInstance().SKF_DecryptFile(String key, File inputFile, File outputFile)；// file decrypt data

     input decrypt key parameter "key"(128bit, or 16 bytes string), decrypt input file, decrypt result file;

     Note: please put this function in sub-thread, as this will be take long time.

     Callback function is onDecryptFile(String result);

	 return Json format string, code: 0 is ok, outputFile is the decrypt result file；

  The String result is Json format, which will provide more information, such as:       

      {code: 0, tips: "ok"; data: "xxxxxxx" }

      {code: 1, tips: "parameter error"; data: "xxxxxxx" }

      {code: 2, tips: "do not connect"; data: "xxxxxxx" }

      {code: 3, tips: "process error"; data: "xxxxxxx" }

  NOte:  code 0 represents success, other value is failure; data contains the returned value, such as key handle, data, etc.
  
  Note:  The call interface has the basice Sequence before and after; Please call SKF_EnumDev firstly to get device name, which used for following steps.
   
         Please call SKF_ConnectDev before any encrpyt or decrpyt operations, as connection success is the precondition.
		 
		 Then you can call SKF_SetSymmKey to set cipher and algorithm, which will return the cipher handle for following operations.
		 
		 You should call SKF_EncryptInit firstly, then call SKF_Encrypt to encrpyt file data.
		 
		 While file decrption need call SKF_DecryptInit firstly, then call SKF_DecryptFile for file decryption.		 
	
	NOte: SkfInterface.getSkfInstance().getConnectionStatus() can get current connection status, true for success, while false for failure.
	
	SkfInterface.getSkfInstance().setDebugFlag(true/false) can set SDK log flag, which is usefule for debug.


5 Sdk is CardEmulation-1.1.0.aar file, please create libs directory in project, and place the CardEmulation-1.1.0.aar library in the libs directory;

  Add next in project build.gradle file: 
  
repositories {

    flatDir {
	
        dirs 'libs'
		
    }

}

dependencies {

    compile (name:'CardEmulation-1.1.0', ext:'aar')

}

6 Please refer the example project: https://github.com/carlshen/SkfCardApp

    EncryptUtil.java file has the example for generating cipher key, please refer the example for detail.


7 Any question, please contact me.

