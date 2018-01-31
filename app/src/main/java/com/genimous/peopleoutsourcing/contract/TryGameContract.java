package com.genimous.peopleoutsourcing.contract;

import com.genimous.core.mvp.IModel;
import com.genimous.core.mvp.IView;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.entity.UserInfoEntity;

import java.util.ArrayList;

/**
 * Created by wudi on 18/1/12.
 */

public interface TryGameContract {

     interface tryGameModle extends IModel{

         void getGameList();

    }

     interface  tryGameView extends IView{

        void getGameSuccess(ArrayList<TryGameBean> gameList);

        void getGameFailed(String msg);

        void showLoading(String loadingStr);

        void hideLoading();

    }
}
