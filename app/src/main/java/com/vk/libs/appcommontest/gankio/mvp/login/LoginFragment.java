package com.vk.libs.appcommontest.gankio.mvp.login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vk.libs.appcommon.ui.EventBusFragment;
import com.vk.libs.appcommontest.R;
import com.vk.libs.appcommontest.gankio.mvp.data.responsebody.LoginInfoEntity;
import com.vk.libs.appcommontest.gankio.mvp.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by VK on 2017/2/8.
 */
public class LoginFragment extends EventBusFragment implements LoginContract.IView {

    public static final String TAG = "LoginFragment";

    private LoginContract.Presenter mPresenter;

    @BindView(R.id.et_login_username)
    EditText et_login_username;

    @BindView(R.id.et_login_password)
    EditText et_login_password;

    @BindView(R.id.tv_login_version)
    TextView tv_login_version;

    @BindView(R.id.btn_login_signin)
    Button btn_login_signin;

    public static LoginFragment newInstance(){
        Bundle arguments = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showLogin() {
        btn_login_signin.setEnabled(false);
        showLoading(getString(R.string.login_signin_show));
    }

    @Override
    public void closeLogin(String message) {
        btn_login_signin.setEnabled(true);
        hideLoading(message);
    }

    @Override
    public void loginSuccess(LoginInfoEntity loginInfoEntity) {
        closeLogin(null);
        //保存token
        mPresenter.saveToken(loginInfoEntity);

        //进入主界面
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    /**
     * 登录失败
     * @param message 失败时附带的信息
     */
    @Override
    public void loginFail(String message) {
        closeLogin(message);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    /**
     * 显示'正在加载'的界面
     * @param message 附带显示的信息
     */
    @Override
    public void showLoading(String message) {

    }

    /**
     * 隐藏'正在加载'的界面
     * @param message 附带消息，可以为null
     */
    @Override
    public void hideLoading(String message) {
        if(!TextUtils.isEmpty(message))
            showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        //可以选择使用Toast 或 Dialog
        showToast(message);
    }

    @Override
    public void refresh(String message) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_login;
    }

    @Override
    protected void initWidget(View root) {
        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
            tv_login_version.setText("versoin "+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击登录
     */
    @OnClick(R.id.btn_login_signin)
    void doLogin(){
        Log.d(TAG, "doLogin: ");
        String username = et_login_username.getText().toString().trim();
        String password = et_login_password.getText().toString();

        mPresenter.login(username,password);
    }

    /**
     * 点击忘记密码
     */
    @OnClick(R.id.btn_login_forget)
    void doForget(){

    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}