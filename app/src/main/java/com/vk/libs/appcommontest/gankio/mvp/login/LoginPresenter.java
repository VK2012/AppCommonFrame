package com.vk.libs.appcommontest.gankio.mvp.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.vk.libs.appcommon.utils.BitmapUtil;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.SaltEntity;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataRepository;
import com.vk.libs.appcommontest.gankio.mvp.data.source.DataSource;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by VK on 2017/2/7.
 */
public class LoginPresenter implements LoginContract.Presenter {

    public static final String TAG = "LoginPresenter";

    private LoginContract.IView mLoginView;

    private final DataRepository mDataRepository;

    private List<Subscription> mSubscriptions = new ArrayList<>();

    private CallbackImp mCallbackImp;

    private Bitmap bitmapCode;

    private String account,pwd,picCode;


    public LoginPresenter(@NonNull DataRepository dataRepository,
                          @NonNull LoginContract.IView loginView) {
        mDataRepository = dataRepository;
        mLoginView = loginView;
        mLoginView.setPresenter(this);
        mCallbackImp = new CallbackImp(LoginPresenter.this);
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

    }

    @Override
    public void saveToken(LoginInfoEntity loginInfoEntity) {
        // TODO: 2017/2/22 缓存token？session
        Log.d(TAG, "saveToken: account");
    }

    @Override
    public void getVerifyCode() {
        Subscription subscription = Observable.just("")
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLoginView.showLoading("获取验证码...");
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mDataRepository.getVerifyCode("3", getIMEI(mLoginView.getViewContext()), mCallbackImp);
                    }
                });

        mSubscriptions.add(subscription);
    }

    public void login(String salt){
        mDataRepository.login(account,pwd,picCode,salt,mCallbackImp);
    }

    @Override
    public void login(String username, String password,String picCode) {

        if (TextUtils.isEmpty(username)) {
            mLoginView.showMessage("username cannot be null");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginView.showMessage("password cannot be null");
            return;
        }

        if (TextUtils.isEmpty(picCode)) {
            mLoginView.showMessage("picCode cannot be null");
            return;
        }
        account = username;
        pwd = password;
        this.picCode = picCode;

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
                .subscribe(new Action1<Pair<String, String>>() {
                    @Override
                    public void call(Pair<String, String> stringStringPair) {
                        if (stringStringPair != null)
                            mDataRepository.getNewSalt( stringStringPair.first, mCallbackImp);
                        else
                            mLoginView.loginFail("param error!");
                    }
                });
//                .map(new Func1<Pair<String, String>, Pair<String, String>>() {
//                    @Override
//                    public Pair<String, String> call(Pair<String, String> stringStringPair) {
//                        //Aes+base64加密
//                        AESUtil mAes = new AESUtil();
//                        try {
//                            String username = mAes.encrypt(stringStringPair.first.getBytes("UTF8"));
//                            String password = mAes.encrypt(stringStringPair.second.getBytes("UTF8"));
//                            return new Pair<>(username, password);
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//
//                        return null;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Pair<String, String>>() {
//                    @Override
//                    public void call(Pair<String, String> stringStringPair) {
//                        if (stringStringPair != null)
//                            mDataRepository.login(LoginPresenter.this.hashCode(),stringStringPair.first, stringStringPair.second, mCallbackImp);
//                        else
//                            mLoginView.loginFail("param error!");
//                    }
//                });

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
                } else if (data instanceof String) {
                    byte[] pic = ((String) data).getBytes();
                    byte[] picData = Base64.decode(pic, Base64.DEFAULT);
                    loginPresenter.recycleBitmap();
                    loginPresenter.bitmapCode = BitmapUtil.bytesToBitmap(picData);
                    loginPresenter.mLoginView.hideLoading(null);
                    loginPresenter.mLoginView.updatePic(loginPresenter.bitmapCode);
                } else if (data instanceof SaltEntity) {
                    SaltEntity saltEntity = (SaltEntity) data;
                    loginPresenter.login(saltEntity.getSalt());
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

    public static final String getIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = manager.getDeviceId();
        return imei;
    }

    private void recycleBitmap() {
        if (bitmapCode != null && !bitmapCode.isRecycled()) {
            bitmapCode.recycle();
        }
    }
}
