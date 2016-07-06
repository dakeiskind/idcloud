/*
 * ts-resource
 * 概要：VmSystemUser.java
 *
 * 创建人：yxu
 * 创建日：2015-6-3
 * 更新履历
 * 2015-6-3  yxu  创建
 *
 * @(#)VmSystemUser.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

/**
 * VmSystemUser.java
 *
 * @author yxu
 */
public class VmUser {

    /**
     * 虚拟机用户名
     */
    private String userName;

    /**
     * 虚拟机用户密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
