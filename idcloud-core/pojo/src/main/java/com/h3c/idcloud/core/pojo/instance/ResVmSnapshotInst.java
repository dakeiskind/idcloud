/*
 * ts-resource
 * 概要：ResVmSnapShotInst.java
 *
 * 创建人：徐印
 * 创建日：2015-3-19
 * 更新履历
 * 2015-3-19  徐印  创建
 *
 * @(#)ResVmSnapShotInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

/**
 * ResVmSnapShotInst.java
 *
 * @author 徐印
 */
public class ResVmSnapshotInst {

	/**
	 * 虚拟机Sid
	 */
	private String resVmSid;
	
	/**
	 * 快照名称
	 */
	private String snapshotName;
	
	/**
	 * 租户Id
	 */
	private String tenantId;

	/**
	 * @return the resVmSid
	 */
	public String getResVmSid() {
		return resVmSid;
	}

	/**
	 * @param resVmSid the resVmSid to set
	 */
	public void setResVmSid(String resVmSid) {
		this.resVmSid = resVmSid;
	}

	/**
	 * @return the snapshotName
	 */
	public String getSnapshotName() {
		return snapshotName;
	}

	/**
	 * @param snapshotName the snapshotName to set
	 */
	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
