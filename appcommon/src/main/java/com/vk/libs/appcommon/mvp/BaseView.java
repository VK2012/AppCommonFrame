package com.vk.libs.appcommon.mvp;

/**
 * Created by VK on 2017/1/4.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    /**
     * 显示加载
     */
    void showLoading(String message);

    /**
     * 隐藏加载
     */
    void hideLoading(String message);

    /**
     * 显示信息
     */
    void showMessage(String message);

    /**
     * 刷新
     * @param message
     */
    void refresh(String message);


}
