package com.genimous.core.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genimous.core.mvp.IPresenter;
import com.genimous.core.mvp.IView;

/**
 * Created by wudi on 18/1/12.
 */

public abstract class BaseMvpFragment <P extends IPresenter> extends Fragment implements IView {

    /**
     * 是否可见
     */
    protected boolean isViable = false;

    /**
     * 标志位，标志Fragment已经初始化完成
     */
    protected boolean isPrepared = false;

    /**
     * 标记已加载完成，保证懒加载只能加载一次
     */
    protected boolean hasLoaded = false;

    protected P mPresenter;
    protected View rootView;
    protected Context mContext = null;

    protected abstract P onLoadPresenter();

    protected abstract int getLayoutResource();

    protected abstract void onInitView(Bundle savedInstanceState);

    protected void onInitPresenter() {
        mPresenter = onLoadPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            rootView = inflater.inflate(getLayoutResource(), null);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        this.onInitView(savedInstanceState);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitPresenter();
        if(!isPrepared && getUserVisibleHint()){
            onFragmentVisibleChange(true);
        }


    }


    @Override
    public void onStart() {
        if (mPresenter != null && !mPresenter.isViewBind()) {
            mPresenter.onStart();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        hasLoaded = false;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        isPrepared = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isViable = true;
            return;
        }

        if (isViable) {
            onFragmentVisibleChange(false);
            isViable = false;
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (hasLoaded) {
            return;
        }
        lazyLoad();
        hasLoaded = true;
    }

    /**
     * 数据懒加载
     */
    protected void lazyLoad() {

    }

    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     *
     * @param isVisible
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    public P getPresenter() {
        return mPresenter;
    }

}
