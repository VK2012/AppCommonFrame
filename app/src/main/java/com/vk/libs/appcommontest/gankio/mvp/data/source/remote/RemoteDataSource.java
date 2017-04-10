package com.vk.libs.appcommontest.gankio.mvp.data.source.remote;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.vk.libs.appcommontest.gankio.mvp.data.api.ApiHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.LoginInfoReqParam;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
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

    private SparseArray<List<Integer>> mReqIds = new SparseArray<>();

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(int hashcode, @NonNull String username, @NonNull String password, @NonNull DataSourceCallback callback) {
        int reqId = ApiHelper.getDefault().httpPost("/login", new LoginInfoReqParam(username, password), LoginInfoEntity.class, callback);
        if (reqId > -1) {
            int index = mReqIds.indexOfKey(hashcode);
            List<Integer> reqIds = null;
            if (index >= 0) {
                reqIds = mReqIds.get(hashcode);
            } else {
                reqIds = new ArrayList<>();
                mReqIds.put(hashcode, reqIds);
            }
            if (!reqIds.contains(reqId))
                reqIds.add(reqId);
        }

    }

    /**
     * 取消所有网络数据请求
     */
    @Override
    public void cancelAll(int hashcode) {
        int index = mReqIds.indexOfKey(hashcode);
        List<Integer> reqIds = null;
        if (index >= 0) {
            reqIds = mReqIds.get(hashcode);
        }
        ApiHelper.getDefault().cancelAll(reqIds);
    }

}
