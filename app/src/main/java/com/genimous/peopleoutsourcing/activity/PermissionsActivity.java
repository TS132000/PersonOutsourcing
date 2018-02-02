package com.genimous.peopleoutsourcing.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.genimous.core.base.BaseMvpActivity;
import com.genimous.core.mvp.IPresenter;
import com.genimous.core.util.PermissionsUtil;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.adapter.PermissionViewHolder;
import com.genimous.peopleoutsourcing.bean.PermissionBean;

import java.util.ArrayList;


/**
 * Created by wudi on 18/1/18.
 */

public class PermissionsActivity extends BaseMvpActivity {

    PermissionViewHolder permissionViewHolder;


    RecyclerView recyclerView;
    TextView tvBack;
    /**权限名称*/
    String [] permissionsStrArray;

    /**未开启权限名称*/
    String [] permissionsFailStrArray;

    /**为开启权限提示*/
    String [] permissionsFailDescStrArray;

    ArrayList<PermissionBean> permissionList = new ArrayList<PermissionBean>(8);

    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_permission;
    }

    @Override
    protected void onInitialization(Bundle bundle) {
        initData();
        tvBack = (TextView)findViewById(R.id.textView_permissionActivity);
        //初始化RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_permissionActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //初始化适配器
        permissionViewHolder = new PermissionViewHolder(permissionList, PermissionsActivity.this);
        recyclerView.setAdapter(permissionViewHolder);


    }

    private void initData(){

        permissionsStrArray = getResources().getStringArray(R.array.permissions_string);
        permissionsFailStrArray = getResources().getStringArray(R.array.permissions_fail_string);
        permissionsFailDescStrArray = getResources().getStringArray(R.array.permissions_fail_desc_string);
        String [] permissions_action_string = getResources().getStringArray(R.array.permissions_action_string);

        for(int i = 0; i < permissions_action_string.length; i++){
            PermissionBean bean = new PermissionBean();

            boolean isOpen = PermissionsUtil.isPermissionOpen(permissions_action_string[i]);
            Log.i("aaa","isOpen === "+isOpen);
            bean.setOpen(isOpen);
            bean.setPrrmissionStr(permissionsStrArray[i]);
            bean.setPermissionFailStr(permissionsFailStrArray[i]);
            bean.setPermissionFailDesc(permissionsFailDescStrArray[i]);
            permissionList.add(bean);
        }

//        PermissionsUtil.isPermissionOpen("android.permission.CAMERA");
//        Log.i("aaa",    "PermissionsUtil.isPermissionOpen(\"android.permission.CAMERA\"); === "+PermissionsUtil.isPermissionOpen("android.permission.CAMERA"));
    }

}
