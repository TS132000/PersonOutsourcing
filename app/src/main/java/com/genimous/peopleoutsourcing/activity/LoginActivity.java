package com.genimous.peopleoutsourcing.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.genimous.core.base.BaseMvpActivity;
import com.genimous.core.util.ToastUtil;
import com.genimous.core.widget.DialogMaker;
import com.genimous.peopleoutsourcing.contract.LoginContract;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;
import com.genimous.peopleoutsourcing.presenter.LoginPresenter;
import com.genimous.peopleoutsourcing.utils.AppUser;

/**
 * Created by wudi on 18/1/11.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.loginView{

    private EditText ET_phone,ET_captcha;
    private TextView TV_captcha,TV_login;


    @Override
    protected void onInitialization(Bundle bundle) {
        Log.i("aaa","onInitialization");
        ET_phone = (EditText)findViewById(R.id.EditText_loginActivity_phone);
        ET_captcha = (EditText)findViewById(R.id.EditText_loginActivity_captcha);
        TV_captcha = (TextView)findViewById(R.id.TextView_loginActivity_captcha);
        TV_login = (TextView)findViewById(R.id.TextView_loginActivity_login);

        TV_captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading("正在获取验证码，请稍后......");
                mPresenter.getVerificationCode(ET_phone.getText().toString());
            }
        });

        TV_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(ET_phone.getText().toString(),ET_captcha.getText().toString());
            }
        });

    }


    @Override
    public void loginSuccess(UserInfoEntity infoEntity) {
        AppUser.login(new UserInfoEntity());
        hideLoading();
    }

    @Override
    public void loginFailed(String msg) {
        hideLoading();
        ToastUtil.show(msg);
    }

    @Override
    public void showLoading(String loadStr) {
        DialogMaker.showProgressDialog(this,loadStr,false);
    }

    @Override
    public void hideLoading() {
        DialogMaker.dismissProgressDialog();
    }

    @Override
    public void VerificationCodeSuccess() {
//        AppUser.login(new UserInfoEntity());
        hideLoading();
    }

    @Override
    public void VerificationCodeFailed(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    protected LoginPresenter onLoadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }


}
