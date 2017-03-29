package com.vk.libs.appcommontest.gankio.mvp.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by VK on 2017/2/8.<br/>
 * - 本地数据源
 */

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;

    // Prevent direct instantiation.
    private LocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull String username, @NonNull String password, @NonNull DataSourceCallback<LoginInfoEntity> loginCallback) {

    }


    @Override
    public void cancelAll() {

    }

}
