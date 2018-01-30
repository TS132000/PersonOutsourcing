package com.genimous.peopleoutsourcing.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.genimous.core.mvp.BasePresenter;
import com.genimous.core.util.GsonUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.net.NetAPI;
import com.genimous.peopleoutsourcing.bean.BaseUrlBean;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.contract.LoginContract;
import com.genimous.peopleoutsourcing.contract.TryGameContract;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by wudi on 18/1/12.
 */

public class TryGamePresenter extends BasePresenter<TryGameContract.tryGameView, TryGameContract.tryGameModle> implements TryGameContract.tryGameModle {



    @Override
    public void getGameList() {
        Log.i("aaa","getGameList ＝＝＝＝ ");
        //联网
        OkHttpUtils.get()
            .url(NetAPI.GET_TRYGAME_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        getView().hideLoading();
                        getView().lgetGameFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            BaseUrlBean< ArrayList<TryGameBean>> captchaInfoEntity = GsonUtil.newGson().fromJson(response,  new TypeToken<BaseUrlBean<ArrayList<TryGameBean>>>(){}.getType());
                            getView().hideLoading();
                            if (captchaInfoEntity.getCode() == NetAPI.SERVER_SUCCESS) {
                                ArrayList<TryGameBean> list = captchaInfoEntity.getMsg();
                                getView().getGameSuccess(list);
                            } else {
                                getView().lgetGameFailed("获取失败");
                            }
                        }
                    }
                });
    }



}
