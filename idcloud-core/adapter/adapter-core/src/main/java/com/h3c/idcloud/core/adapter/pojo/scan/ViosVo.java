package com.h3c.idcloud.core.adapter.pojo.scan;

public class ViosVo {
    /**
     * vios 的IP地址
     */
    private String viosIp;
    /**
     * vios 用户名
     */
    private String viosUser;
    /**
     * vios 密码
     */
    private String viosPwd;
    /**
     * vios 所属主机名
     */
    private String viosHostName;
    /**
     * vios 所属Lpar的ID
     */
    private String viosId;
    /**
     * vios 所属虚拟机名称
     */
    private String viosName;

    public String getViosIp() {
        return viosIp;
    }

    public void setViosIp(String viosIp) {
        this.viosIp = viosIp;
    }

    public String getViosUser() {
        return viosUser;
    }

    public void setViosUser(String viosUser) {
        this.viosUser = viosUser;
    }

    public String getViosPwd() {
        return viosPwd;
    }

    public void setViosPwd(String viosPwd) {
        this.viosPwd = viosPwd;
    }

    public String getViosHostName() {
        return viosHostName;
    }

    public void setViosHostName(String viosHostName) {
        this.viosHostName = viosHostName;
    }

    public String getViosId() {
        return viosId;
    }

    public void setViosId(String viosId) {
        this.viosId = viosId;
    }

    public String getViosName() {
        return viosName;
    }

    public void setViosName(String viosName) {
        this.viosName = viosName;
    }
}
