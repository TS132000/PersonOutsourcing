package com.genimous.peopleoutsourcing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.bean.PermissionBean;

import java.util.ArrayList;

/**
 * Created by wudi on 18/1/18.
 */

public class PermissionViewHolder extends RecyclerView.Adapter<PermissionViewHolder.MyViewHolder> {

    ArrayList<PermissionBean> pbeanList;

    Context context;
    public PermissionViewHolder(ArrayList<PermissionBean> pbeanList, Context context){
        this.pbeanList = pbeanList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_check_permissions_status,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (pbeanList.get(position).isOpen()){

        } else {
//            holder.open_permission_tv.setText(pbeanList.get(position).getPrrmissionStr());
            holder.open_tips_tv.setVisibility(View.GONE);
            holder.permission_name_tv.setVisibility(View.GONE);
            holder.fail_permission_tv.setText(pbeanList.get(position).getPermissionFailStr());
            holder.fail_desc_permission_tv
                    .setText(pbeanList.get(position).getPermissionFailDesc());
        }
    }

    @Override
    public int getItemCount() {
        return pbeanList.size();
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
