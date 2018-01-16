package com.genimous.peopleoutsourcing.utils;

import android.text.TextUtils;

import com.genimous.core.util.GsonUtil;
import com.genimous.core.util.SPUtils;
import com.genimous.peopleoutsourcing.activity.MyApplication;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;

/**
 * Created by wudi on 18/1/15.
 */

public class AppUser {
    private static UserInfoEntity sUserInfoEntity;

    public static void init(){
        String userInfo = SPUtils.getString(MyApplication.getInstance().getApplicationContext(), "userInfo", "");
        if (!TextUtils.isEmpty(userInfo)) {
            sUserInfoEntity = GsonUtil.newGson().fromJson(userInfo, UserInfoEntity.class);
        }
    }

    public static UserInfoEntity getUserInfoEntity() {
        if (sUserInfoEntity == null) {
            return GsonUtil.newGson().fromJson("{}", UserInfoEntity.class);
        }
        return sUserInfoEntity;
    }

    public static boolean isLogin() {
        return sUserInfoEntity != null;
    }


    /**
     * 登录成功时调用
     *
     * @param userInfoEntity
     */
    public static void login(UserInfoEntity userInfoEntity) {
        setUserInfo(userInfoEntity);
    }

    /**
     * 刷新用户信息时调用
     *
     * @param userInfoEntity
     */
    public static void setUserInfo(UserInfoEntity userInfoEntity) {
        AppUser.sUserInfoEntity = userInfoEntity;
        SPUtils.putString(MyApplication.getInstance().getApplicationContext(), "userInfo", GsonUtil.newGson().toJson(userInfoEntity));
    }

    /**
     * 注销时调用
     */
    public static void logout() {
        sUserInfoEntity = null;
    }
}
