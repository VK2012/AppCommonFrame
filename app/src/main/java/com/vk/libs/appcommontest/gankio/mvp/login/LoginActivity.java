package com.vk.libs.appcommontest.gankio.mvp.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vk.libs.appcommon.ui.EventBusActivity;
import com.vk.libs.appcommon.utils.ActivityUtils;
import com.vk.libs.appcommontest.R;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataRepository;


/**
 * Created by VK on 2017/2/7.
 */
public class LoginActivity extends EventBusActivity {

    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.contentFrame);
        }

        // Create the presenter
        new LoginPresenter(DataRepository.getInstance(getApplicationContext()),loginFragment);
    }

    @Override
    protected void initView() {
    }

}