/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

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

/**
 * Created by carl on 2019/11/20.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private boolean mLogShown = false;
    private Button mButtonExist = null;
    private Button mButtonEnum = null;
    private Button mButtonConnect = null;
    private Button mButtonInfo = null;
    private Button mButtonDisconnect = null;
    private TextView tvResult = null;
    private String deviceName = null;
    private String deviceData = null;
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
        mButtonExist = (Button) findViewById(R.id.btn_exist);
        mButtonExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("Skf Device: " + SkfInterface.getSkfInstance().SKF_Exist(getApplicationContext()));
            }
        });
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
                        Log.i(TAG, "onGetDevInfo data = " + deviceData);
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
                        Log.i(TAG, "onConnectDev code = " + code);
                        Log.i(TAG, "onConnectDev tip = " + tip);
                        Log.i(TAG, "onGetDevInfo data = " + deviceData);
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
                        Log.i(TAG, "onConnectDev code = " + code);
                        Log.i(TAG, "onConnectDev tip = " + tip);
                        Log.i(TAG, "onGetDevInfo data = " + deviceData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText("Device: " + deviceData);
            }
        };
        SkfInterface.getSkfInstance().SKF_SetCallback(Callback);
        SkfInterface.getSkfInstance().setDebugFlag(true);
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
