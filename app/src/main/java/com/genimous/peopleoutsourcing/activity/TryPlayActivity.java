package com.genimous.peopleoutsourcing.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.genimous.core.base.BaseMvpActivity;
import com.genimous.core.mvp.IPresenter;
import com.genimous.core.util.PermissionsUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.core.widget.DialogMaker;
import com.genimous.peopleoutsourcing.adapter.PermissionViewHolder;
import com.genimous.peopleoutsourcing.adapter.TryGameViewHolder;
import com.genimous.peopleoutsourcing.bean.PermissionBean;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.contract.TryGameContract;
import com.genimous.peopleoutsourcing.presenter.TryGamePresenter;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by wudi on 18/1/18.
 */

public class TryPlayActivity extends BaseMvpActivity<TryGamePresenter> implements TryGameContract.tryGameView {

    TryGameViewHolder tryGameViewHolder;


    RecyclerView recyclerView;
    ImageView imageBack;
    TextView TextView_baseFragmentTitle;
    /**权限名称*/
    String [] permissionsStrArray;

    /**未开启权限名称*/
    String [] permissionsFailStrArray;

    /**为开启权限提示*/
    String [] permissionsFailDescStrArray;

    ArrayList<TryGameBean> tryGameList = new ArrayList<TryGameBean>(8);

    @Override
    protected TryGamePresenter onLoadPresenter() {
        return new TryGamePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_tryplay;
    }

    @Override
    protected void onInitialization(Bundle bundle) {

        imageBack = (ImageView)findViewById(R.id.ImageView_baseActivity_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView_baseFragmentTitle = (TextView) findViewById(R.id.TextView_baseFragmentTitle);
        TextView_baseFragmentTitle.setText("试玩专区");

        //初始化RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_tryplayActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRefresh();

    }

    private void onRefresh(){
        this.mPresenter.getGameList();
    }

    private void initData(){

        //初始化适配器
        tryGameViewHolder = new TryGameViewHolder(tryGameList, TryPlayActivity.this);
        recyclerView.setAdapter(tryGameViewHolder);
//        PermissionsUtil.isPermissionOpen("android.permission.CAMERA");
//        Log.i("aaa",    "PermissionsUtil.isPermissionOpen(\"android.permission.CAMERA\"); === "+PermissionsUtil.isPermissionOpen("android.permission.CAMERA"));
    }

    @Override
    public void getGameSuccess(ArrayList<TryGameBean> gameList) {
        hideLoading();
        tryGameList = gameList;
        initData();
    }

    @Override
    public void lgetGameFailed(String msg) {
        hideLoading();
        ToastUtil.show(msg);
    }

    @Override
    public void showLoading(String loadingStr) {

        DialogMaker.showProgressDialog(this,loadingStr,false);
    }

    @Override
    public void hideLoading() {
        DialogMaker.dismissProgressDialog();
    }
}
