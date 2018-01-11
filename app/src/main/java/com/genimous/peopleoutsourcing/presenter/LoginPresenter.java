package com.genimous.peopleoutsourcing.presenter;

import com.genimous.peopleoutsourcing.bean.UserBean;
import com.genimous.peopleoutsourcing.model.LoginModel;
import com.genimous.peopleoutsourcing.model.LoginModelImpl;
import com.genimous.peopleoutsourcing.viewlistener.LoginView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by wudi on 18/1/11.
 */

public class LoginPresenter  {

    private LoginView loginView;
    private LoginModel loginModel;
    private Handler mHandler;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
        mHandler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };

    }

    public void login(){
        loginView.showLoading();
        loginModel.login(loginView.getUserName(), loginView.getPassword(), new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(UserBean userBean) {
                //登录成功

            }

            @Override
            public void loginFailed(String s) {
                //登录失败

            }
        });
    }

    public void clear(){
        loginView.clearEditText();
    }

}
