package com.h3c.idcloud.core.pojo.common;

import java.io.Serializable;

/**
 * Service参数基类
 * @apiNote
 * <ul>
 *     <li>mgtObjSid    :   租户ID</li>
 *     <li>userId       :   用户ID</li>
 *     <li>userAccount  :   用户名</li>
 *     <li>userPass     :   用户密码</li>
 *     <li>zoneId       :   区域ID</li>
 * </ul>
 * @author Chaohong.Mao
 */
public class ServiceBaseInput implements Serializable {
    /** 租户ID */
    private Long mgtObjSid;
    /** 用户ID */
    private String userId;
    /** 用户名 */
    private String userAccount;
    /** 用户密码 */
    private String userPass;
    /** 区域ID */
    private String zoneId;

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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }
}
