/*
 * ts-resource
 * 概要：ResReconfigInst.java
 *
 * 创建人：徐印
 * 创建日：2014-9-17
 * 更新履历
 * 2014-9-17  徐印  创建
 *
 * @(#)ResReconfigInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;

import java.util.List;

/**
 * ResReconfigInst.java
 *
 * @author 徐印
 */
public class ResReconfigVmInst {

	/**
	 * CPU核数
	 */
	private int cpu;

	/**
	 * 内存大小(MB)
	 */
	private long memory;

	/**
	 * 虚拟机磁盘列表
	 */
	private List<ResVdInst> dataDisks;

	/**
	 * 虚拟机网络列表
	 */
	private List<ResNetworkInst> nets;

	/**
	 * 获得 cpu.
	 *
	 * @return CPU核数 cpu
	 */
	public int getCpu() {
		return cpu;
	}

	/**
	 * 设定 cpu.
	 *
	 * @param cpu the cpu
	 */
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	/**
	 * 获得 memory.
	 *
	 * @return 内存大小(MB) memory
	 */
	public long getMemory() {
		return memory;
	}

	/**
	 * 设定 memory.
	 *
	 * @param memory the memory
	 */
	public void setMemory(long memory) {
		this.memory = memory;
	}

	/**
	 * 获得 data disks.
	 *
	 * @return 虚拟机磁盘列表 data disks
	 */
	public List<ResVdInst> getDataDisks() {
		return dataDisks;
	}

	/**
	 * 设定 data disks.
	 *
	 * @param dataDisks the data disks
	 */
	public void setDataDisks(List<ResVdInst> dataDisks) {
		this.dataDisks = dataDisks;
	}

	/**
	 * 获得 nets.
	 *
	 * @return 虚拟机网络列表 nets
	 */
	public List<ResNetworkInst> getNets() {
		return nets;
	}

	/**
	 * 设定 nets.
	 *
	 * @param nets the nets
	 */
	public void setNets(List<ResNetworkInst> nets) {
		this.nets = nets;
	}

}
