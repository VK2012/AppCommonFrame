package com.vk.libs.appcommon.crash;

/**
 * Created by VK on 2016/12/14.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private Thread.UncaughtExceptionHandler mOldHandler;
    public void init(){
        Thread.UncaughtExceptionHandler tmpHandler = Thread.getDefaultUncaughtExceptionHandler();
        if(mOldHandler == null)
            mOldHandler = tmpHandler;

    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

    }
}
