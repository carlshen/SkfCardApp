package com.tongxin.skfcard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tongxin.cardemulation.SkfCallback;
import com.tongxin.cardemulation.SkfInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by carl on 2019/11/20.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private boolean mLogShown = false;
    private Button mButtonEnum = null;
    private Button mButtonConnect = null;
    private Button mButtonInfo = null;
    private Button mButtonDisconnect = null;
    private Button mCreateApp = null;
    private Button mOpenApp = null;
    private Button mCreateCon = null;
    private Button mSetSymKey = null;
    private Button mEncrInit = null;
    private Button mEncrypt = null;
    private Button mDecrInit = null;
    private Button mDecrypt = null;
    private Button mEncryptFile = null;
    private Button mDecryptFile = null;
    private TextView tvResult = null;
    private String deviceName = null;
    private String deviceData = null;
    private String KeyData = null;
    private String EncrpytData = null;
    private String DecrpytData = null;
    private SkfCallback Callback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CardLogFragment fragment = new CardLogFragment();
        transaction.add(R.id.sample_content_fragment, fragment, "log");
        transaction.commit();

        tvResult = (TextView) findViewById(R.id.tv_result);
        mButtonEnum = (Button) findViewById(R.id.btn_device);
        mButtonEnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkfInterface.getSkfInstance().SKF_EnumDev(getApplicationContext());
            }
        });
        mButtonConnect = (Button) findViewById(R.id.btn_connect);
        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_ConnectDev(deviceName);
//                tvResult.setText("ConnectDev: " + result);
            }
        });
        mButtonInfo = (Button) findViewById(R.id.btn_info);
        mButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_GetDevInfo(deviceName);
//                tvResult.setText("DevInfo: " + result);
            }
        });
        mButtonDisconnect = (Button) findViewById(R.id.btn_disconnect);
        mButtonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_DisconnectDev(deviceName);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });

        // ======== next 2nd interfaces
        mCreateApp = (Button) findViewById(R.id.btn_createapp);
        mCreateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_CreateApplication(deviceName);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mOpenApp = (Button) findViewById(R.id.btn_openapp);
        mOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_OpenApplication(deviceName);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mCreateCon = (Button) findViewById(R.id.btn_createcon);
        mCreateCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_CreateContainer(deviceName);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mSetSymKey = (Button) findViewById(R.id.btn_setsymkey);
        mSetSymKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symKey = "";
                try {
                    byte[] key = EncryptUtil.generateKey();
                    symKey = EncryptUtil.ByteArrayToHexString(key);
                } catch (Exception e) {
                    e.printStackTrace();
                    symKey = "11223344556677881122334455667788";
                }
                Log.i(TAG, "====== mSetSymKey = " + symKey);
                boolean result = SkfInterface.getSkfInstance().SKF_SetSymmKey(deviceName, symKey, 1025);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mEncrInit = (Button) findViewById(R.id.btn_encrinit);
        mEncrInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_EncryptInit(KeyData);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mEncrypt = (Button) findViewById(R.id.btn_encrpyt);
        mEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder encbuilder = new StringBuilder(2048);
                for (int i = 0; i < 88; i++) {
                    encbuilder.append("1122334455667788990011223344556677889900");
                }
                EncrpytData = encbuilder.toString();
                boolean result = SkfInterface.getSkfInstance().SKF_Encrypt(KeyData, EncrpytData);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mDecrInit = (Button) findViewById(R.id.btn_decrinit);
        mDecrInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_DecryptInit(KeyData);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mDecrypt = (Button) findViewById(R.id.btn_decrypt);
        mDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = SkfInterface.getSkfInstance().SKF_Decrypt(KeyData, DecrpytData);
