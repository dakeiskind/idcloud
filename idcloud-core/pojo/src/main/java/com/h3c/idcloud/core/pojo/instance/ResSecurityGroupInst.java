/*
 * ts-resource
 * 概要：ResSecurityGroupInst.java
 *
 * 创建人：徐印
 * 创建日：2015-3-27
 * 更新履历
 * 2015-3-27  徐印  创建
 *
 * @(#)ResSecurityGroupInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

/**
 * ResSecurityGroupInst.java
 *
 * @author 徐印
 */
public class ResSecurityGroupInst {

	/**
	 * 安全组名称
	 */
	private String securityGourpName;
	
	/**
	 * 安全组描述
	 */
	private String description;
	
	/**
	 * 管理对象Sid
	 */
	private long mgtObjSid;

	/**
	 * @return the securityGourpName
	 */
	public String getSecurityGourpName() {
		return securityGourpName;
	}

	/**
	 * @param securityGourpName the securityGourpName to set
	 */
	public void setSecurityGourpName(String securityGourpName) {
		this.securityGourpName = securityGourpName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mgtObjSid
	 */
	public long getMgtObjSid() {
		return mgtObjSid;
	}

	/**
	 * @param mgtObjSid the mgtObjSid to set
	 */
	public void setMgtObjSid(long mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}
	
	
}
