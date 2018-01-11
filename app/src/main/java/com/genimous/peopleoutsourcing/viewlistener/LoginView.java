package com.genimous.peopleoutsourcing.viewlistener;

import com.genimous.peopleoutsourcing.bean.UserBean;

/**
 * Created by wudi on 18/1/11.
 */

public interface LoginView {
    //得到用户填写的信息
    String getUserName();
    String getPassword();

//    显示和隐藏登录的ProgressBar
    void showLoading();
    void hideLoading();
//    登录成功或失败后，返回信息的方法
    void showSuccessMsg(UserBean userBean);
//    清除数据
    void clearEditText();

}
