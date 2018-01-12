package com.genimous.core.mvp;

/**
 * Created by wudi on 18/1/12.
 */

public class BasePresenter <V extends IView, M extends IModel> implements IPresenter<V> {

    protected V mView;
    protected M mModel;


    @Override
    public void attachView(V mView) {
        this.mView = mView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public boolean isViewBind() {
        return mView != null;
    }

    public V getView(){
        return  mView;
    }

    public M getModel(){
        return mModel;
    }
}
