package com.genimous.peopleoutsourcing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.bean.FastMoneyBean;
import com.genimous.peopleoutsourcing.fragment.SubFastMoney;

import java.util.ArrayList;

/**
 * Created by wudi on 18/1/18.
 */

public class FastMoneyViewHolder extends RecyclerView.Adapter<FastMoneyViewHolder.MyViewHolder> {

    ArrayList<FastMoneyBean> fbeanList;

    Context context;
    public FastMoneyViewHolder(ArrayList<FastMoneyBean> fbeanList, Context context){
        this.fbeanList = fbeanList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_makemoney,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.open_permission_tv.setText(pbeanList.get(position).getPrrmissionStr());
            holder.TextView_ItemMakeMoney_title.setText(fbeanList.get(position).getFastMoneyTitle());
            holder.TextView_ItemMakeMoney_info.setText(fbeanList.get(position).getFastMoneyInfo());
            holder.TextView_ItemMakeMoney_button.setText("马上进入");
            holder.TextView_ItemMakeMoney_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubFastMoney.start(context);
                }
            });
//            holder.fail_desc_permission_tv
//                    .setText(fbeanList.get(position).getPermissionFailDesc());
    }

    @Override
    public int getItemCount() {
        return fbeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView TextView_ItemMakeMoney_title,TextView_ItemMakeMoney_button,TextView_ItemMakeMoney_info;
        //开启
//        TextView ImageView_ItemMakeMoney_icon;
        public MyViewHolder(View view)
        {
            super(view);
            TextView_ItemMakeMoney_title = (TextView) view.findViewById(R.id.TextView_ItemMakeMoney_title);
            TextView_ItemMakeMoney_button = (TextView) view.findViewById(R.id.TextView_ItemMakeMoney_button);
            TextView_ItemMakeMoney_info = (TextView) view.findViewById(R.id.TextView_ItemMakeMoney_info);
//            permission_name_tv = (TextView) view.findViewById(R.id.permission_name_tv);
        }
    }


}
