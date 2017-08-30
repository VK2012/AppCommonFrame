package com.vk.libs.appcommontest.gankio.mvp.data.requestbody;

/**
 * Created by ruihong.tan on 2017/8/25.
 */

public class SaltReqParam {
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public SaltReqParam(String account){
        this.account = account;
    }
}
