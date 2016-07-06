/*
 * 惠普云管理平台
 * 概要：虚拟机资源实例定义类
 *
 * 创建人：徐印
 * 创建日：2014-8-26
 * 更新履历
 * 2014-8-26  徐印  创建
 *
 * @(#)ResVdInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

import java.io.Serializable;

/**
 * Res network inst 类.
 *
 * @author 徐印
 */
public class ResNetworkInst implements Serializable {

	/**
	 * 网络资源Id
	 */
	private String resNetworkId;

	/**
	 * 网络资源类型
	 */
	private String resNetworkType;

	/**
	 * IP地址
	 */
	private String ipAddress;

	/**
	 * 操作
	 */
	private String operate;

	/**
	 * 表示是否为主网卡（P:主网卡）
	 */
	private String netPrimary;

	/**
	 * 网络HBA卡
	 */
	private String netHBASid;

	/**
	 * 获得 res network id.
	 *
	 * @return 网络资源Id res network id
	 */
	public String getResNetworkId() {
		return resNetworkId;
	}

	/**
	 * 设定 res network id.
	 *
	 * @param resNetworkId the res network id
	 */
	public void setResNetworkId(String resNetworkId) {
		this.resNetworkId = resNetworkId;
	}

	/**
	 * 获得 res network type.
	 *
	 * @return 网络资源类型 res network type
	 */
	public String getResNetworkType() {
		return resNetworkType;
	}

	/**
	 * 设定 res network type.
	 *
	 * @param resNetworkType the res network type
	 */
	public void setResNetworkType(String resNetworkType) {
		this.resNetworkType = resNetworkType;
	}

	/**
	 * 获得 ip address.
	 *
	 * @return IP地址 ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * 设定 ip address.
	 *
	 * @param ipAddress the ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 获得 operate.
	 *
	 * @return 操作 operate
	 */
	public String getOperate() {
		return operate;
	}

	/**
	 * 设定 operate.
	 *
	 * @param operate the operate
	 */
	public void setOperate(String operate) {
		this.operate = operate;
	}

	/**
	 * 获得 net primary.
	 *
	 * @return the netPrimary
	 */
	public String getNetPrimary() {
		return netPrimary;
	}

	/**
	 * 设定 net primary.
	 *
	 * @param netPrimary the netPrimary to set
	 */
	public void setNetPrimary(String netPrimary) {
		this.netPrimary = netPrimary;
	}

	/**
	 * 获得 net hba sid.
	 *
	 * @return the net hba sid
	 */
	public String getNetHBASid() {
		return netHBASid;
	}

	/**
	 * 设定 net hba sid.
	 *
	 * @param netHBASid the net hba sid
	 */
	public void setNetHBASid(String netHBASid) {
		this.netHBASid = netHBASid;
	}

	
}
