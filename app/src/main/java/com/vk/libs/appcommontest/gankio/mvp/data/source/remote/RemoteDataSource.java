package com.vk.libs.appcommontest.gankio.mvp.data.source.remote;

import android.support.annotation.NonNull;

import com.vk.libs.appcommontest.gankio.mvp.data.api.ApiHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.LoginInfoReqParam;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VK on 2017/2/8.
 * - 远端数据源
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private List<Integer> mReqIds = new ArrayList<>();

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull String username, @NonNull String password, @NonNull DataSourceCallback callback) {
        int reqId = ApiHelper.getDefault().login(new LoginInfoReqParam(username, password), callback);
        if (reqId > -1 && !mReqIds.contains(reqId)) {
            mReqIds.add(reqId);
        }

    }

    /**
     * 取消所有网络数据请求
     */
    @Override
    public void cancelAll() {
        ApiHelper.getDefault().cancelAll(mReqIds);
    }

}
