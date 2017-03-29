package com.vk.libs.appcommontest.gankio.mvp.data.source;

import android.support.annotation.NonNull;

import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;


/**
 * Created by VK on 2017/2/8.<br/>
 * - 数据源接口
 */

public interface DataSource {

    interface DataSourceCallback<T> {

        void onAccessSuccess(T data);

        void onAccessFail(String message);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param loginCallback
     */
    void login(@NonNull String username, @NonNull String password, @NonNull DataSourceCallback<LoginInfoEntity> loginCallback);


    /**
     * 取消所有数据请求
     */
    void cancelAll();
}
