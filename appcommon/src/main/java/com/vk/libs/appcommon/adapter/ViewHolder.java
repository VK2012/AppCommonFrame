package com.vk.libs.appcommon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by vk on 2017/4/5.
 */

public class ViewHolder {

    private SparseArray<View> mViews;

    private View mContentView;

    private int mPosition;

    private int mOldPosition;

    private Context mContext;

    private int mLayoutId;

    private ViewHolder(Context context,int layoutId,int position){
        mPosition = position;
        mOldPosition = -1;
        mContext = context;
        mLayoutId = layoutId;
        mViews = new SparseArray<>();
        mContentView = LayoutInflater.from(context).inflate(layoutId,null);
        mContentView.setTag(this);

    }

    public static ViewHolder get(Context context, View contentView,int layoutId,int position){
        if(contentView == null)
            return new ViewHolder(context,position,layoutId);
        else{
            ViewHolder viewHolder = (ViewHolder) contentView.getTag();
            viewHolder.mOldPosition = viewHolder.mPosition;
            viewHolder.mPosition = position;
            return viewHolder;
        }

    }

    /**
     * 查找子view
     * @param viewId
     * @param <T>
     * @return
     */
    private <T extends View> T getView(int viewId){

        View view = mViews.get(viewId);
        if(view == null){
            //之前没有缓存过的，直接find查找后缓存
            view = mContentView.findViewById(viewId);
            mViews.put(viewId,view);
        }

        return (T) view;

    }

    public View getContentView(){
        return mContentView;
    }

    /**
     * 获取当前位置
     * @return
     */
    public int getPosition(){
        return mPosition;
    }

    /**
     * 获取旧位置
     * @return
     */
    public int getOldPosition() {
        return mOldPosition;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public ViewHolder setText(int viewId, int stringId){
        TextView textView = getView(viewId);
        textView.setText(stringId);
        return this;
    }

    public ViewHolder setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageResource(int viewId,int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable){
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置可视化属性
     * @param viewId
     * @param visibility
     */
    public void setVisibility(int viewId,int visibility){
        View view = getView(viewId);
        view.setVisibility(visibility);
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public TextView getTextView(int viewId){
        return getView(viewId);
    }

    public ImageView getImageView(int viewId){
        return getView(viewId);
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取自定义View对象
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getCustomerView(int viewId){
        return getView(viewId);
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


}
