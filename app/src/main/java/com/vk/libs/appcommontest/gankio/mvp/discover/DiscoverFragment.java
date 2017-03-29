package com.vk.libs.appcommontest.gankio.mvp.discover;

import com.vk.libs.appcommon.eventbus.MessageEvent;
import com.vk.libs.appcommon.ui.EventBusFragment;
import com.vk.libs.appcommontest.R;

/**
 * Created by VK on 2017/1/20.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public class DiscoverFragment extends EventBusFragment implements DiscoverContract.IView {

    public static final String TAG = "DiscoverFragment";

    private DiscoverContract.Presenter mPresenter;

    @Override
    public void setPresenter(DiscoverContract.Presenter presenter) {

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
        return R.layout.fragment_discover;
    }
}