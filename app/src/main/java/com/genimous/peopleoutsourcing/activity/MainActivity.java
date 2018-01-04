package com.genimous.peopleoutsourcing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.genimous.peopleoutsourcing.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    Button btn;
    TextView tv ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



    }

//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void onEventMainThread(FirstEvent event){
//        String msg = "onEventMainThread:::"+event.getMsg();
//        Log.d("aaa","msg === "+msg);
//        tv.setText(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//
//
//    }

//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void onEvent(SecondEvent event){
//        String msg = "onEvent:::"+event.getmMsg();
//        Log.d("aaa","msg === "+msg);
//    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }
}
