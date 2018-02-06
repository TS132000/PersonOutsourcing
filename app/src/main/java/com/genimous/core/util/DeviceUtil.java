package com.genimous.core.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * Created by wudi on 18/2/1.
 */

public class DeviceUtil {


    public static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;

    public static String getForegroundActivity(Context context) {
        String topActivity = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
                Log.i("aaa", "Running app number in last 60 seconds : " + stats.size());



                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                }
                Log.i("aaa", "top running app is : " + topActivity);
            }

        }
        return topActivity;
    }

    public static boolean hasPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager)
                context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

//    /**
//     * 获取系统运行的进程信息
//     *
//     * @param context
//     * @return
//     */
//    public static List<TaskInfo> getTaskInfos1(Context context) {
//        // 应用程序管理器
//        ActivityManager am = (ActivityManager) context
//                .getSystemService(context.ACTIVITY_SERVICE);
//
//        // 应用程序包管理器
//        PackageManager pm = context.getPackageManager();
//
//        // 获取正在运行的程序信息, 就是以下粗体的这句代码,获取系统运行的进程     要使用这个方法，需要加载
//　　　　　  　//　import com.jaredrummler.android.processes.ProcessManager;
//　　　　　　　//　import com.jaredrummler.android.processes.models.AndroidAppProcess;  这两个包, 这两个包附件可以下载
//
//        List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
//
//        List<TaskInfo> taskinfos = new ArrayList<TaskInfo>();
//        // 遍历运行的程序,并且获取其中的信息
//        for (AndroidAppProcess processInfo : processInfos) {
//            TaskInfo taskinfo = new TaskInfo();
//            // 应用程序的包名
//            String packname = processInfo.name;
//            taskinfo.setPackname(packname);
//            // 湖区应用程序的内存 信息
//            android.os.Debug.MemoryInfo[] memoryInfos = am
//                    .getProcessMemoryInfo(new int[] { processInfo.pid });
//            long memsize = memoryInfos[0].getTotalPrivateDirty() * 1024L;
//            taskinfo.setMemsize(memsize);
//            try {
//                // 获取应用程序信息
//                ApplicationInfo applicationInfo = pm.getApplicationInfo(
//                        packname, 0);
//                Drawable icon = applicationInfo.loadIcon(pm);
//                taskinfo.setIcon(icon);
//                String name = applicationInfo.loadLabel(pm).toString();
//                taskinfo.setName(name);
//                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                    // 用户进程
//                    taskinfo.setUserTask(true);
//                } else {
//                    // 系统进程
//                    taskinfo.setUserTask(false);
//                }
//            } catch (NameNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                // 系统内核进程 没有名称
//                taskinfo.setName(packname);
//                Drawable icon = context.getResources().getDrawable(
//                        R.drawable.default_icon);
//                taskinfo.setIcon(icon);
//            }
//            if (taskinfo != null) {
//                taskinfos.add(taskinfo);
//            }
//        }
//        return taskinfos;
//    }

}
