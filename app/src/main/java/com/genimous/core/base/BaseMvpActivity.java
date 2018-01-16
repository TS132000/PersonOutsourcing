package com.genimous.core.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.genimous.core.mvp.IPresenter;
import com.genimous.core.mvp.IView;

/**
 * Created by wudi on 18/1/12.
 */

public abstract class BaseMvpActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;

    protected abstract P onLoadPresenter();

    protected abstract int getLayoutResource();

    protected abstract void onInitialization(Bundle bundle);

    protected  void onInitPresenter(){
        mPresenter = onLoadPresenter();
        if (mPresenter!=null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); Log.i("aaa","onCreateonCreateonCreate");

        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        }
        onInitialization(savedInstanceState);
        onInitPresenter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null && mPresenter.isViewBind()){
            mPresenter.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    public P getPresenter() {
        return mPresenter;
    }
}
