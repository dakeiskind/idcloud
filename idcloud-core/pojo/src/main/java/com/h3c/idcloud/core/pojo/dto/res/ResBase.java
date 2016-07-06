package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

/**
 * com.h3c.idcloud.core.pojo.dto.res
 *
 * @author Chaohong.Mao
 */
public class ResBase implements Serializable {
    /** 租户ID */
    private String tenantId;
    /** 租户名 */
    private String tenantName;
    /** 用户ID */
    private String userId;
    /** 用户名 */
    private String userAccount;
    /** 用户密码 */
    private String userPass;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /** 区域ID */
    private String zoneId;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
