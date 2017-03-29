package com.vk.libs.appcommon.base;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by VK on 2016/12/12.
 */

public class BaseApp extends Application{

    private static BaseApp mApp;

    public static BaseApp getInstance(){return mApp;}

    @Override
    public void onCreate() {
        super.onCreate();
//        extCacheDir = getExternalCacheDir().getPath();
//        cacheDir = getCacheDir().getPath();
        mApp = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
        // Normal app init code...
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApp application = (BaseApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;


}
