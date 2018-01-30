package com.genimous.peopleoutsourcing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.genimous.peopleoutsourcing.activity.R;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.bean.TryGameBean;

import java.util.ArrayList;

/**
 * Created by wudi on 18/1/18.
 */

public class TryGameViewHolder extends RecyclerView.Adapter<TryGameViewHolder.MyViewHolder> {

    ArrayList<TryGameBean> tbeanList;

    Context context;
    public TryGameViewHolder(ArrayList<TryGameBean> tbeanList, Context context){
        this.tbeanList = tbeanList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_trygame,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//            holder.open_permission_tv.setText(tbeanList.get(position).getPrrmissionStr());
        Log.i("aaa","tbeanList.get(position).getIcon() ======= "+tbeanList.get(position).getIcon());
        Glide.with(context).load(tbeanList.get(position).getIcon())
                .error(R.mipmap.icon)
                .into( holder.ImageView_ItemTryGame_icon);

            holder.TextView_ItemTryGame_title.setText(tbeanList.get(position).getName());
//            holder.ImageView_ItemTryGame_icon
//                    .setText(tbeanList.get(position).getPermissionFailDesc());

        holder.TextView_ItemTryGame_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aaa","download = "+tbeanList.get(position).getDownUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tbeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView TextView_ItemTryGame_button,TextView_ItemTryGame_title,TextView_ItemTryGame_info;
        //开启
        ImageView ImageView_ItemTryGame_icon;
        public MyViewHolder(View view)
        {
            super(view);
            TextView_ItemTryGame_button = (TextView) view.findViewById(R.id.TextView_ItemTryGame_button);
            TextView_ItemTryGame_title = (TextView) view.findViewById(R.id.TextView_ItemTryGame_title);
            TextView_ItemTryGame_info = (TextView) view.findViewById(R.id.TextView_ItemTryGame_info);
            ImageView_ItemTryGame_icon = (ImageView) view.findViewById(R.id.ImageView_ItemTryGame_icon);
        }
    }


}
