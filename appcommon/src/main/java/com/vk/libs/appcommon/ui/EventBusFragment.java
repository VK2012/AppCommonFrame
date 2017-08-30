package com.vk.libs.appcommon.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.squareup.leakcanary.RefWatcher;
import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by VK on 2017/1/13.
 */

public abstract class EventBusFragment extends BaseFragment{

    protected  ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        RefWatcher refWatcher = BaseApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIMessageEvent(MessageEvent messageEvent){};

    protected void showProgressDialog(String text) {
        cancelProgressDialog();
        progressDialog = ProgressDialog.show(getActivity(), "", text, false, true);
    }

    protected void cancelProgressDialog() {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
