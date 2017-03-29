package com.vk.libs.appcommon.ui.extra;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk.libs.appcommon.utils.DimensUtils;

import static com.vk.libs.appcommon.utils.DimensUtils.dpToPx;

/**
 * Created by VK on 2017/1/20.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */

public class BottomNavButton extends LinearLayout {

    private static final int TOPLINE_COLOR = 0xffc9c8c8;

    private String name;

    private Class<Fragment> clz;

    private int iconResId;

    private View vTopline;

    private ImageView iconImageView;

    private TextView tvName;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomNavButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public BottomNavButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public BottomNavButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BottomNavButton(Context context) {
        super(context);
        initView();
    }

    /**
     * 添加顶部分割线
     */
    private void initView(){
        //顺序不能变
        addTopLine();
        addIcon();
        addName();
    }

    private void addTopLine(){
        vTopline = new View(getContext());
        LinearLayout.LayoutParams llp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
        vTopline.setBackgroundColor(TOPLINE_COLOR);
        llp.bottomMargin = dpToPx(getContext(),6);
        addView(vTopline,llp);
    }

    /**
     * 添加图标
     */
    private void addIcon(){
        iconImageView = new ImageView(getContext());
        iconImageView.setFocusable(false);
        iconImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iconImageView.setImageResource(iconResId);

        int size = DimensUtils.dpToPx(getContext(),20);
        LinearLayout.LayoutParams llp = new LayoutParams(size,size);
        addView(iconImageView,llp);

    }

    /**
     * 添加名字
     */
    private void addName(){
        tvName = new TextView(getContext());
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        tvName.setText(name);

        LinearLayout.LayoutParams llp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0,1);
        llp.topMargin = DimensUtils.dpToPx(getContext(),4);
        llp.bottomMargin = DimensUtils.dpToPx(getContext(),2);
        addView(tvName,llp);

    }

    public void setButtonName(String name){
        this.name = name;
    }

    public void setButtonClz(Class<Fragment> clz){
        this.clz = clz;
    }

    public void setButtonIconRes(int iconResId){
        this.iconResId = iconResId;
    }
}
