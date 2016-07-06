package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResOsSoftware implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 软件ID
     */
    private String resSoftwareSid;

    /**
     * 资源ID
     */
    private String resSid;

    /**
     * 资源类型
     */
    private String resType;

    /**
     * 软件分类
     */
    private String softwareCategory;

    /**
     * 软件分类名称
     */
    private String softwareCategoryName;

    /**
     * 软件类型
     */
    private String softwareType;

    /**
     * 软件类型名称
     */
    private String softwareTypeName;

    /**
     * 软件版本
     */
    private String softwareVersion;

    /**
     * 软件版本名称
     */
    private String softwareVersionName;

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
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * UUID
     */
    private String uuid;

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
     * 是否能自动部署
     */
    private Boolean canAutoDeploy;

    /**
     * @return 软件ID
     */
    public String getResSoftwareSid() {
        return resSoftwareSid;
    }

    /**
     * @param resSoftwareSid 软件ID
     */
    public void setResSoftwareSid(String resSoftwareSid) {
        this.resSoftwareSid = resSoftwareSid;
    }

    /**
     * @return 资源ID
     */
    public String getResSid() {
        return resSid;
    }

    /**
     * @param resSid 资源ID
     */
    public void setResSid(String resSid) {
        this.resSid = resSid;
    }

    /**
     * @return 资源类型
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resType 资源类型
     */
    public void setResType(String resType) {
        this.resType = resType;
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
     * @return 安装路径
     */
    public String getInstallPath() {
        return installPath;
    }

    /**
     * @param installPath 安装路径
     */
    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    /**
     * @return 安装用户组
     */
    public String getInstallUserGroup() {
        return installUserGroup;
    }

    /**
     * @param installUserGroup 安装用户组
     */
    public void setInstallUserGroup(String installUserGroup) {
        this.installUserGroup = installUserGroup;
    }

    /**
     * @return 安装用户
     */
    public String getInstallUser() {
        return installUser;
    }

    /**
     * @param installUser 安装用户
     */
    public void setInstallUser(String installUser) {
        this.installUser = installUser;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * @return the softwareCategoryName
     */
    public String getSoftwareCategoryName() {
        return softwareCategoryName;
    }

    /**
     * @param softwareCategoryName the softwareCategoryName to set
     */
    public void setSoftwareCategoryName(String softwareCategoryName) {
        this.softwareCategoryName = softwareCategoryName;
    }

    /**
     * @return the softwareTypeName
     */
    public String getSoftwareTypeName() {
        return softwareTypeName;
    }

    /**
     * @param softwareTypeName the softwareTypeName to set
     */
    public void setSoftwareTypeName(String softwareTypeName) {
        this.softwareTypeName = softwareTypeName;
    }

    /**
     * @return the softwareVersionName
     */
    public String getSoftwareVersionName() {
        return softwareVersionName;
    }

    /**
     * @param softwareVersionName the softwareVersionName to set
     */
    public void setSoftwareVersionName(String softwareVersionName) {
        this.softwareVersionName = softwareVersionName;
    }

    public Boolean getCanAutoDeploy() {
        return canAutoDeploy;
    }

    public void setCanAutoDeploy(Boolean canAutoDeploy) {
        this.canAutoDeploy = canAutoDeploy;
    }

}