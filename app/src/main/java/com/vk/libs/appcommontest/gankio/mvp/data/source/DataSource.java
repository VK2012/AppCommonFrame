package com.vk.libs.appcommontest.gankio.mvp.data.source;

import android.support.annotation.NonNull;


/**
 * Created by VK on 2017/2/8.<br/>
 * - 数据源接口
 */

public interface DataSource {

    interface DataSourceCallback {

        void onAccessSuccess(Object data);

        void onAccessFail(String message);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param loginCallback
     */
    void login(@NonNull String username, @NonNull String password,@NonNull String picCode,@NonNull String salt, @NonNull DataSourceCallback loginCallback);


    /**
     * 获取图片验证码
     */
    void getVerifyCode(String type,String imei,@NonNull DataSourceCallback loginCallback);

    /***
     * 拿盐
     * @param account
     */
    void getNewSalt(String account,@NonNull DataSourceCallback loginCallback);
}
