package com.vk.libs.appcommontest.gankio.mvp.mine.fav;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vk.libs.appcommon.ui.BaseActivity;

/**
 * Created by VK on 2017/1/18.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public class FavActivity extends BaseActivity implements FavContract.IView {

    public static final String TAG = "FavActivity";

    private FavContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void setPresenter(FavContract.Presenter presenter) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void refresh(String message) {

    }
}