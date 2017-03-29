package com.vk.libs.appcommontest.gankio.mvp.main;

import com.vk.libs.appcommon.mvp.BasePresenter;
import com.vk.libs.appcommon.mvp.BaseView;

/**
 * Created by VK on 2017/1/19.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public interface MainContract {

    interface IView extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}