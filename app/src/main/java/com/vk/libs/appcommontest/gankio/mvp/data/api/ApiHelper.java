package com.vk.libs.appcommontest.gankio.mvp.data.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.cache.sharedpreference.SharedPreferenceHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.requestbody.ReqBody;
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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by VK on 2017/2/8.<br/>
 * - 网络请求帮助类
 *
 */
public class ApiHelper {

    public static final String TAG = "ApiHelper";

    private AtomicInteger num = new AtomicInteger(1);

    public static final String BASE_URL = "baseUrl";

    private String baseUrl;

    private SparseArray<Call> mCallArray = new SparseArray<>();

    private ApiService mApiService;

    private static ApiHelper outInstance = new ApiHelper();

    public static ApiHelper getDefault() {
        return outInstance;
    }

    private ApiHelper() {
        updateBaseUrl();
    }

    public void updateBaseUrl() {
        // TODO: 2017/2/10  可能需要做一下配置

        baseUrl = SharedPreferenceHelper.getStringInDefault(BaseApp.getInstance(), BASE_URL);
        if (TextUtils.isEmpty(baseUrl)) {
            Log.d(TAG, "initApiService: baseUrl can not be null");
            mApiService = null;
            return;
        }
        if (baseUrl.charAt(baseUrl.length() - 1) != '/')
            baseUrl = baseUrl + '/';

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
     * http post方式请求
     * @param url 请求url
     * @param param 请求参数
     * @param targetCls 期望的响应结果类型，不能为null
     * @param callback 结果回调
     * @param <T> 请求体范型
     * @param <V> 响应体范型
     * @return
     */
    public <T, V> int httpPost(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback) {
        int reqId = -1;
        if (check()) {
            reqId = num.getAndIncrement();
            TaskWrapper<T, V> taskWrapper =
                    new TaskWrapper<>(param, callback);
            Call<Result<V>> call = mApiService.doPost(url,taskWrapper.getBody());
            mCallArray.append(reqId, call);
            taskWrapper.run(call);
        } else
            callback.onAccessFail("Server ip can not be null");
        return reqId;
    }

    /**
     * http get方式请求
     * @param url 请求url
     * @param param 请求参数
     * @param targetCls 期望的响应结果类型，不能为null
     * @param callback 结果回调
     * @param <T> 请求体范型
     * @param <V> 响应体范型
     * @return
     */
    public <T, V> int httpGet(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback) {
        int reqId = -1;
        if (check()) {
            reqId = num.getAndIncrement();
            Call<Result<V>> call = mApiService.doGet(url);
            mCallArray.append(reqId, call);
            TaskWrapper<T, V> taskWrapper =
                    new TaskWrapper<>(param, callback);
            taskWrapper.run(call);
        } else
            callback.onAccessFail("Server ip can not be null");
        return reqId;
    }

    /**
     * 任务包装类
     * @param <T> 请求体
     * @param <V> 响应体
     */
    private class TaskWrapper<T, V> {
        /**请求体，主要用于post*/
        private RequestBody body;
        /**结果返还的callback*/
        private DataSource.DataSourceCallback callback;

        private TaskWrapper(T param, @NonNull DataSource.DataSourceCallback callback) {
            this.callback = checkNotNull(callback);
            if (param == null)
                return;
            ReqBody<T> reqBody = new ReqBody<>();
            reqBody.setParam(param);
            // TODO: 2017/3/30  还需要配置timestamp,token,security三个参数值
            Gson gson = new Gson();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gson.toJson(reqBody));
        }

        private RequestBody getBody() {
            return body;
        }

        private void run(Call<Result<V>> call) {
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

    /**
     * 根据请求id集，取消目标请求集，旨在释放资源
     * @param reqIds 请求id集合
     */
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

    interface ApiService {

        @POST
        <T> Call<Result<T>> doPost(@Url String url, @Body RequestBody body);

        @GET
        <T> Call<Result<T>> doGet(@Url String url);

    }
}
