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
//    {"j_mobile_mid":"864895022552002","j_pic_code":"4725",
//            "j_password":"841e5c1f5a8a9ebc34a723f66affb38787b9364f",
//            "j_app_version":"2.4.1_52_20170810","j_os_name":"samsung",
//            "j_mobile_type":"ZTE U795","j_os_sdk":"17",
//            "j_os_version":"Android4.2.2_JDQ39E","j_username":"leileima","salt":"b208cb62a85edb65d30f99ec3d08c434"}

    private String j_mobile_mid = "864895022552002";
    private String j_pic_code ;
    private String j_password = "841e5c1f5a8a9ebc34a723f66affb38787b9364f";
    private String j_app_version ="2.4.1_52_20170810";
    private String j_os_name = "samsung";
    private String j_mobile_type = "ZTE U795";
    private String j_os_sdk = "17";
    private String j_os_version = "Android4.2.2_JDQ39E";
    private String j_username;
    private String salt;

    public LoginInfoReqParam(String account,String pwd,String picCode,String salt){
        j_username = account;
        this.salt = salt;
        j_pic_code = picCode;
    }

    public String getJ_mobile_mid() {
        return j_mobile_mid;
    }

    public void setJ_mobile_mid(String j_mobile_mid) {
        this.j_mobile_mid = j_mobile_mid;
    }

    public String getJ_pic_code() {
        return j_pic_code;
    }

    public void setJ_pic_code(String j_pic_code) {
        this.j_pic_code = j_pic_code;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String j_password) {
        this.j_password = j_password;
    }

    public String getJ_app_version() {
        return j_app_version;
    }

    public void setJ_app_version(String j_app_version) {
        this.j_app_version = j_app_version;
    }

    public String getJ_os_name() {
        return j_os_name;
    }

    public void setJ_os_name(String j_os_name) {
        this.j_os_name = j_os_name;
    }

    public String getJ_mobile_type() {
        return j_mobile_type;
    }

    public void setJ_mobile_type(String j_mobile_type) {
        this.j_mobile_type = j_mobile_type;
    }

    public String getJ_os_sdk() {
        return j_os_sdk;
    }

    public void setJ_os_sdk(String j_os_sdk) {
        this.j_os_sdk = j_os_sdk;
    }

    public String getJ_os_version() {
        return j_os_version;
    }

    public void setJ_os_version(String j_os_version) {
        this.j_os_version = j_os_version;
    }

    public String getJ_username() {
        return j_username;
    }

    public void setJ_username(String j_username) {
        this.j_username = j_username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
