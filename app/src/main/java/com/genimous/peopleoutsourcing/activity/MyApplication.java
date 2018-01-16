package com.genimous.peopleoutsourcing.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.genimous.core.base.BaseApplication;
import com.genimous.peopleoutsourcing.utils.AppUser;

/**
 * Created by wudi on 18/1/12.
 *
 * 参考 https://github.com/xiaohaibin/DMGameApp.git
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Toast
        ToastManager.instance.init(this);
        //初始化用户数据
        AppUser.init();
    }

    public enum ToastManager {

        instance;

        private TextView contentTextView;
        private Toast toast;

        public void init(Context context) {
            contentTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.view_toast, null);
            toast = new Toast(context);
            toast.setView(contentTextView);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }

        public void show(CharSequence charSequence, int duration) {
            if (!TextUtils.isEmpty(charSequence)) {
                contentTextView.setText(charSequence);
                toast.setDuration(duration);
                toast.show();
            }
        }
    }
}
