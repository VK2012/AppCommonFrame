package com.vk.libs.appcommontest.gankio.mvp.login;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;

import com.vk.libs.appcommon.utils.AESUtil;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataRepository;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by VK on 2017/2/7.
 */
public class LoginPresenter implements LoginContract.Presenter {

    public static final String TAG = "LoginPresenter";

    private LoginContract.IView mLoginView;

    private final DataRepository mDataRepository;

    private List<Subscription> mSubscriptions = new ArrayList<>();


    public LoginPresenter(@NonNull DataRepository dataRepository,
                          @NonNull LoginContract.IView loginView) {
        mDataRepository = dataRepository;
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mSubscriptions.size() > 0) {
            for (Subscription tmp : mSubscriptions) {
                if (!tmp.isUnsubscribed()) {
                    tmp.unsubscribe();
                }
            }
            mSubscriptions.clear();
        }
        mDataRepository.cancelAll(this.hashCode());

    }

    @Override
    public void saveToken(LoginInfoEntity loginInfoEntity) {
        // TODO: 2017/2/22 缓存token？session
        Log.d(TAG, "saveToken: account");
    }

    @Override
    public void login(String username, String password) {

        if (TextUtils.isEmpty(username)) {
            mLoginView.showMessage("username cannot be null");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginView.showMessage("password cannot be null");
            return;
        }

        //无论是本地缓存还是网络数据，都以异步方式请求

        Pair<String, String> s = new Pair<>(username, password);
        Subscription subscription = Observable.just(s)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLoginView.showLogin();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<Pair<String, String>, Pair<String, String>>() {
                    @Override
                    public Pair<String, String> call(Pair<String, String> stringStringPair) {
                        //Aes+base64加密
                        AESUtil mAes = new AESUtil();
                        try {
                            String username = mAes.encrypt(stringStringPair.first.getBytes("UTF8"));
                            String password = mAes.encrypt(stringStringPair.second.getBytes("UTF8"));
                            return new Pair<>(username, password);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Pair<String, String>>() {
                    @Override
                    public void call(Pair<String, String> stringStringPair) {
                        if (stringStringPair != null)
                            mDataRepository.login(LoginPresenter.this.hashCode(),stringStringPair.first, stringStringPair.second, new CallbackImp(LoginPresenter.this));
                        else
                            mLoginView.loginFail("param error!");
                    }
                });

        mSubscriptions.add(subscription);
    }

    public static class CallbackImp implements DataSource.DataSourceCallback {

        private WeakReference<LoginPresenter> mLoginPresenterWeakReference;

        public CallbackImp(LoginPresenter loginPresenter) {
            mLoginPresenterWeakReference = new WeakReference<>(loginPresenter);
        }

        @Override
        public void onAccessSuccess(Object data) {
            LoginPresenter loginPresenter = mLoginPresenterWeakReference.get();
            if (loginPresenter != null) {
                if (data instanceof LoginInfoEntity) {
                    LoginInfoEntity loginInfoEntity = (LoginInfoEntity) data;
                    loginPresenter.saveToken(loginInfoEntity);
                    loginPresenter.mLoginView.loginSuccess(loginInfoEntity);
                }
            }
        }

        @Override
        public void onAccessFail(String message) {
            LoginPresenter loginPresenter = mLoginPresenterWeakReference.get();
            if (loginPresenter != null)
                loginPresenter.mLoginView.loginFail(message);
        }
    }


}
