package com.vk.libs.appcommon.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;
import com.vk.libs.appcommon.base.BaseApp;

import butterknife.ButterKnife;

/**
 * Created by VK on 2017/1/13.
 * Contact with trh5176891@126.com
 */

public abstract class BaseFragment extends Fragment {

    protected View mRoot;

    protected Bundle mBundle;

    protected LayoutInflater mInflater;

    protected Toast mToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
        initToast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
            ButterKnife.bind(this, mRoot);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    /**
     * 视图组件绑定前的预处理
     * @param root
     */
    protected void onBindViewBefore(View root) {
        // ...
    }

    /**
     * 获取布局资源ID
     * @return
     */
    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    /**
     * 初始化视图组件
     * @param root
     */
    protected abstract void initWidget(View root);

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    protected void onRestartInstance(Bundle bundle) {

    }

    private void initToast(){
        mToast = Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
    }

    protected void showToast(String message){
        mToast.setText(message);
        mToast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mToast != null)
            mToast.cancel();
        RefWatcher refWatcher = BaseApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
