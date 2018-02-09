package com.genimous.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.genimous.peopleoutsourcing.activity.MainActivity;
import com.genimous.peopleoutsourcing.activity.MyApplication;

/**
 * Created by wudi on 18/1/18.
 *
 * 判断权限是否打开
 */

public class PermissionsUtil {


    public static final int PERMISSIONSUTIL_REQUEST_CODE = 1001;

    public static boolean isPermissionOpen(String permission){
        PackageManager pm = MyApplication.getInstance().getPackageManager();
        return (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("",MyApplication.getInstance().getPackageName()));

    }

    /**
     * 检测Android版本是否需要开启权限
     */
    public static void checkVersion(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(activity)) {
//                mCheckBox.setChecked(false);
                activity.startActivityForResult(
                                new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()))
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                                PERMISSIONSUTIL_REQUEST_CODE
                );
            }

        }
    }

    /**
     * @return
     * 判断是否开启辅助功能
     */
    private static boolean isAccessibilitySettingsOn(Activity activity) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(activity.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(activity.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    public static boolean checkAccessibility(Activity activity) {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(activity)) {
            // 引导至辅助功能设置页面

            activity.startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0);
            Toast.makeText(activity, "请先开启 \"龙猫众包\" 的试玩功能", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
