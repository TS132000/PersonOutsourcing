package com.genimous.peopleoutsourcing.service;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.genimous.core.download.DownloadManager;
import com.genimous.core.download.DownloadTask;
import com.genimous.core.util.DeviceUtil;
import com.genimous.core.util.GsonUtil;
import com.genimous.net.NetAPI;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.activity.MainActivity;
import com.genimous.peopleoutsourcing.activity.MyApplication;
import com.genimous.peopleoutsourcing.activity.TryPlayActivity;
import com.genimous.peopleoutsourcing.presenter.LoginPresenter;
import com.genimous.peopleoutsourcing.utils.AppUser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by wudi on 18/2/2.
 */

public class WindowChangeDetectingService extends AccessibilityService{


    private CountDownTimer countDownTimer;

    List<DownloadTask> downloadTaskList;
    DownloadManager downloadManager;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager mNotificationManager;
    int notifyId ;


    @Override
    public void onCreate() {

        Log.i("aaa","oncreate");
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        downloadManager = DownloadManager.getInstance(MyApplication.getInstance());
        new NotificationCompat.Builder(this)
                .setContentTitle("试玩时间")
                .setContentText("60秒")
                .setSmallIcon(android.R.drawable.stat_sys_download);
        super.onCreate();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        //Configure these here for compatibility with API 13 and below.
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= 16)
            //Just in case this helps
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.d("aaa", "onAccessibilityEvent: " + event.getPackageName());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName().toString();
            Log.i("aaa"," event.getPackageName().toString( === "+ packageName);
            downloadTaskList = downloadManager.loadAllTask();
            //是否弹出到试玩界面
            boolean isTryPlay = false;
            String appId = null;
            String appName = null;

            for (int i = 0; i < downloadTaskList.size(); i++){
                appId = downloadTaskList.get(i).getAppId();
                appName = downloadTaskList.get(i).getAppName();

                if (downloadTaskList.get(i).getPackageName().equals(packageName)){
                    isTryPlay = true;
                    notifyId = packageName.hashCode();
                    break;
                }
            }
            if (isTryPlay) {
                // 弹出试玩

                Log.i("aaa","弹出试玩 ==== ");
                if (!isFinish)
                    return;
//                setNotification("试玩应用","60秒", notifyId);
                start(appId, appName);
            }
//            ComponentName componentName = new ComponentName(
//                    event.getPackageName().toString(),
//                    event.getClassName().toString()
//            );
//            ActivityInfo activityInfo = tryGetActivity(componentName);
//            boolean isActivity = activityInfo != null;
//            if (isActivity)
//                Log.i("CurrentActivity", componentName.flattenToShortString());
        }

    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
    @Override
    public void onInterrupt() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setNotification(String title, String content, int notifyId) {
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder mNotifyBuilder = new Notification.Builder(this);
         mNotifyBuilder.setContentTitle(title)
                .setContentText(content)
                .setFullScreenIntent(hangPendingIntent, true)
                .setSmallIcon(android.R.drawable.stat_sys_download);

        mNotificationManager.notify(
                notifyId,
                mNotifyBuilder.build());

//        notificationHashMap.put(notifyId, mNotifyBuilder);
    }

    int countDownSeconds = 60;
    boolean isFinish = true;
    private void start(final String appId, final String appName){
        isFinish = false;
        countDownTimer = new CountDownTimer(countDownSeconds * 1000, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTick(long millisUntilFinished) {

                setNotification("试玩应用",millisUntilFinished / 1000 + "秒", notifyId);
            }

            @Override
            public void onFinish() {
                isFinish = true;
                mNotificationManager.cancel(notifyId);
                downLoadRecord(appId, appName, NetAPI.RECORD_STATUS_FINISHED);

            }
        };
        countDownTimer.start();
    }
    @SuppressLint("MissingPermission")
    public void downLoadRecord(final String appId, String appName, String status) {
        //联网


        Log.i("aaa","userid === "+ AppUser.getUserInfoEntity().getId()+
        "app_id"+appId+
        "device_id"+DeviceUtil.getTelephonyManager(WindowChangeDetectingService.this).getDeviceId()+
        "name"+appName+
        "status"+status);

        OkHttpUtils
                .post()
                .url(NetAPI.GET_RECORD_LIST)
                .addParams("user_id", AppUser.getUserInfoEntity().getId())
                .addParams("app_id", appId)
                .addParams("device_id", DeviceUtil.getTelephonyManager(WindowChangeDetectingService.this).getDeviceId())
                .addParams("name", appName)
                .addParams("status", status)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
//                            Log.i("aaa", ""+ (e.printStackTrace()));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("aaa", "response ＝＝＝＝＝ "+ response);
                        if (!TextUtils.isEmpty(response)) {

                        }
                    }


                });
    }

}
