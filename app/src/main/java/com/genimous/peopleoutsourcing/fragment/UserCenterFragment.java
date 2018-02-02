package com.genimous.peopleoutsourcing.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.genimous.peopleoutsourcing.activity.PermissionsActivity;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.widget.LoadStatusView;

import org.w3c.dom.Text;

/**
 * Created by wudi on 18/1/4.
 */

public class UserCenterFragment extends BaseFragment {
//
    TextView item_permissions_tv;
    Activity context;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        context = getActivity();
        item_permissions_tv = (TextView)view.findViewById(R.id.item_permissions_tv);
        item_permissions_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPermission = new Intent(context, PermissionsActivity.class);
                context.startActivity(toPermission);

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    public static UserCenterFragment newInstance(Bundle bundle) {
        UserCenterFragment fragment = new UserCenterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
