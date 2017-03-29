package com.vk.libs.appcommontest.gankio.mvp.data.api;

/**
 * Created by VK on 2017/2/8.
 */

public final class Result<T> {

    private String result;

    private String message;

    private T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
