package com.genimous.peopleoutsourcing.presenter;

import android.text.TextUtils;

import com.genimous.core.mvp.BasePresenter;
import com.genimous.core.util.ToastUtil;
import com.genimous.peopleoutsourcing.contract.LoginContract;

/**
 * Created by wudi on 18/1/12.
 */

public class LoginPresenter extends BasePresenter<LoginContract.loginView, LoginContract.loginModle> implements LoginContract.loginModle{
    @Override
    public void login(String userName, String pwd) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.show("请填写用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show("请填写密码");
            return;
        }

        toLogin(userName, pwd);
    }

    public void toLogin(String username, String pwd) {
        //联网
    }

}
