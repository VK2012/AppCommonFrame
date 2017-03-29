package com.vk.libs.appcommon.ui.extra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.ui.EventBusFragment;
import com.vk.libs.appcommon.utils.DimensUtils;

/**
 * Created by VK on 2017/1/20.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
//将下列布局代码加入到父布局
//      <fragment
//        android:id="@+id/fragment_nav"
//        android:layout_width="match_parent"
//        android:name="com.vk.libs.appcommon.ui.extra.BottomNavFragment"
//        android:layout_height="48dp"/>
public class BottomNavFragment extends EventBusFragment {

    private String[] tabNames;

    private int[] tabIconRes;

    private Class[] tabClass;


    private int bgColor = -1;

    private static final int DEFAULT_HEIGHT = DimensUtils.dpToPx(BaseApp.getInstance(),48);

    private int height = DEFAULT_HEIGHT;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = createView();
        }

        return mRoot;
    }

    private View createView() {
        //创建底部导航栏
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));

        //添加tab
        LinearLayout.LayoutParams llp;
        for (int i = 0; i < tabClass.length; i++) {
            BottomNavButton bottomNavButton = new BottomNavButton(getContext());
            bottomNavButton.setButtonName(tabNames[i]);
            bottomNavButton.setButtonClz(tabClass[i]);
            bottomNavButton.setButtonIconRes(tabIconRes[i]);

            llp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
            linearLayout.addView(bottomNavButton,llp);
        }
        if(bgColor > 0)
            linearLayout.setBackgroundColor(bgColor);
        return linearLayout;
    }

    public void setup(String[] tabNames, int[] tabIconRes, Class[] tabClass) {
        setup(0,bgColor,tabNames,tabIconRes,tabClass);
    }

    public void setup(int bgColor,String[] tabNames, int[] tabIconRes, Class[] tabClass) {
        setup(0,bgColor,tabNames,tabIconRes,tabClass);
    }

    public void setup(int height,int bgColor,String[] tabNames, int[] tabIconRes, Class[] tabClass) {
        if (!(tabClass.length == tabNames.length && tabIconRes.length == tabNames.length))
            throw new IllegalArgumentException("Make sure:tabClass.length == tabNames.length == tabNames.length ");

        this.bgColor = bgColor;
        this.height = height > DEFAULT_HEIGHT?height:DEFAULT_HEIGHT;
        this.tabNames = tabNames;
        this.tabIconRes = tabIconRes;
        this.tabClass = tabClass;


    }

    public void setBackgroundColor(int color) {
        bgColor = color;
        mRoot.setBackgroundColor(color);
    }
}
