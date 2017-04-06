package com.vk.libs.appcommon.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by vk on 2017/4/5.<br/>
 * - 通用Apdater
 *
 */

public abstract class CommonAdapter<T> extends BaseAdapter{

    protected Context mContext;

    List<T> mDatas;

    int layoutId;

    public CommonAdapter(@NonNull Context context, List<T> data, @NonNull int layoutId){
        this.mDatas = data;
        this.layoutId = layoutId;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas == null?0:mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        ViewHolder viewHolder = ViewHolder.get(mContext,contentView,layoutId,position);
        convert(viewHolder,getItem(position));
        return viewHolder.getContentView();
    }

    public void setData(List<T> data){
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public abstract void convert(ViewHolder viewHolder,T item);

}
