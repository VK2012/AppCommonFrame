package com.vk.libs.appcommontest.gankio.mvp.home;

import com.vk.libs.appcommon.eventbus.MessageEvent;
import com.vk.libs.appcommon.ui.EventBusFragment;
import com.vk.libs.appcommontest.R;

/**
 * Created by VK on 2017/1/20.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public class HomeFragment extends EventBusFragment implements HomeContract.IView {

    public static final String TAG = "HomeFragment";

    private HomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

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
    public void onUIMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}