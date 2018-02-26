package com.genimous.peopleoutsourcing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.genimous.core.util.DeviceUtil;
import com.genimous.core.util.GsonUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.net.NetAPI;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.base.BaseActivity;
import com.genimous.peopleoutsourcing.bean.BaseUrlBean;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.presenter.LoginPresenter;
import com.genimous.peopleoutsourcing.utils.AppUser;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;


public class SplashActivity extends BaseActivity {

    Button btn;
    TextView tv ;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        DeviceUtil.getTelephonyManager(SplashActivity.this).getDeviceId();
        TelephonyManager tele =  DeviceUtil.getTelephonyManager(SplashActivity.this);

//        Log.i("aaa","getPhoneBrand === "+DeviceUtil.getPhoneBrand());
//        Log.i("aaa","getVerCode === "+DeviceUtil.getVerCode(SplashActivity.this));
//        Log.i("aaa","getAppName === "+DeviceUtil.getAppName(SplashActivity.this));
//        Log.i("aaa","getHostIP === "+DeviceUtil.getHostIP());
        postDevInfo(Build.BOARD, tele.getSimSerialNumber(), tele.getDeviceSoftwareVersion(), tele.getDeviceId(),
                DeviceUtil.getAndroidId(SplashActivity.this),Build.MANUFACTURER, DeviceUtil.getHostIP());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },3000);
    }


    private void startMainActivity(){

        if (AppUser.isLogin()) {
            Intent in = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        } else {

            Intent in = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void postDevInfo(String brand,String serial_number,String system_version,String device_id,
                            String android_id,String hardware,String ip_address) {
        //联网
        OkHttpUtils
                .post()
//                    .addParams("type", "login")
                .url(NetAPI.POST_DEVINFO)

                .addParams("user_id",  AppUser.getUserInfoEntity().getId())
                .addParams("brand",  brand)
                .addParams("serial_number",  serial_number)
                .addParams("system_version",  system_version)
                .addParams("device_id",  device_id)
                .addParams("android_id",  android_id)
                .addParams("hardware",  hardware)
                .addParams("ip_address",  ip_address)
//                    .postString()
//                    .content(GsonUtil.newGson().toJson(new CaptchaEntity("login", mobileNum)))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("aaa", "SplashActivity id ==== " + id);
                        e.printStackTrace();
//                            Log.i("aaa", ""+ (e.printStackTrace()));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("aaa", "SplashActivity response = " + response);
                        if (!TextUtils.isEmpty(response)) {
                            LoginPresenter.CaptchaInfoEntity captchaInfoEntity = GsonUtil.newGson().fromJson(response, LoginPresenter.CaptchaInfoEntity.class);
                            if (captchaInfoEntity.getCode() == NetAPI.SERVER_SUCCESS) {

                            } else {

                            }
                        }
                    }
                });


}


}
