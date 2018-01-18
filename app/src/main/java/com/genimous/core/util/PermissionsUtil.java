package com.genimous.core.util;

import android.content.pm.PackageManager;

import com.genimous.peopleoutsourcing.activity.MyApplication;

/**
 * Created by wudi on 18/1/18.
 *
 * 判断权限是否打开
 */

public class PermissionsUtil {

    public static boolean isPermissionOpen(String permission){
        PackageManager pm = MyApplication.getInstance().getPackageManager();
        return (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("",MyApplication.getInstance().getPackageName()));

    }

}
