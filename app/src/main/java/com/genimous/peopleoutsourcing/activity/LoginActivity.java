package com.genimous.peopleoutsourcing.activity;

import com.genimous.peopleoutsourcing.base.BaseActivity;
import com.genimous.peopleoutsourcing.bean.UserBean;
import com.genimous.peopleoutsourcing.viewlistener.LoginView;

/**
 * Created by wudi on 18/1/11.
 */

public class LoginActivity extends BaseActivity implements LoginView{


    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSuccessMsg(UserBean userBean) {

    }

    @Override
    public void clearEditText() {

    }
}
