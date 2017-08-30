package com.vk.libs.appcommontest.gankio.mvp.data.source.remote;

import com.vk.libs.appcommontest.gankio.mvp.data.api.ApiHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

/**
 * Created by VK on 2017/2/8.
 * - 远端数据源
 */

public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    public static final String URL_LOGIN = "123";
    public static final String URL_GET_VERIFY_CODE = "34234";
    public static final String URL_GET_SALT = "25235";

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    public <T,V> void httpPost(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback){
        ApiHelper.getDefault().httpPost(url, param, targetCls, callback);
    }

    public <T,V> void httpPost2(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback){
        ApiHelper.getDefault().httpPost2(url, param, targetCls, callback);
    }

    public <T,V> void httpPostSp(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback){
        ApiHelper.getDefault().httpPostSp(url, param, targetCls, callback);
    }

//    public void login(int hashcode, @NonNull String username, @NonNull String password, @NonNull DataSourceCallback callback) {
//        int reqId = ApiHelper.getDefault().httpPost("admin/getEncryptTypeAndSalt", new LoginInfoReqParam(username, password), TestBean.class, callback);
//
//    }
//
//    public void getVerifyCode(int hashCode, String type, String imei, @NonNull DataSourceCallback callback) {
//        int reqId = ApiHelper.getDefault().httpPost2("admin/getPicCode", new VerifyCodeReqParam(type, imei), EmptyEntity.class, callback);
//    }
//
//    public void getNewSalt(int hashCode, String account,@NonNull DataSourceCallback callback) {
//        int reqId = ApiHelper.getDefault().httpPost("admin/getEncryptTypeAndSalt", new SaltReqParam(account), SaltEntity.class, callback);
//    }



}
