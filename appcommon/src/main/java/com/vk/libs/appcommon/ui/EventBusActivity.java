package com.vk.libs.appcommon.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.squareup.leakcanary.RefWatcher;
import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by VK on 2017/1/13.
 * Contact with trh5176891@126.com
 */

public abstract class EventBusActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        RefWatcher refWatcher = BaseApp.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIMessageEvent(MessageEvent messageEvent){};

}
