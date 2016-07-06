/*
 * ts-resource
 * 概要：ResVmSurvey.java
 *
 * 创建人：徐印
 * 创建日：2014-9-23
 * 更新履历
 * 2014-9-23  徐印  创建
 *
 * @(#)ResVmSurvey.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

import java.util.List;

/**
 * ResVmSurvey.java
 *
 * @author 徐印
 */
public class ResVmCheck {

	/**
	 * 虚拟机集合
	 */
	private List<ResVmInst> resVmList;

	/**
	 * 集群ID集合
	 */
	private List<String> allocateResVcIds;

	/**
	 * 主机ID集合
	 */
	private List<String> allocateResHostIds;

	/**
	 * 内网ID集合
	 */
	private List<ResNetworkInst> PNINets;

	/**
	 * 外网ID集合
	 */
	private List<ResNetworkInst> PNENets;

	/**
	 * 获得 res vm list.
	 *
	 * @return 虚拟机集合 res vm list
	 */
	public List<ResVmInst> getResVmList() {
		return resVmList;
	}

	/**
	 * 设定 res vm list.
	 *
	 * @param resVmList the res vm list
	 */
	public void setResVmList(List<ResVmInst> resVmList) {
		this.resVmList = resVmList;
	}

	/**
	 * 获得 allocate res vc ids.
	 *
	 * @return 集群ID集合 allocate res vc ids
	 */
	public List<String> getAllocateResVcIds() {
		return allocateResVcIds;
	}

	/**
	 * 设定 allocate res vc ids.
	 *
	 * @param allocateResVcIds the allocate res vc ids
	 */
	public void setAllocateResVcIds(List<String> allocateResVcIds) {
		this.allocateResVcIds = allocateResVcIds;
	}

	/**
	 * 获得 allocate res host ids.
	 *
	 * @return 主机ID集合 allocate res host ids
	 */
	public List<String> getAllocateResHostIds() {
		return allocateResHostIds;
	}

	/**
	 * 设定 allocate res host ids.
	 *
	 * @param allocateResHostIds the allocate res host ids
	 */
	public void setAllocateResHostIds(List<String> allocateResHostIds) {
		this.allocateResHostIds = allocateResHostIds;
	}

	/**
	 * 获得 pni nets.
	 *
	 * @return 内网ID集合 pni nets
	 */
	public List<ResNetworkInst> getPNINets() {
		return PNINets;
	}

	/**
	 * 设定 pni nets.
	 *
	 * @param pNINets the p ni nets
	 */
	public void setPNINets(List<ResNetworkInst> pNINets) {
		PNINets = pNINets;
	}

	/**
	 * 获得 pne nets.
	 *
	 * @return 外网ID集合 pne nets
	 */
	public List<ResNetworkInst> getPNENets() {
		return PNENets;
	}

	/**
	 * 设定 pne nets.
	 *
	 * @param pNENets the p ne nets
	 */
	public void setPNENets(List<ResNetworkInst> pNENets) {
		PNENets = pNENets;
	}
}
