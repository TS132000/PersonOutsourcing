package com.genimous.peopleoutsourcing.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.genimous.core.mvp.BasePresenter;
import com.genimous.core.util.GsonUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.net.NetAPI;
import com.genimous.peopleoutsourcing.contract.LoginContract;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.genimous.net.NetAPI.POST_CAPTCHA_CODE;

/**
 * Created by wudi on 18/1/12.
 */

public class LoginPresenter extends BasePresenter<LoginContract.loginView, LoginContract.loginModle> implements LoginContract.loginModle {
    @Override
    public void login(String mobileNum, String captcha) {


        if (!isMobile(mobileNum)) {
            ToastUtil.show("请填写正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(captcha)) {
            ToastUtil.show("请填写验证码");
            return;
        }

        toLogin(mobileNum, captcha);
    }

    public void toLogin(final String mobileNum, final String captcha) {
        //联网

        //联网
        OkHttpUtils
                .post()
//                    .addParams("type", "login")
                .url(NetAPI.POST_CAPTCHA_CODE)
                .addParams("mobile",  mobileNum)
                .addParams("code",  captcha)
//                    .postString()
//                    .content(GsonUtil.newGson().toJson(new CaptchaEntity("login", mobileNum)))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
//                            Log.i("aaa", ""+ (e.printStackTrace()));
                        getView().hideLoading();
                        getView().loginFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        getView().hideLoading();
                        if (!TextUtils.isEmpty(response)) {
                            CaptchaInfoEntity captchaInfoEntity = GsonUtil.newGson().fromJson(response, CaptchaInfoEntity.class);
                            if (captchaInfoEntity.getCode() == NetAPI.SERVER_SUCCESS) {

                                if(captchaInfoEntity.getStatus().equals(NetAPI.HTTP_STATUS_SUCCESS)){
//                                    ToastUtil.show("登录成功");
//                                    getView().loginSuccess();
                                     getUserInfo(mobileNum);

                                } else {
                                    getView().loginFailed("验证失败");
                                    getView().hideLoading();
                                }

                            } else {
                                getView().hideLoading();
                                getView().loginFailed(captchaInfoEntity.getMsg());
                            }
                        }
                    }


                });

    }

    // 获取用户信息
    public void getUserInfo(final String mobileNum){

        //联网
        OkHttpUtils
                .get()
//                    .addParams("type", "login")
                .url(NetAPI.GET_USER_INFO+"/"+mobileNum)
//                .addParams("mobile",  mobileNum)
//                    .postString()
//                    .content(GsonUtil.newGson().toJson(new CaptchaEntity("login", mobileNum)))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("aaa", "id ==== " + id);
                        getView().hideLoading();
                        getView().loginFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        getView().hideLoading();
                        Log.i("aaa", "response = " + response);
                        if (!TextUtils.isEmpty(response)) {
                            UserInfoEntity userInfoEntity = GsonUtil.newGson().fromJson(response, UserInfoEntity.class);

                            if (userInfoEntity != null && userInfoEntity.getId() != null){
                                getView().loginSuccess(userInfoEntity);
                            } else {
                                getView().loginFailed("用户信息获取失败");
                            }
                        } else {
                            getView().loginFailed("用户信息获取失败");
                        }
                    }
                });
    }

    @Override
    public void verificationCode(String mobileNum) {
        getVerificationCode(mobileNum);
    }

    // 获取签证码
    public void getVerificationCode(String mobileNum) {

        if (isMobile(mobileNum)) {
            //联网
            OkHttpUtils
                    .post()
//                    .addParams("type", "login")
                    .url(NetAPI.POST_CAPTCHA_CODE)
                    .addParams("mobile",  mobileNum)
//                    .postString()
//                    .content(GsonUtil.newGson().toJson(new CaptchaEntity("login", mobileNum)))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("aaa", "id ==== " + id);
                            e.printStackTrace();
//                            Log.i("aaa", ""+ (e.printStackTrace()));
                            getView().hideLoading();
                            getView().VerificationCodeFailed(e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            getView().hideLoading();
                            Log.i("aaa", "response = " + response);
                            if (!TextUtils.isEmpty(response)) {
                                CaptchaInfoEntity captchaInfoEntity = GsonUtil.newGson().fromJson(response, CaptchaInfoEntity.class);
                                if (captchaInfoEntity.getCode() == NetAPI.SERVER_SUCCESS) {
                                    getView().hideLoading();
                                    ToastUtil.show("验证码已发送");
                                    getView().VerificationCodeSuccess();
                                } else {
                                    getView().hideLoading();
                                    getView().VerificationCodeFailed(captchaInfoEntity.getMsg());
                                }
                            }
                        }
                    });

        } else {
            ToastUtil.show("请填写正确的手机号码");
        }

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


    public static class CaptchaInfoEntity{
//        01-17 14:52:33.350 6043-6043/com.genimous.peopleoutsourcing.activity I/aaa: {"code":0,"message":"发送成功","data":""}
        private String msg;

        private String status;

        private int code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public int  getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }



    }


    public static class LoginContentEntity {

        private String type;
        private String phone;

        public LoginContentEntity(String type, String phone) {

        }
    }


}
