package com.vk.libs.appcommontest.gankio.mvp.data.api;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.cache.sharedpreference.SharedPreferenceHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.LoginInfoReqParam;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.ReqBody;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by VK on 2017/2/8.
 */
public class ApiHelper {

    public static final String TAG = "ApiHelper";

    private AtomicInteger num = new AtomicInteger(1);

    public static final String BASEURL = "baseUrl";

    private static ApiHelper outInstance = new ApiHelper();

    private String baseUrl;

    private SparseArray<Call> mCallArray = new SparseArray<>();

    public static ApiHelper getDefault() {
        return outInstance;
    }

    private ApiService mApiService;

    private ApiHelper() {
        updateBaseUrl();
    }

    public void updateBaseUrl() {
        // TODO: 2017/2/10  可能需要做一下配置

        baseUrl = SharedPreferenceHelper.getStringInDefault(BaseApp.getInstance(), BASEURL);
        if (TextUtils.isEmpty(baseUrl)) {
            Log.d(TAG, "initApiService: baseUrl can not be null");
            mApiService = null;
            return;
        }
        if(baseUrl.charAt(baseUrl.length() - 1) != '/')
            baseUrl = baseUrl+'/';

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    private boolean check() {
        if (mApiService == null) {
            Log.d(TAG, "check: baseUrl can not be null,please invoke ApiHelper.updateBaseUrl() to init!");
            return false;
        }
        return true;
    }

    /**
     * 登录
     * @param param 请求参数对象
     * @param callback 回调对象
     * @return 唯一请求序号
     */
    public int login(LoginInfoReqParam param, DataSource.DataSourceCallback<LoginInfoEntity> callback) {
        int reqId = -1;
        if (check()) {
            reqId = num.getAndIncrement();
            TaskWrapper<LoginInfoReqParam, LoginInfoEntity> taskWrapper =
                    new TaskWrapper<>(param);
            Call<Result<LoginInfoEntity>> call = mApiService.login(taskWrapper.getBody());
            mCallArray.append(reqId, call);
            taskWrapper.run(call, callback);
        }else
            callback.onAccessFail("Server ip can not be null");
        return reqId;
    }



    private class TaskWrapper<T, V> {
        private RequestBody body;

        private TaskWrapper(T param) {
            ReqBody<T> reqBody = new ReqBody<>();
            reqBody.setParam(param);
            // TODO: 2017/3/30  还需要配置timestamp,token,security三个参数值
            Gson gson = new Gson();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gson.toJson(reqBody));
        }

        private RequestBody getBody() {
            return body;
        }

        private void run(Call<Result<V>> call, final DataSource.DataSourceCallback<V> callback) {
            call.enqueue(new Callback<Result<V>>() {
                @Override
                public void onResponse(Call<Result<V>> call, Response<Result<V>> response) {
                    Result<V> result = response.body();
                    if (result.getResult().equals("success"))
                        callback.onAccessSuccess(result.getData());
                    else
                        callback.onAccessFail(result.getMessage());
                }

                @Override
                public void onFailure(Call<Result<V>> call, Throwable t) {
                    callback.onAccessFail("request fail");
                }
            });
        }
    }

    interface ApiService {

        /**2.2.1. 登录*/
        @POST(Url.LOGIN)
        Call<Result<LoginInfoEntity>> login(@Body RequestBody body);

    }

    class Url{

        /**2.2.1. 登录*/
        private static final String LOGIN = "/user/login.action";


    }

    public void cancelAll(List<Integer> reqIds) {
        if (reqIds != null && reqIds.size() > 0) {
            for (Integer id : reqIds) {
                Call call = mCallArray.get(id);
                if (call != null) {
                    call.cancel();
                    mCallArray.remove(id);
                }
            }
        }
    }
}
