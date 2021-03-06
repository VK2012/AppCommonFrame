package com.vk.libs.appcommontest.gankio.mvp.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.network.NetworkUtil;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.LoginInfoReqParam;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.SaltReqParam;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.VerifyCodeReqParam;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.EmptyEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.SaltEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.source.local.LocalDataSource;
import com.vk.libs.appcommontest.gankio.mvp.data.source.remote.RemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by VK on 2017/2/8.<br/>
 * - 数据仓库，包装类
 */

public class DataRepository implements DataSource {

    public static final String TAG = "DataRepository";

    private static DataRepository INSTANCE = null;

    private final DataSource mLocalDataSource;

    private final RemoteDataSource mRemoteDataSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = true;

    // Prevent direct instantiation.
    private DataRepository(Context context) {
        mRemoteDataSource = RemoteDataSource.getInstance();
        mLocalDataSource = LocalDataSource.getInstance(context);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     * @Context context
     * @return the {@link DataRepository} instance
     */
    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull String username, @NonNull String password,@NonNull String picCode, @NonNull String salt, @NonNull DataSourceCallback loginCallback) {
        checkNotNull(username);
        checkNotNull(password);

        if(mCacheIsDirty){
            if(!NetworkUtil.isConnected(BaseApp.getInstance())){
                Log.d(TAG, "login: network is unavailable");
                loginCallback.onAccessFail("network is unavailable");
                return ;
            }
            mRemoteDataSource.httpPostSp(RemoteDataSource.URL_LOGIN, new LoginInfoReqParam(username, password,picCode,salt),LoginInfoEntity.class,loginCallback);
            Log.d(TAG, "login: test mode");
//            loginCallback.onAccessSuccess(new LoginInfoEntity());
        }
    }

    @Override
    public void getVerifyCode(String type, String imei, @NonNull DataSourceCallback callback) {
        if(!NetworkUtil.isConnected(BaseApp.getInstance())){
            Log.d(TAG, "login: network is unavailable");
            callback.onAccessFail("network is unavailable");
            return ;
        }
        mRemoteDataSource.httpPost2(RemoteDataSource.URL_GET_VERIFY_CODE, new VerifyCodeReqParam(type, imei),EmptyEntity.class,callback);
    }

    @Override
    public void getNewSalt(String account, @NonNull DataSourceCallback callback) {
        if(!NetworkUtil.isConnected(BaseApp.getInstance())){
            Log.d(TAG, "login: network is unavailable");
            callback.onAccessFail("network is unavailable");
            return ;
        }
        mRemoteDataSource.httpPost(RemoteDataSource.URL_GET_SALT,  new SaltReqParam(account),SaltEntity.class,callback);
    }


}
