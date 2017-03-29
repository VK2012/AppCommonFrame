package com.vk.libs.appcommontest;

import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by VK on 2016/12/14.
 */


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{
    List<ActivityInfo> targets;

    public MyAdapter(List<ActivityInfo> targets) {
        this.targets = targets;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
        myViewHolder.view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(targets.get(position).loadLabel( holder.mTextView.getContext().getPackageManager()));
        holder.pos = position;
        holder.view.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return targets == null?0:targets.size();
    }

    @Override
    public void onClick(View v) {
        MyViewHolder myViewHolder = (MyViewHolder) v.getTag();
        if(myViewHolder != null && mOnItemClickListener!= null){
            mOnItemClickListener.onItemClick(v,myViewHolder.pos,targets.get(myViewHolder.pos));
        }
    }

    interface OnItemClickListener{
        void onItemClick(View view,int position,ActivityInfo activityInfo);
    }

    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    int pos;
    View view;
    TextView mTextView;
    public MyViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        mTextView = (TextView) itemView.findViewById(R.id.tv);

    }
}
