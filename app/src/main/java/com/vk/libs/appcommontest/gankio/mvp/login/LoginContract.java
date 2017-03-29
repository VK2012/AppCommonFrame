package com.vk.libs.appcommontest.gankio.mvp.login;


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
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
        void saveToken(LoginInfoEntity loginInfoEntity);
    }
}