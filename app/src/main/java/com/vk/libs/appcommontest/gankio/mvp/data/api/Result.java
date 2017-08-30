package com.vk.libs.appcommontest.gankio.mvp.data.api;

/**
 * Created by VK on 2017/2/8.
 */

public class Result {

    private String result;

    private String code;

    private String message;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
