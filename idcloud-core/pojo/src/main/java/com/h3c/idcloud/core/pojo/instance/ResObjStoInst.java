/*
 * ts-resource
 * 概要：ResObjStorageInst.java
 *
 * 创建人：徐印
 * 创建日：2015-3-26
 * 更新履历
 * 2015-3-26  徐印  创建
 *
 * @(#)ResObjStorageInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

/**
 * ResObjStorageInst.java
 *
 * @author 徐印
 */
public class ResObjStoInst {

	/**
	 * 对象存储Sid
	 */
	private String resOsSid;
	
	/**
	 * 管理对象Sid
	 */
	private long mgtObjSid;
	
	/**
	 * 申请人账户
	 */
	private String account;
	
	/**
	 * 容量
	 */
	private long capacity;

	/**
	 * @return the resOsSid
	 */
	public String getResOsSid() {
		return resOsSid;
	}

	/**
	 * @param resOsSid the resOsSid to set
	 */
	public void setResOsSid(String resOsSid) {
		this.resOsSid = resOsSid;
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

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the capacity
	 */
	public long getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
}
