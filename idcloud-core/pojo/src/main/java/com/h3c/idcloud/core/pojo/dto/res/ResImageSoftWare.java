/*
 * ts-resource
 * 概要：ResImageSoftWare.java
 *
 * 创建人：jianweil
 * 创建日：2015年6月3日
 * 更新履历
 * 2015年6月3日  jianweil  创建
 *
 * @(#)ResImageSoftWare.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.dto.res;

import java.util.List;

/**
 * ResImageSoftWare.java
 *
 * @author jianweil
 */
public class ResImageSoftWare {

    //资源环境Sid
    private String resVeSid;

    //镜像Sid
    private String imageSid;

    //镜像UUID
    private String imageUUID;

    //镜像名称
    private String imageName;

    //操作系统
    private String osVersion;

    //操作系统类型
    private String osType;

    //匹配后包含的软件版本
    private String includeSoftwareVersion;

    //匹配后没有包含的软件的版本
    private String withoutSoftwareVersion;

    //软件列表
    private List<ResOsSoftware> softwares;


    /**
     * @return the resVeSid
     */
    public String getResVeSid() {
        return resVeSid;
    }

    /**
     * @param resVeSid the resVeSid to set
     */
    public void setResVeSid(String resVeSid) {
        this.resVeSid = resVeSid;
    }

    /**
     * @return the imageSid
     */
    public String getImageSid() {
        return imageSid;
    }

    /**
     * @param imageSid the imageSid to set
     */
    public void setImageSid(String imageSid) {
        this.imageSid = imageSid;
    }

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    /**
     * @return the osType
     */
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @return the imageUUID
     */
    public String getImageUUID() {
        return imageUUID;
    }

    /**
     * @param imageUUID the imageUUID to set
     */
    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }


//	/**
//	 * @return the softwareVersion
//	 */
//	public List<String> getSoftwareVersion() {
//		return softwareVersion;
//	}
//
//	/**
//	 * @param softwareVersion the softwareVersion to set
//	 */
//	public void setSoftwareVersion(List<String> softwareVersion) {
//		this.softwareVersion = softwareVersion;
//	}

    public List<ResOsSoftware> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<ResOsSoftware> softwares) {
        this.softwares = softwares;
    }

    /**
     * @return the includeSoftwareVersion
     */
    public String getIncludeSoftwareVersion() {
        return includeSoftwareVersion;
    }

    /**
     * @param includeSoftwareVersion the includeSoftwareVersion to set
     */
    public void setIncludeSoftwareVersion(String includeSoftwareVersion) {
        this.includeSoftwareVersion = includeSoftwareVersion;
    }

    /**
     * @return the withoutSoftwareVersion
     */
    public String getWithoutSoftwareVersion() {
        return withoutSoftwareVersion;
    }

    /**
     * @param withoutSoftwareVersion the withoutSoftwareVersion to set
     */
    public void setWithoutSoftwareVersion(String withoutSoftwareVersion) {
        this.withoutSoftwareVersion = withoutSoftwareVersion;
    }
}
