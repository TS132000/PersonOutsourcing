package com.genimous.peopleoutsourcing.fragment;

import android.os.Bundle;
import android.view.View;

import com.genimous.peopleoutsourcing.base.BaseFragment;

/**
 * Created by wudi on 18/1/4.
 */

public class MakeMoneyFragment extends BaseFragment {

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public static MakeMoneyFragment newInstance(Bundle bundle) {
        MakeMoneyFragment fragment = new MakeMoneyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
