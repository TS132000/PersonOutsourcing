package com.genimous.peopleoutsourcing.activity;

import android.os.Bundle;

import com.genimous.core.base.BaseMvpActivity;
import com.genimous.peopleoutsourcing.contract.LoginContract;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;
import com.genimous.peopleoutsourcing.presenter.LoginPresenter;

/**
 * Created by wudi on 18/1/11.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.loginView{


    @Override
    public void loginSuccess(UserInfoEntity infoEntity) {

    }

    @Override
    public void loginFailed(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected LoginPresenter onLoadPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void onInitialization(Bundle bundle) {

    }
}
