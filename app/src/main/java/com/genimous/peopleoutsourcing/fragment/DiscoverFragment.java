package com.genimous.peopleoutsourcing.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.genimous.peopleoutsourcing.activity.R;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.view.IconTitleItemView;
import com.genimous.peopleoutsourcing.view.TextTitleItemView;
import com.genimous.peopleoutsourcing.widget.LoadStatusView;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Created by wudi on 18/1/4.
 */

public class DiscoverFragment extends BaseFragment {
    LoadStatusView mLoadStatusView;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mLoadStatusView = (LoadStatusView)view.findViewById(R.id.LoadStatusView_Discover_status);
        mLoadStatusView.setViewStatus(LoadStatusView.LoadStatus.NO_NET);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }


    public static DiscoverFragment newInstance(Bundle bundle) {
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
