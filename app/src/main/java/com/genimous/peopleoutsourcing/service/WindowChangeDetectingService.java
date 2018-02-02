package com.genimous.peopleoutsourcing.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by wudi on 18/2/2.
 */

public class WindowChangeDetectingService extends AccessibilityService{


    private WindowChangeDetectingService mTopWindowManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mTopWindowManager == null) {
            Log.d("aaa","onStartCommand");
        }

        return super.onStartCommand(intent, flags, startId);

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
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("aaa", "onAccessibilityEvent: " + event.getPackageName());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.i("aaa"," event.getPackageName().toString( === "+ event.getPackageName().toString());
            ComponentName componentName = new ComponentName(
                    event.getPackageName().toString(),
                    event.getClassName().toString()
            );

            ActivityInfo activityInfo = tryGetActivity(componentName);
            boolean isActivity = activityInfo != null;
            if (isActivity)
                Log.i("CurrentActivity", componentName.flattenToShortString());
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
}
