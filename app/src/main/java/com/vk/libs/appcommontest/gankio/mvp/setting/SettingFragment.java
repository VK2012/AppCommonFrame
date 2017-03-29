package com.vk.libs.appcommontest.gankio.mvp.setting;

import com.vk.libs.appcommon.ui.EventBusFragment;
import com.vk.libs.appcommontest.R;

/**
 * Created by VK on 2017/1/20.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public class SettingFragment extends EventBusFragment implements SettingContract.IView {

    public static final String TAG = "SettingFragment";

    private SettingContract.Presenter mPresenter;

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }
}