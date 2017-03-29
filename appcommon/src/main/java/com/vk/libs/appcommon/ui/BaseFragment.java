package com.vk.libs.appcommon.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
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

    protected void onBindViewBefore(View root) {
        // ...
    }

    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    protected void initWidget(View root) {

    }

    protected void initData() {

    }

    protected void onRestartInstance(Bundle bundle) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
