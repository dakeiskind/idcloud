package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResVe extends ResTopology implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询资源名称
     */
    private String resTopologyNameLike;

    /**
     * 虚拟平台类型
     */
    private String virtualPlatformType;

    /**
     * 虚拟平台版本
     */
    private String virtualPlatformVersion;

    /**
     * 虚拟平台版本
     */
    private String virtualPlatformVersionName;

    /**
     * Admin管理地址
     */
    private String adminManagementUrl;
    /**
     * 管理地址
     */
    private String managementUrl;

    /**
     * 管理租户帐号
     */
    private String managementTenant;

    /**
     * 管理用户帐号
     */
    private String managementAccount;

    /**
     * 管理用户帐号
     */
    private String managementAccountLike;

    /**
     * 管理用户密码
     */
    private String managementPassword;

    /**
     * 连接状态
     */
    private String connectStatus;

    /**
     * 连接状态名称
     */
    private String connectStatusName;

    /**
     * 更新状态
     */
    private String updateStatus;

    /**
     * 更新状态
     */
    private String updateStatusName;

    /**
     * 更新周期
     */
    private String updateCycle;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 消息主题
     */
    private String mqTopic;


    /**
     * 消息主题
     */
    private String taskId;

    /**
     * 消息主题
     */
    private String account;

    /**
     * 同步HMC模板ip
     */
    private String nimIp;

    /**
     * 同步HMC模板用户名
     */
    private String nimUser;

    /**
     * 同步HMC模板密码
     */
    private String nimPassword;

    public String getAdminManagementUrl() {
        return adminManagementUrl;
    }

    public void setAdminManagementUrl(String adminManagementUrl) {
        this.adminManagementUrl = adminManagementUrl;
    }

    /**
     * @return 虚拟平台类型
     */
    public String getVirtualPlatformType() {
        return virtualPlatformType;
    }

    /**
     * @param virtualPlatformType 虚拟平台类型
     */
    public void setVirtualPlatformType(String virtualPlatformType) {
        this.virtualPlatformType = virtualPlatformType;
    }

    /**
     * @return 虚拟平台版本
     */
    public String getVirtualPlatformVersion() {
        return virtualPlatformVersion;
    }

    /**
     * @param virtualPlatformVersion 虚拟平台版本
     */
    public void setVirtualPlatformVersion(String virtualPlatformVersion) {
        this.virtualPlatformVersion = virtualPlatformVersion;
    }

    /**
     * @return 管理地址
     */
    public String getManagementUrl() {
        return managementUrl;
    }

    /**
     * @param managementUrl 管理地址
     */
    public void setManagementUrl(String managementUrl) {
        this.managementUrl = managementUrl;
    }

    /**
     * @return 管理用户帐号
     */
    public String getManagementAccount() {
        return managementAccount;
    }

    /**
     * @param managementAccount 管理用户帐号
     */
    public void setManagementAccount(String managementAccount) {
        this.managementAccount = managementAccount;
    }

    /**
     * @return 管理用户密码
     */
    public String getManagementPassword() {
        return managementPassword;
    }

    /**
     * @param managementPassword 管理用户密码
     */
    public void setManagementPassword(String managementPassword) {
        this.managementPassword = managementPassword;
    }

    /**
     * @return 连接状态
     */
    public String getConnectStatus() {
        return connectStatus;
    }

    /**
     * @param connectStatus 连接状态
     */
    public void setConnectStatus(String connectStatus) {
        this.connectStatus = connectStatus;
    }

    /**
     * @return 更新状态
     */
    public String getUpdateStatus() {
        return updateStatus;
    }

    /**
     * @param updateStatus 更新状态
     */
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    /**
     * @return 更新周期
     */
    public String getUpdateCycle() {
        return updateCycle;
    }

    /**
     * @param updateCycle 更新周期
     */
    public void setUpdateCycle(String updateCycle) {
        this.updateCycle = updateCycle;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 消息主题
     */
    public String getMqTopic() {
        return mqTopic;
    }

    /**
     * @param mqTopic 消息主题
     */
    public void setMqTopic(String mqTopic) {
        this.mqTopic = mqTopic;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getResTopologyNameLike() {
        return resTopologyNameLike;
    }

    public void setResTopologyNameLike(String resTopologyNameLike) {
        this.resTopologyNameLike = resTopologyNameLike;
    }

    public String getVirtualPlatformVersionName() {
        return virtualPlatformVersionName;
    }

    public void setVirtualPlatformVersionName(String virtualPlatformVersionName) {
        this.virtualPlatformVersionName = virtualPlatformVersionName;
    }

    public String getConnectStatusName() {
        return connectStatusName;
    }

    public void setConnectStatusName(String connectStatusName) {
        this.connectStatusName = connectStatusName;
    }

    public String getUpdateStatusName() {
        return updateStatusName;
    }

    public void setUpdateStatusName(String updateStatusName) {
        this.updateStatusName = updateStatusName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the nimIp
     */
    public String getNimIp() {
        return nimIp;
    }

    /**
     * @param nimIp the nimIp to set
     */
    public void setNimIp(String nimIp) {
        this.nimIp = nimIp;
    }

    /**
     * @return the nimUser
     */
    public String getNimUser() {
        return nimUser;
    }

    /**
     * @param nimUser the nimUser to set
     */
    public void setNimUser(String nimUser) {
        this.nimUser = nimUser;
    }

    /**
     * @return the nimPassword
     */
    public String getNimPassword() {
        return nimPassword;
    }

    /**
     * @param nimPassword the nimPassword to set
     */
    public void setNimPassword(String nimPassword) {
        this.nimPassword = nimPassword;
    }

    /**
     * @return the managementAccountLike
     */
    public String getManagementAccountLike() {
        return managementAccountLike;
    }

    /**
     * @param managementAccountLike the managementAccountLike to set
     */
    public void setManagementAccountLike(String managementAccountLike) {
        this.managementAccountLike = managementAccountLike;
    }

    public String getManagementTenant() {
        return managementTenant;
    }

    public void setManagementTenant(String managementTenant) {
        this.managementTenant = managementTenant;
    }
}