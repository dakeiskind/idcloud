/*
 * ts-resource
 * 概要：ResTenant.java
 *
 * 创建人：asdc002
 * 创建日：2015年4月27日
 * 更新履历
 * 2015年4月27日  asdc002  创建
 *
 * @(#)ResTenant.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.dto.res;

import java.util.List;

/**
 * ResTenant.java
 *
 * @author Chaohong.Mao
 */
public class ResTenant {

    /**
     * The Auth url.
     */
    private String authUrl;
    /**
     * 管理用户名
     */
    private String authUser;
    /**
     * 管理用户密码.
     */
    private String authPass;
    /**
     * 租户管理员.
     */
    private String authTenant;
    /**
     * 租户名称.
     */
    private List<Long> tenantId;

    /**
     * 获得 auth url.
     *
     * @return the auth url
     */
    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * 设定 auth url.
     *
     * @param authUrl the auth url
     */
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    /**
     * 获得 auth user.
     *
     * @return the auth user
     */
    public String getAuthUser() {
        return authUser;
    }

    /**
     * 设定 auth user.
     *
     * @param authUser the auth user
     */
    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    /**
     * 获得 auth pass.
     *
     * @return the auth pass
     */
    public String getAuthPass() {
        return authPass;
    }

    /**
     * 设定 auth pass.
     *
     * @param authPass the auth pass
     */
    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    /**
     * 获得 auth tenant.
     *
     * @return the auth tenant
     */
    public String getAuthTenant() {
        return authTenant;
    }

    /**
     * 设定 auth tenant.
     *
     * @param authTenant the auth tenant
     */
    public void setAuthTenant(String authTenant) {
        this.authTenant = authTenant;
    }

    /**
     * 获得 tenant id.
     *
     * @return the tenantId
     */
    public List<Long> getTenantId() {
        return tenantId;
    }

    /**
     * 设定 tenant id.
     *
     * @param tenantId the tenantId to set
     */
    public void setTenantId(List<Long> tenantId) {
        this.tenantId = tenantId;
    }


}
