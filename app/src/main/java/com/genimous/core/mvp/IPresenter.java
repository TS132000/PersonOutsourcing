package com.genimous.core.mvp;

/**
 * Created by wudi on 18/1/12.
 */

public interface IPresenter <V extends IView>{

    void attachView(V mView);

    void onStart();

    void detachView();

    boolean isViewBind();

}
