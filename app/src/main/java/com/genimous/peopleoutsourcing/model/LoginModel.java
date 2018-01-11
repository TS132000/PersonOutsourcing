package com.genimous.peopleoutsourcing.model;

import com.genimous.peopleoutsourcing.bean.UserBean;

/**
 * Created by wudi on 18/1/11.
 *
 * 定义接口类
 */

public interface LoginModel {

    interface  OnLoginListener{
        void loginSuccess(UserBean userBean);

        void loginFailed(String s);
    }

 void login(String userNmae, String password, OnLoginListener onLoginListener);

}
