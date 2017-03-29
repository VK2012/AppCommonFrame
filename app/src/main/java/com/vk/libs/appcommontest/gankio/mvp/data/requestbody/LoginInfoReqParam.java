package com.vk.libs.appcommontest.gankio.mvp.data.requestbody;

/**
 * Created by VK on 2017/2/8.<br/>
 * - 登录
 */

public class LoginInfoReqParam {
    /*
    URL: /user/login.action
    param:
    {
      account: ‘admin’,
      password: ‘admin123’
    }

     */

    private String account;

    private String password;

    public LoginInfoReqParam(String account , String password){
        this.account = account;
        this.password = password;
    }

}
