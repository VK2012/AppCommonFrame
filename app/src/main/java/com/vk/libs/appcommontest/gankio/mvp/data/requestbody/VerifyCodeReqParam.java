package com.vk.libs.appcommontest.gankio.mvp.data.requestbody;

/**
 * Created by ruihong.tan on 2017/8/25.
 */

public class VerifyCodeReqParam {

    private String type;
    private String mid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public VerifyCodeReqParam(String type,String mid){
        this.type = type;
        this.mid = mid;
    }
}
