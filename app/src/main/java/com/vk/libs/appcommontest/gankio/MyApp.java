package com.vk.libs.appcommontest.gankio;

import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.cache.sharedpreference.SharedPreferenceHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.api.ApiHelper;

/**
 * Created by VK on 2016/12/13.
 */

public class MyApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceHelper.putStringInDefault(this, ApiHelper.BASE_URL,"http://172.31.52.15:8080");
    }
}
