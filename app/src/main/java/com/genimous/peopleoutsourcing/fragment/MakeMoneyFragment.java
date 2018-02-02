package com.genimous.peopleoutsourcing.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.view.TextTitleItemView;
import com.genimous.peopleoutsourcing.widget.LoadStatusView;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Created by wudi on 18/1/4.
 */

public class MakeMoneyFragment extends BaseFragment {

    PageBottomTabLayout tabLayout;
    ViewPager viewPager;

    private NavigationController navigationController;

    private BaseFragment[] mFragments = new BaseFragment[1];
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tabLayout = (PageBottomTabLayout)view.findViewById(R.id.PageBottomTabLayout_makeMoneyFragment);
        viewPager = (ViewPager)view.findViewById(R.id.ViewPager_makeMoneyFragment );
        mFragments[0] = SubFastMoney.newInstance(new Bundle());
//        mFragments[1] = SubDataCollectFragment.newInstance(new Bundle());
//        mFragments[2] = SubFastMoney.newInstance(new Bundle());
//        mFragments[3] = SubDataCollectFragment.newInstance(new Bundle());
//        mFragments[4] = SubFastMoney.newInstance(new Bundle());
//        mFragments[5] = SubDataCollectFragment.newInstance(new Bundle());
//        mFragments[6] = SubFastMoney.newInstance(new Bundle());
//        mFragments[7] = SubDataCollectFragment.newInstance(new Bundle());

        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                navigationController.setSelect(viewPager.getCurrentItem());
            }
        });

        // 初始化底部Tab
        navigationController = tabLayout.custom()
                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"快速赚钱"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"数据采集"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"快速赚钱"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"数据采集"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"快速赚钱"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"数据采集"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"快速赚钱"))
//                .addItem(buildTabItem(R.drawable.shape_item_text_default, R.drawable.shape_item_text_select,"数据采集"))
                .build();

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                viewPager.setCurrentItem(index);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    private BaseTabItem buildTabItem(int drawable, int checkedDrawable, String title) {
        TextTitleItemView textTitleItemView = new TextTitleItemView(getActivity());
        textTitleItemView.initialize(drawable, checkedDrawable, title);
        return textTitleItemView;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_makemoney;
    }

    public static MakeMoneyFragment newInstance(Bundle bundle) {
        MakeMoneyFragment fragment = new MakeMoneyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
