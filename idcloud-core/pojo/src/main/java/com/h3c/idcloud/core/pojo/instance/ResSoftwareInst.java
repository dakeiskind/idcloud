/*
 * ts-resource
 * 概要：ResSoftwareInst.java
 *
 * 创建人：yxu
 * 创建日：2015-6-12
 * 更新履历
 * 2015-6-12  yxu  创建
 *
 * @(#)ResSoftwareInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

/**
 * ResSoftwareInst.java
 *
 * @author yxu
 */
public class ResSoftwareInst {

	/**
	 * 虚拟化环境Sid
	 */
	private String resVeSid;

	/**
	 * 镜像类型
	 */
	private String osType;

	/**
	 * 镜像版本
	 */
	private String osVersion;

	/**
	 * 软件类型（格式 "softwareType": "S01,S02"）
	 */
	private String softwareType;

	/**
	 * 软件版本（格式 "softwareVersion": "S010101,S020101"）
	 */
	private String softwareVersion;

	/**
	 * 获得 res ve sid.
	 *
	 * @return the res ve sid
	 */
	public String getResVeSid() {
		return resVeSid;
	}

	/**
	 * 设定 res ve sid.
	 *
	 * @param resVeSid the res ve sid
	 */
	public void setResVeSid(String resVeSid) {
		this.resVeSid = resVeSid;
	}

	/**
	 * 获得 os type.
	 *
	 * @return the os type
	 */
	public String getOsType() {
		return osType;
	}

	/**
	 * 设定 os type.
	 *
	 * @param osType the os type
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}

	/**
	 * 获得 os version.
	 *
	 * @return the os version
	 */
	public String getOsVersion() {
		return osVersion;
	}

	/**
	 * 设定 os version.
	 *
	 * @param osVersion the os version
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * 获得 software type.
	 *
	 * @return the software type
	 */
	public String getSoftwareType() {
		return softwareType;
	}

	/**
	 * 设定 software type.
	 *
	 * @param softwareType the software type
	 */
	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}

	/**
	 * 获得 software version.
	 *
	 * @return the software version
	 */
	public String getSoftwareVersion() {
		return softwareVersion;
	}

	/**
	 * 设定 software version.
	 *
	 * @param softwareVersion the software version
	 */
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	
}
