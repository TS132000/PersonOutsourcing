package com.genimous.peopleoutsourcing.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.genimous.core.download.DownloadManager;
import com.genimous.core.download.DownloadTask;
import com.genimous.core.util.DeviceUtil;
import com.genimous.peopleoutsourcing.activity.MyApplication;

import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by wudi on 18/2/1.
 */

public class OpenAppService extends IntentService {

    private static final String COM_DUCK_HUSBAND_UNCHECKED = "com.duck.husband.UNCHECKED";
    private Context context;
    private String unCheckedPackageName;
//    private UnCheckedReceiver receiver;
    DownloadManager downloadManager;

    List<DownloadTask> downloadTaskList;
    public OpenAppService() {

        super("OpenAppService");
        downloadManager = DownloadManager.getInstance(MyApplication.getInstance());

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        while (true) {
//            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(99);
//            String packageName = runningTasks.get(0).topActivity
//                    .getPackageName();
            String packageName = getLollipopRecentTask(context);
//            ActivityManager mActivityManager =(ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
//            if(Build.VERSION.SDK_INT > 20){
//                packageName = mActivityManager.getRunningAppProcesses().get(0).processName;
//                Log.i("aaa","packageName11111111111 ==== "+packageName);
//            }else{
//                packageName =  mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
//                Log.i("aaa","packageName ==== "+packageName);
//            }

//            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//            ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;


//            String packageName = DeviceUtil.getForegroundActivity(context);

            // 获取最近打开的App包名
//            boolean b = watchDogDao.query(packageName);
            downloadTaskList = downloadManager.loadAllTask();
            Log.i("aaa","packageName ==== "+packageName);
            //是否弹出到试玩界面
            boolean isTryPlay = false;
            for (int i = 0; i < downloadTaskList.size(); i++){
                Log.i("aaa","downloadTaskList.get(i).getPackageName() ==== "+downloadTaskList.get(i).getPackageName());

                if (downloadTaskList.get(i).getPackageName().equals(packageName)){
                    isTryPlay = true;
                    break;
                }
            }
            if (isTryPlay) {
                // 弹出试玩
                Log.i("aaa","弹出试玩 ==== ");
//                    Intent intent2 = new Intent(context, LockActivity.class);
//                    intent2.putExtra("packageName", packageName);// TODO：这一行不加，就没有办法去临时取消保护了！！！
//                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent2);
                }
            SystemClock.sleep(300);
        }
    }

    public static String getCurrentPkgName(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String pkgName = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception e) { e.printStackTrace(); }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo app : appList) {
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                Integer state = null;
                try {
                    state = field.getInt( app );
                } catch (Exception e) { e.printStackTrace(); }
                if (state != null && state == START_TASK_TO_FRONT) {
                    currentInfo = app;
                    break;
                }
            }
        }
        if (currentInfo != null) {
            pkgName = currentInfo.processName;
        }
        return pkgName;
    }

    public static String getLollipopRecentTask(Context context) {
        String currentApp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            @SuppressLint("WrongConstant") UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        return currentApp;
    }

}
