package com.vk.libs.appcommontest.gankio.mvp.data.requestbody;

/**
 * Created by VK on 2017/2/8.<br/>
 * - 基于gson 序列化与反序列化
 */

public class ReqBody<T> {

    private String timestamp;

    private String token;

    private String security;

    private T param;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
