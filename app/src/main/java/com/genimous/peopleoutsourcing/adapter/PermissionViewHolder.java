package com.genimous.peopleoutsourcing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genimous.peopleoutsourcing.activity.R;

/**
 * Created by wudi on 18/1/18.
 */

public class PermissionViewHolder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String [] permissionArray;
    public PermissionViewHolder(String [] permissionArray){
        this.permissionArray = permissionArray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return permissionArray.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView open_permission_tv,fail_permission_tv,fail_desc_permission_tv;
        //开启
        TextView open_tips_tv,permission_name_tv;
        public MyViewHolder(View view)
        {
            super(view);
            open_permission_tv = (TextView) view.findViewById(R.id.open_permission_tv);
            fail_permission_tv = (TextView) view.findViewById(R.id.fail_permission_tv);
            fail_desc_permission_tv = (TextView) view.findViewById(R.id.fail_desc_permission_tv);
            open_tips_tv = (TextView) view.findViewById(R.id.open_tips_tv);
            permission_name_tv = (TextView) view.findViewById(R.id.permission_name_tv);
        }
    }
}
