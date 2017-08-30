package com.vk.libs.appcommontest.gankio.mvp.data.responsebody;

import java.util.List;

/**
 * Created by VK on 2017/2/8.
 */

public class LoginInfoEntity {


    private String auditStatus;

    private String contactPerson;
    private String carrierCode;
    private String jobNumber;
    private String memberName;
    private String mobile;
    private String orgCode;
    private List<String> roles;
    private List<String> romeRoles;
    private List<Permissions> permissions;

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRomeRoles() {
        return romeRoles;
    }

    public void setRomeRoles(List<String> romeRoles) {
        this.romeRoles = romeRoles;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "LoginInfoEntity{" +
                "auditStatus='" + auditStatus + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", memberName='" + memberName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", roles=" + roles +
                ", romeRoles=" + romeRoles +
                ", permissions=" + permissions +
                '}';
    }

    class Permissions{
        private String resourcevalue;

        public String getResourcevalue() {
            return resourcevalue;
        }

        public void setResourcevalue(String resourcevalue) {
            this.resourcevalue = resourcevalue;
        }

        @Override
        public String toString() {
            return "Permissions{" +
                    "resourcevalue='" + resourcevalue + '\'' +
                    '}';
        }
    }

}
