package com.genimous.peopleoutsourcing.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.genimous.peopleoutsourcing.activity.PermissionsActivity;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.activity.TryPlayActivity;
import com.genimous.peopleoutsourcing.adapter.FastMoneyViewHolder;
import com.genimous.peopleoutsourcing.adapter.PermissionViewHolder;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.bean.FastMoneyBean;
import com.genimous.peopleoutsourcing.bean.PermissionBean;
import com.genimous.peopleoutsourcing.widget.LoadStatusView;

import java.util.ArrayList;

/**
 * Created by wudi on 18/1/9.
 */

//快速赚钱
public class SubFastMoney extends BaseFragment {
    LoadStatusView mLoadStatusView;

    ArrayList<FastMoneyBean> fbeanList;

    FastMoneyViewHolder fastMoneyViewHolder;

    RecyclerView recyclerView;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        mLoadStatusView = (LoadStatusView)view.findViewById(R.id.LoadStatusView_Discover_status);
//        mLoadStatusView.setViewStatus(LoadStatusView.LoadStatus.NO_NET);

        fbeanList = new ArrayList<FastMoneyBean>(1);

        FastMoneyBean fbean = new FastMoneyBean();
        fbean.setFastMoneyInfo("下载打开体验即可，每次奖励0.1元,多做多得长期有效");
        fbean.setFastMoneyTitle("试玩应用");
        fbeanList.add(fbean);

        //初始化RecyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_fastMoneyFragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //初始化适配器
        fastMoneyViewHolder = new FastMoneyViewHolder(fbeanList, SubFastMoney.this.getActivity());
        recyclerView.setAdapter(fastMoneyViewHolder);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, TryPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_fastmoney;
    }

    public static SubFastMoney newInstance(Bundle bundle) {
        SubFastMoney fragment = new SubFastMoney();
        fragment.setArguments(bundle);
        return fragment;
    }
}
