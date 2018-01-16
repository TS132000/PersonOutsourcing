package com.genimous.peopleoutsourcing.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.genimous.core.mvp.BasePresenter;
import com.genimous.core.util.GsonUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.net.NetAPI;
import com.genimous.peopleoutsourcing.contract.LoginContract;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.genimous.net.NetAPI.POST_CAPTCHA_CODE;

/**
 * Created by wudi on 18/1/12.
 */

public class LoginPresenter extends BasePresenter<LoginContract.loginView, LoginContract.loginModle> implements LoginContract.loginModle{
    @Override
    public void login(String mobileNum, String captcha) {


        if (isMobile(mobileNum)) {
            ToastUtil.show("请填写正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(captcha)) {
            ToastUtil.show("请填写验证码");
            return;
        }

        toLogin(mobileNum, captcha);
    }

    public void toLogin(String mobileNum, String captcha) {
        //联网
    }

    @Override
    public void verificationCode(String mobileNum) {
        getVerificationCode(mobileNum);
    }

    // 获取签证码
    public void getVerificationCode(String mobileNum){

//        ToastUtil.show("请填写正确的手机号码");
//        if (isMobile(mobileNum)) {
//            ToastUtil.show("请填写正确的手机号码");
//            return;
//        }

        //联网
        OkHttpUtils.postString().content(GsonUtil.newGson().toJson(new CaptchaEntity("login",mobileNum)))
                .url(NetAPI.POST_CAPTCHA_CODE).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("aaa",response);
                    }


                });


    }


    /**
     * 验证手机格式
     */
    public boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3578]\\d{9}"; //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }


    public static class CaptchaEntity{
        private String type ;
        private String phone;

        public CaptchaEntity(String type, String phone){
            this.type = type;
            this.phone = phone;
        }

    }

    public static class LoginContentEntity{

        private String type ;
        private String phone;

        public LoginContentEntity(String type,String phone){

        }
    }


}
