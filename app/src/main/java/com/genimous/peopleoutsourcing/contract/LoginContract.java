package com.genimous.peopleoutsourcing.contract;

import com.genimous.core.mvp.IModel;
import com.genimous.core.mvp.IView;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;

/**
 * Created by wudi on 18/1/12.
 */

public interface LoginContract {

     interface loginModle extends IModel{
        void login (String mobileNum, String captcha);

        void verificationCode (String mobileNum);
    }

     interface  loginView extends IView{

        void loginSuccess(UserInfoEntity infoEntity);

        void loginFailed(String msg);

        void showLoading(String loadingStr);

        void hideLoading();

        void VerificationCodeSuccess();

        void VerificationCodeFailed(String msg);

    }
}
