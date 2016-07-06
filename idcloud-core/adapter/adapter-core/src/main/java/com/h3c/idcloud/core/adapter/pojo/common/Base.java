package com.h3c.idcloud.core.adapter.pojo.common;

import java.util.Map;
import java.util.UUID;

public class Base {

    private String authUrl;
    private String authTenant;//租户
    private String authUser;//用户
    private String authPass;//密码

    private String providerUrl;//pointPort url
    private String tenantUserName;//
    private String tenantUserId;//
    private String tenantUserPass;//
    private String tenantName;//
    private String tenantId;

    private String providerType;
    private String virtEnvType;
    private String virtEnvUuid;
    private Map<String, Object> options;//自定义参数


    //for jclouds
    private String availabilityZone;
    private String region;
    private String endpoint;
    private String msgId = UUID.randomUUID().toString();

    public String getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(String tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getTenantUserName() {
        return tenantUserName;
    }

    public void setTenantUserName(String tenantUserName) {
        this.tenantUserName = tenantUserName;
    }

    public String getTenantUserPass() {
        return tenantUserPass;
    }

    public void setTenantUserPass(String tenantUserPass) {
        this.tenantUserPass = tenantUserPass;
    }

    public String getAuthTenant() {
        return authTenant;
    }

    public void setAuthTenant(String authTenant) {
        this.authTenant = authTenant;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getVirtEnvType() {
        return virtEnvType;
    }

    public void setVirtEnvType(String virtEnvType) {
        this.virtEnvType = virtEnvType;
    }

    public String getVirtEnvUuid() {
        return virtEnvUuid;
    }

    public void setVirtEnvUuid(String virtEnvUuid) {
        this.virtEnvUuid = virtEnvUuid;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAuthUser() {
        return authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getAuthPass() {
        return authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
        //this.tenantId = "81e043e3856442d89fa7813cea57c530";
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "Base{" +
                "authUrl='" + authUrl + '\'' +
                ", authTenant='" + authTenant + '\'' +
                ", authUser='" + authUser + '\'' +
                ", authPass='" + authPass + '\'' +
                ", providerUrl='" + providerUrl + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", tenantUserName='" + tenantUserName + '\'' +
                ", tenantUserPass='" + tenantUserPass + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", providerType='" + providerType + '\'' +
                ", virtEnvType='" + virtEnvType + '\'' +
                ", virtEnvUuid='" + virtEnvUuid + '\'' +
                ", options=" + options +
                ", availabilityZone='" + availabilityZone + '\'' +
                ", region='" + region + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
