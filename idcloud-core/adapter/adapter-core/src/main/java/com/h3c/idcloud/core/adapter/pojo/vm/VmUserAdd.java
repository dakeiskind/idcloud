package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmUserAdd extends Base {
    private String serverIP; //目标虚拟机IP
    private String serverUser; //目标虚拟机root用户
    private String serverPwd; //目标虚拟机root密码
    private String nonRootUser;//需要添加的非root用户
    private String nonRootPwd;//添加非root用户密码
    private String osType;//目标机操作系统类型。支持linux、aix

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerUser() {
        return serverUser;
    }

    public void setServerUser(String serverUser) {
        this.serverUser = serverUser;
    }

    public String getServerPwd() {
        return serverPwd;
    }

    public void setServerPwd(String serverPwd) {
        this.serverPwd = serverPwd;
    }

    public String getNonRootUser() {
        return nonRootUser;
    }

    public void setNonRootUser(String nonRootUser) {
        this.nonRootUser = nonRootUser;
    }

    public String getNonRootPwd() {
        return nonRootPwd;
    }

    public void setNonRootPwd(String nonRootPwd) {
        this.nonRootPwd = nonRootPwd;
    }
}
