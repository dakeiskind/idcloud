/*
 * ts-resource
 * 概要：ResFloatingIpInst.java
 *
 * 创建人：徐印
 * 创建日：2015-3-19
 * 更新履历
 * 2015-3-19  徐印  创建
 *
 * @(#)ResFloatingIpInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

import java.util.List;

/**
 * ResFloatingIpInst.java
 *
 * @author 徐印
 */
public class ResFloatingIpInst {

	/**
	 * 浮动IpSid
	 */
	private String resSid;
	
	/**
	 * 关联内网IPSid
	 */
	private String mappingIpSid;
	
	/**
	 * 关联虚拟机Sid
	 */
	private String mappingVmSid;
	
	/**
	 * 租户Sid
	 */
	private long mgtObjSid;
	

	/**
	 * 网络Sid
	 */
	private List<String> resNetSids;

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

	/**
	 * @return the resSid
	 */
	public String getResSid() {
		return resSid;
	}

	/**
	 * @param resSid the resSid to set
	 */
	public void setResSid(String resSid) {
		this.resSid = resSid;
	}


	/**
	 * @return the mappingIpSid
	 */
	public String getMappingIpSid() {
		return mappingIpSid;
	}

	/**
	 * @param mappingIpSid the mappingIpSid to set
	 */
	public void setMappingIpSid(String mappingIpSid) {
		this.mappingIpSid = mappingIpSid;
	}

	/**
	 * @return the mappingVmSid
	 */
	public String getMappingVmSid() {
		return mappingVmSid;
	}

	/**
	 * @param mappingVmSid the mappingVmSid to set
	 */
	public void setMappingVmSid(String mappingVmSid) {
		this.mappingVmSid = mappingVmSid;
	}

	/**
	 * @return the resNetSids
	 */
	public List<String> getResNetSids() {
		return resNetSids;
	}

	/**
	 * @param resNetSids the resNetSids to set
	 */
	public void setResNetSids(List<String> resNetSids) {
		this.resNetSids = resNetSids;
	}
	
}
