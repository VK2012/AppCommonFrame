package com.vk.libs.appcommontest.gankio.mvp.login;


import android.content.Context;
import android.graphics.Bitmap;

import com.vk.libs.appcommon.mvp.BasePresenter;
import com.vk.libs.appcommon.mvp.BaseView;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;

/**
 * Created by VK on 2017/2/7.
 */
public interface LoginContract {

    interface IView extends BaseView<Presenter> {

        void showLogin();
        void closeLogin(String message);
        void loginSuccess(LoginInfoEntity loginInfoEntity);
        void loginFail(String message);
        Context getViewContext();
        void updatePic(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password,String picCode);
        void saveToken(LoginInfoEntity loginInfoEntity);
        void getVerifyCode();
    }
}