package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResImage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源实例ID
     */
    private String resImageSid;

    /**
     * 镜像ID
     */
    private String imageId;

    /**
     * UUID
     */
    private String uuid;

    private Long mgtObjSid;

    /**
     * 镜像名称
     */
    private String imageName;
    private String imageNameLike;

    /**
     * 镜像类型
     */
    private String imageType;
    private String imageTypeName;

    /**
     * 所属虚拟化环境
     */
    private String resVeSid;
    private String resVeSids;

    /**
     * 所属虚拟化环境名称
     */
    private String resTopologyName;

    /**
     * 镜像大小(GB)
     */
    private Float imageSize;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 操作系统版本
     */
    private String osVersion;
    private String osVersionName;

    /**
     * 分配主机ID
     */
    private String allocateResHostSid;

    /**
     * 存储资源ID
     */
    private String allocateResStorageSid;

    /**
     * 主机管理帐号
     */
    private String managementAccount;

    /**
     * 主机管理密码
     */
    private String managementPassword;

    /**
     * 状态
     */
    private String status;

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

    private String imageStatus;
    /**
     * 虚拟化环境名称
     */
    private String resTopologyNameVE;
    private String resTopologySid;
    /**
     * 数据中心名称
     */
    private String resTopologyNameDC;
    /**
     * 区域名称
     */
    private String resTopologyNameR;

    /**
     * 主机名称
     */
    private String allocateResHostName;

    /**
     * 安装软件
     */
    private String installedSoftware;
    private String softwareType;
    private String softwareVersion;
    private String resVeSidTp;

    /**
     * @return 资源实例ID
     */
    public String getResImageSid() {
        return resImageSid;
    }

    /**
     * @param resImageSid 资源实例ID
     */
    public void setResImageSid(String resImageSid) {
        this.resImageSid = resImageSid;
    }

    /**
     * @return 镜像ID
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * @param imageId 镜像ID
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     * @return 镜像名称
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName 镜像名称
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return 镜像类型
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * @param imageType 镜像类型
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     * @return 所属虚拟化环境
     */
    public String getResVeSid() {
        return resVeSid;
    }

    /**
     * @param resVeSid 所属虚拟化环境
     */
    public void setResVeSid(String resVeSid) {
        this.resVeSid = resVeSid;
    }

    /**
     * @return 镜像大小(GB)
     */
    public Float getImageSize() {
        return imageSize;
    }

    /**
     * @param imageSize 镜像大小(GB)
     */
    public void setImageSize(Float imageSize) {
        this.imageSize = imageSize;
    }

    /**
     * @return 操作系统类型
     */
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType 操作系统类型
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @return 操作系统版本
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * @param osVersion 操作系统版本
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * @return 分配主机ID
     */
    public String getAllocateResHostSid() {
        return allocateResHostSid;
    }

    /**
     * @param allocateResHostSid 分配主机ID
     */
    public void setAllocateResHostSid(String allocateResHostSid) {
        this.allocateResHostSid = allocateResHostSid;
    }

    /**
     * @return 存储资源ID
     */
    public String getAllocateResStorageSid() {
        return allocateResStorageSid;
    }

    /**
     * @param allocateResStorageSid 存储资源ID
     */
    public void setAllocateResStorageSid(String allocateResStorageSid) {
        this.allocateResStorageSid = allocateResStorageSid;
    }

    /**
     * @return 主机管理帐号
     */
    public String getManagementAccount() {
        return managementAccount;
    }

    /**
     * @param managementAccount 主机管理帐号
     */
    public void setManagementAccount(String managementAccount) {
        this.managementAccount = managementAccount;
    }

    /**
     * @return 主机管理密码
     */
    public String getManagementPassword() {
        return managementPassword;
    }

    /**
     * @param managementPassword 主机管理密码
     */
    public void setManagementPassword(String managementPassword) {
        this.managementPassword = managementPassword;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getImageNameLike() {
        return imageNameLike;
    }

    public void setImageNameLike(String imageNameLike) {
        this.imageNameLike = imageNameLike;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getResTopologyName() {
        return resTopologyName;
    }

    public void setResTopologyName(String resTopologyName) {
        this.resTopologyName = resTopologyName;
    }

    public String getResTopologyNameVE() {
        return resTopologyNameVE;
    }

    public void setResTopologyNameVE(String resTopologyNameVE) {
        this.resTopologyNameVE = resTopologyNameVE;
    }

    public String getResTopologyNameDC() {
        return resTopologyNameDC;
    }

    public void setResTopologyNameDC(String resTopologyNameDC) {
        this.resTopologyNameDC = resTopologyNameDC;
    }

    public String getResTopologyNameR() {
        return resTopologyNameR;
    }

    public void setResTopologyNameR(String resTopologyNameR) {
        this.resTopologyNameR = resTopologyNameR;
    }

    public String getAllocateResHostName() {
        return allocateResHostName;
    }

    public void setAllocateResHostName(String allocateResHostName) {
        this.allocateResHostName = allocateResHostName;
    }

    public String getImageTypeName() {
        return imageTypeName;
    }

    public void setImageTypeName(String imageTypeName) {
        this.imageTypeName = imageTypeName;
    }

    public String getOsVersionName() {
        return osVersionName;
    }

    public void setOsVersionName(String osVersionName) {
        this.osVersionName = osVersionName;
    }

    public String getResTopologySid() {
        return resTopologySid;
    }

    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    /**
     * @return the installedSoftware
     */
    public String getInstalledSoftware() {
        return installedSoftware;
    }

    /**
     * @param installedSoftware the installedSoftware to set
     */
    public void setInstalledSoftware(String installedSoftware) {
        this.installedSoftware = installedSoftware;
    }

    /**
     * @return the softwareType
     */
    public String getSoftwareType() {
        return softwareType;
    }

    /**
     * @param softwareType the softwareType to set
     */
    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    /**
     * @return the softwareVersion
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * @param softwareVersion the softwareVersion to set
     */
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    /**
     * @return the resVeSids
     */
    public String getResVeSids() {
        return resVeSids;
    }

    /**
     * @param resVeSids the resVeSids to set
     */
    public void setResVeSids(String resVeSids) {
        this.resVeSids = resVeSids;
    }

    /**
     * @return the resVeSidTp
     */
    public String getResVeSidTp() {
        return resVeSidTp;
    }

    /**
     * @param resVeSidTp the resVeSidTp to set
     */
    public void setResVeSidTp(String resVeSidTp) {
        this.resVeSidTp = resVeSidTp;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }
}