package com.vk.libs.appcommontest.gankio.mvp.mine;

import android.view.View;

import com.vk.libs.appcommon.eventbus.MessageEvent;
import com.vk.libs.appcommon.ui.EventBusFragment;
import com.vk.libs.appcommontest.R;

/**
 * Created by VK on 2017/1/20.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public class MineFragment extends EventBusFragment implements MineContract.IView {

    public static final String TAG = "MineFragment";

    private MineContract.Presenter mPresenter;

    @Override
    public void setPresenter(MineContract.Presenter presenter) {

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
        return R.layout.fragment_mine;
    }

    @Override
    protected void initWidget(View root) {

    }
}