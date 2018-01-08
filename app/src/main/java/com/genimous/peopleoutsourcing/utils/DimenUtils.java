package com.genimous.peopleoutsourcing.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by wudi on 18/1/8.
 */

/**
 * 尺寸管理类
 */
public class DimenUtils {
    public static float px2dp(Context context, float px) {
        return (float) px / context.getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }

    public static int dp2px(Context context, int dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5);
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5);
    }

    public static int getDPI(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.densityDpi;
    }

    public static int getDeviceWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getStatusHeightPx(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
