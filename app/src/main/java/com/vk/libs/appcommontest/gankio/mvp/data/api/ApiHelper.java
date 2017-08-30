package com.vk.libs.appcommontest.gankio.mvp.data.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.vk.libs.appcommon.base.BaseApp;
import com.vk.libs.appcommon.cache.sharedpreference.SharedPreferenceHelper;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
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
                .client(genericClient())
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
    public <T,V> int httpPost(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback) {
        int reqId = -1;
        if (check()) {
            reqId = num.getAndIncrement();
            TaskWrapper<T,V> taskWrapper =
                    new TaskWrapper<>(param,targetCls, callback);
            Call<Result> call = mApiService.doPost(url,taskWrapper.getBody());
            mCallArray.append(reqId, call);
            taskWrapper.run(call);
        } else
            callback.onAccessFail("Server ip can not be null");
        return reqId;
    }

    public <T,V> int httpPost2(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback) {
        int reqId = -1;
        if (check()) {
            reqId = num.getAndIncrement();
            TaskWrapper<T,V> taskWrapper =
                    new TaskWrapper<>(param,targetCls, callback);
            Call<Result> call = mApiService.doPost(url,taskWrapper.getBody());
            mCallArray.append(reqId, call);
            taskWrapper.run2(call);
        } else
            callback.onAccessFail("Server ip can not be null");
        return reqId;
    }

    public <T,V> int httpPostSp(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback) {
        int reqId = -1;
        if (check()) {
            reqId = num.getAndIncrement();
            TaskWrapper<T,V> taskWrapper =
                    new TaskWrapper<>(param,targetCls, callback);
            Call<Object> call = mApiService.doPostSp(url,taskWrapper.getBody());
            mCallArray.append(reqId, call);
            taskWrapper.run3(call);
        } else
            callback.onAccessFail("Server ip can not be null");
        return reqId;
    }

//    /**
//     * http get方式请求
//     * @param url 请求url
//     * @param param 请求参数
//     * @param targetCls 期望的响应结果类型，不能为null
//     * @param callback 结果回调
//     * @param <T> 请求体范型
//     * @param <V> 响应体范型
//     * @return
//     */
//    public <T, V> int httpGet(String url, T param, Class<V> targetCls, DataSource.DataSourceCallback callback) {
//        int reqId = -1;
//        if (check()) {
//            reqId = num.getAndIncrement();
//            Call<Result<V>> call = mApiService.doGet(url);
//            mCallArray.append(reqId, call);
//            TaskWrapper<T, V> taskWrapper =
//                    new TaskWrapper<>(param, callback);
//            taskWrapper.run(call);
//        } else
//            callback.onAccessFail("Server ip can not be null");
//        return reqId;
//    }

    /**
     * 任务包装类
     * @param <T> 请求体
     */
    private class TaskWrapper<T,V> {
        /**请求体，主要用于post*/
        private RequestBody body;
        /**结果返还的callback*/
        private DataSource.DataSourceCallback callback;
        private Class<V> targetCls;
        private Gson gson = new Gson();
        private TaskWrapper(T param,Class<V>  targetCls, @NonNull DataSource.DataSourceCallback callback) {
            this.callback = checkNotNull(callback);
            this.targetCls = targetCls;
            if (param == null)
                return;
//            ReqBody<T> reqBody = new ReqBody<>();
//            reqBody.setParam(param);

//            LoginInfoReqParam loginInfoReqParam = new LoginInfoReqParam("leileima",null);
            // TODO: 2017/3/30  还需要配置timestamp,token,security三个参数值
//            FormBody formBody = new FormBody.Builder().build();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gson.toJson(param));
        }

        private RequestBody getBody() {
            return body;
        }

        private void run(Call<Result> call) {
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(!response.isSuccessful()) {
                        callback.onAccessFail("服务器访问异常" + response.code());
                        return;
                    }

                    Result result = response.body();
                    if (result != null&&"SUCCESS".equals(result.getCode())) {
//                        V obj = gson.fromJson(result.getData().toString(),targetCls);
//                        Log.d("tag",""+obj);
                        String jj = gson.toJson(result.getData());
                        V obj = gson.fromJson(jj,targetCls);
                        Log.d("vkvk", jj+" \n"+obj.toString());
                        if(obj == null)
                            callback.onAccessFail("数据解析异常");
                        else
                            callback.onAccessSuccess(obj);
                    }
                    else
                        callback.onAccessFail(result.getMessage());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    callback.onAccessFail("request fail "+t.getMessage());
                }
            });
        }

        private void run2(Call<Result> call) {
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(!response.isSuccessful()) {
                        callback.onAccessFail("服务器访问异常" + response.code());
                        return;
                    }

                    Result result = response.body();
                    if (result.getCode().equals("SUCCESS")) {
//                        V obj = gson.fromJson(result.getData().toString(),targetCls);
//                        Log.d("tag",""+obj);
                        String jj = gson.toJson(result.getData());
//                        V obj = gson.fromJson(jj,targetCls);
                        Log.d("vkvk", jj+" \n");
                        if(jj == null)
                            callback.onAccessFail("数据解析异常");
                        else
                            callback.onAccessSuccess(jj);
                    }
                    else
                        callback.onAccessFail(result.getMessage());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    callback.onAccessFail("request fail "+t.getMessage());
                }
            });
        }

        private void run3(Call<Object> call) {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if(!response.isSuccessful()) {
                        callback.onAccessFail("服务器访问异常" + response.code());
                        return;
                    }
//                        V obj = gson.fromJson(result.getData().toString(),targetCls);
//                        Log.d("tag",""+obj);
                        String jj = gson.toJson(response.body());
                        V obj = gson.fromJson(jj,targetCls);
                        Log.d("vkvk", jj+" \n");
                        if(jj == null)
                            callback.onAccessFail("数据解析异常");
                        else
                            callback.onAccessSuccess(obj);

                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    callback.onAccessFail("request fail "+t.getMessage());
                }
            });
        }
    }


    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

    interface ApiService {

        @POST
        Call<Result> doPost(@Url String url, @Body RequestBody body);

        @POST
        Call<Object> doPostSp(@Url String url, @Body RequestBody body);


//        @GET
//        <T extends Result> Call<T> doGet(@Url String url);

    }
}
