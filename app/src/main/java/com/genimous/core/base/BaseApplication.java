package com.genimous.core.base;

import java.util.logging.Logger;

import android.app.Application;
/**
 * Created by wudi on 18/1/12.
 */

public class BaseApplication extends Application {

    private static BaseApplication ourInstance;

    public static BaseApplication getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;

    }

}