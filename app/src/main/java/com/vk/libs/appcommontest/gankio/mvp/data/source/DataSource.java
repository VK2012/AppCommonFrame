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
    void login(int hashcode,@NonNull String username, @NonNull String password, @NonNull DataSourceCallback loginCallback);


    /**
     * 取消所有数据请求
     */
    void cancelAll(int hashcode);
}
