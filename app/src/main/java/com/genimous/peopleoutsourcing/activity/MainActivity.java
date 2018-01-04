package com.genimous.peopleoutsourcing.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.genimous.peopleoutsourcing.base.BaseActivity;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.fragment.MakeMoneyFragment;

import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;


public class MainActivity extends BaseActivity {

    PageBottomTabLayout tabLayout;
    ViewPager viewPager;

    private BaseFragment[] mFragments = new BaseFragment[3];
//    private MakeMoneyFragment make


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment);

        tabLayout = (PageBottomTabLayout)findViewById(R.id.PageBottomTabLayout_mainActivity);
        viewPager = (ViewPager)findViewById(R.id.ViewPager_mainActivity);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
