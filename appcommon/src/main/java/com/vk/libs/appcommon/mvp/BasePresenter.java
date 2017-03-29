package com.vk.libs.appcommon.mvp;

/**
 * Created by VK on 2017/1/4.
 */

public interface BasePresenter {

    void onCreate();

    void onRestart();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
