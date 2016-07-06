/*
 * ts-resource
 * 概要：LincenseResult.java
 *
 * 创建人：徐印
 * 创建日：2015-1-26
 * 更新履历
 * 2015-1-26  徐印  创建
 *
 * @(#)LincenseResult.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

import java.io.Serializable;

/**
 * LincenseResult.java
 *
 * @author yxu
 */
public class LicenseResult implements Serializable {

	/** 有效 */
	public static final boolean VALID = true;

	/** 无效 */
	public static final boolean INVALID = false;
	
	/**
	 *  Lincense是否有效
	 */
	public boolean isValid;
	
	/**
	 * 信息返回
	 */
	public Object message;

	public LicenseResult() {
	}

	public LicenseResult(boolean isValid, Object message) {
		this.isValid = isValid;
		this.message = message;
	}

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the message
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Object message) {
		this.message = message;
	}
}
