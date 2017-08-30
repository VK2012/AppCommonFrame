package com.vk.libs.appcommontest.gankio.mvp.data.responsebody;

/**
 * Created by ruihong.tan on 2017/8/25.
 */

public class SaltEntity {


    private String salt;

    private Integer pwdFlag;

    public Integer getPwdFlag() {
        return pwdFlag;
    }

    public String getSalt() {
        return salt;
    }

    public void setPwdFlag(Integer pwdFlag) {
        this.pwdFlag = pwdFlag;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