//                tvResult.setText("DisconnectDev: " + result);
            }
        });
        mEncryptFile = (Button) findViewById(R.id.btn_encrfile);
        mEncryptFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File inFile = new File(EncryptUtil.getExternalStoragePath() + "/entest.txt");
                        File ouFile = new File(EncryptUtil.getExternalAppFilesPath(getApplicationContext()) + "/enresult.txt");
                        try {
                            boolean result = SkfInterface.getSkfInstance().SKF_EncryptFile(KeyData, inFile, ouFile);
                            Log.i(TAG, "SKF_EncryptFile result = " + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        mDecryptFile = (Button) findViewById(R.id.btn_decrfile);
        mDecryptFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        File inFile = new File(EncryptUtil.getExternalStoragePath() + "/detest.txt");
                        File inFile = new File(EncryptUtil.getExternalAppFilesPath(getApplicationContext()) + "/enresult.txt");
                        File ouFile = new File(EncryptUtil.getExternalAppFilesPath(getApplicationContext()) + "/deresult.txt");
                        try {
                            boolean result = SkfInterface.getSkfInstance().SKF_DecryptFile(KeyData, inFile, ouFile);
                            Log.i(TAG, "SKF_DecryptFile result = " + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        Callback = new SkfCallback() {
            @Override
            public void onEnumDev(String result) {
                deviceName = result;
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceName = json.optString("data");
                        Log.i(TAG, "onEnumDev code = " + code);
                        Log.i(TAG, "onEnumDev tip = " + tip);
                        Log.i(TAG, "onEnumDev data = " + deviceName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceName);
            }

            @Override
            public void onConnectDev(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onConnectDev code = " + code);
                        Log.i(TAG, "onConnectDev tip = " + tip);
                        Log.i(TAG, "onConnectDev data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }

            @Override
            public void onDisconnectDev(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onDisconnectDev code = " + code);
                        Log.i(TAG, "onDisconnectDev tip = " + tip);
                        Log.i(TAG, "onDisconnectDev data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }

            @Override
            public void onGetDevInfo(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onGetDevInfo code = " + code);
                        Log.i(TAG, "onGetDevInfo tip = " + tip);
                        Log.i(TAG, "onGetDevInfo data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }

            @Override
            public void onCreateApplication(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onCreateApplication code = " + code);
                        Log.i(TAG, "onCreateApplication tip = " + tip);
                        Log.i(TAG, "onCreateApplication data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }

            @Override
            public void onOpenApplication(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onOpenApplication code = " + code);
                        Log.i(TAG, "onOpenApplication tip = " + tip);
                        Log.i(TAG, "onOpenApplication data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }

            @Override
            public void onCreateContainer(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onCreateContainer code = " + code);
                        Log.i(TAG, "onCreateContainer tip = " + tip);
                        Log.i(TAG, "onCreateContainer data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }

            @Override
            public void onSetSymmKey(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        KeyData = json.optString("data");
                        Log.i(TAG, "onSetSymmKey code = " + code);
                        Log.i(TAG, "onSetSymmKey tip = " + tip);
                        Log.i(TAG, "onSetSymmKey KeyData = " + KeyData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("KeyData: " + KeyData);
            }

            @Override
            public void onEncryptInit(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onEncryptInit code = " + code);
                        Log.i(TAG, "onEncryptInit tip = " + tip);
                        Log.i(TAG, "onEncryptInit data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("onEncryptInit: " + deviceData);
            }

            @Override
            public void onEncrypt(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        DecrpytData = json.optString("data");
                        Log.i(TAG, "onEncrypt code = " + code);
                        Log.i(TAG, "onEncrypt tip = " + tip);
                        Log.i(TAG, "onEncrypt data = " + DecrpytData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("encrpytedData: " + DecrpytData);
            }

            @Override
            public void onEncryptFile(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onEncryptFile code = " + code);
                        Log.i(TAG, "onEncryptFile tip = " + tip);
                        Log.i(TAG, "onEncryptFile data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("onEncryptFile data: " + deviceData);
            }

            @Override
            public void onDecryptInit(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onDecryptInit code = " + code);
                        Log.i(TAG, "onDecryptInit tip = " + tip);
                        Log.i(TAG, "onDecryptInit data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("onDecryptInit data: " + deviceData);
            }

            @Override
            public void onDecrypt(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onDecrypt code = " + code);
                        Log.i(TAG, "onDecrypt tip = " + tip);
                        Log.i(TAG, "onDecrypt data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("onDecrypt data: " + deviceData);
            }

            @Override
            public void onDecryptFile(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json != null) {
                        int code = json.optInt("code");
                        String tip = json.optString("tips");
                        deviceData = json.optString("data");
                        Log.i(TAG, "onDecryptFile code = " + code);
                        Log.i(TAG, "onDecryptFile tip = " + tip);
                        Log.i(TAG, "onDecryptFile data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("onDecryptFile data: " + deviceData);
            }
        };
        SkfInterface.getSkfInstance().SKF_SetCallback(Callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                SkfInterface.getSkfInstance().setDebugFlag(mLogShown);
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
