package com.genimous.peopleoutsourcing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.genimous.peopleoutsourcing.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class SplashActivity extends BaseActivity {

    Button btn;
    TextView tv ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },3000);
    }


    private void startMainActivity(){
        Intent in = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
