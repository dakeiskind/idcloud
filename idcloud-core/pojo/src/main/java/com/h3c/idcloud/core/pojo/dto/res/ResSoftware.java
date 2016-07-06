package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResSoftware implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 软件ID
     */
    private String resSortwareSid;

    /**
     * 软件分类
     */
    private String softwareCategory;

    /**
     * 软件类型
     */
    private String softwareType;

    /**
     * 软件版本
     */
    private String softwareVersion;

    /**
     * 软件操作系统
     */
    private String osType;

    /**
     * 软件操作系统版本
     */
    private String osVersion;

    /**
     * 部署软件操作系统版本
     */
    private String deployOsVersion;

    /**
     * 部署类型
     */
    private String deploymentType;

    /**
     * 介质库地址
     */
    private String mediaLibAddress;

    /**
     * 介质路径
     */
    private String mediaPath;

    /**
     * 脚本库地址
     */
    private String scriptLibAddress;

    /**
     * 脚本路径
     */
    private String scriptPath;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 安装路径
     */
    private String installPath;

    /**
     * 安装用户组
     */
    private String installUserGroup;

    /**
     * 安装用户
     */
    private String installUser;

    /**
     * @return 软件ID
     */
    public String getResSortwareSid() {
        return resSortwareSid;
    }

    /**
     * @param resSortwareSid 软件ID
     */
    public void setResSortwareSid(String resSortwareSid) {
        this.resSortwareSid = resSortwareSid;
    }

    /**
     * @return 软件分类
     */
    public String getSoftwareCategory() {
        return softwareCategory;
    }

    /**
     * @param softwareCategory 软件分类
     */
    public void setSoftwareCategory(String softwareCategory) {
        this.softwareCategory = softwareCategory;
    }

    /**
     * @return 软件类型
     */
    public String getSoftwareType() {
        return softwareType;
    }

    /**
     * @param softwareType 软件类型
     */
    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    /**
     * @return 软件版本
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * @param softwareVersion 软件版本
     */
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    /**
     * @return 软件操作系统
     */
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType 软件操作系统
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeployOsVersion() {
        return deployOsVersion;
    }

    public void setDeployOsVersion(String deployOsVersion) {
        this.deployOsVersion = deployOsVersion;
    }

    /**
     * @return the deploymentType
     */
    public String getDeploymentType() {
        return deploymentType;
    }

    /**
     * @param deploymentType the deploymentType to set
     */
    public void setDeploymentType(String deploymentType) {
        this.deploymentType = deploymentType;
    }

    /**
     * @return 介质库地址
     */
    public String getMediaLibAddress() {
        return mediaLibAddress;
    }

    /**
     * @param mediaLibAddress 介质库地址
     */
    public void setMediaLibAddress(String mediaLibAddress) {
        this.mediaLibAddress = mediaLibAddress;
    }

    /**
     * @return 介质路径
     */
    public String getMediaPath() {
        return mediaPath;
    }

    /**
     * @param mediaPath 介质路径
     */
    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    /**
     * @return 脚本库地址
     */
    public String getScriptLibAddress() {
        return scriptLibAddress;
    }

    /**
     * @param scriptLibAddress 脚本库地址
     */
    public void setScriptLibAddress(String scriptLibAddress) {
        this.scriptLibAddress = scriptLibAddress;
    }

    /**
     * @return 脚本路径
     */
    public String getScriptPath() {
        return scriptPath;
    }

    /**
     * @param scriptPath 脚本路径
     */
    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt 创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the installPath
     */
    public String getInstallPath() {
        return installPath;
    }

    /**
     * @param installPath the installPath to set
     */
    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    /**
     * @return the installUserGroup
     */
    public String getInstallUserGroup() {
        return installUserGroup;
    }

    /**
     * @param installUserGroup the installUserGroup to set
     */
    public void setInstallUserGroup(String installUserGroup) {
        this.installUserGroup = installUserGroup;
    }

    /**
     * @return the installUser
     */
    public String getInstallUser() {
        return installUser;
    }

    /**
     * @param installUser the installUser to set
     */
    public void setInstallUser(String installUser) {
        this.installUser = installUser;
    }

}