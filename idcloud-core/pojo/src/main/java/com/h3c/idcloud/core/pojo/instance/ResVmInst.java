/*
 * 惠普云管理平台
 * 概要：虚拟机资源实例定义类
 *
 * 创建人：徐印
 * 创建日：2014-8-25
 * 更新履历
 * 2014-8-25  徐印  创建
 *
 * @(#)ResVmInst.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.instance;


import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;

import java.io.Serializable;
import java.util.List;

/**
 * 虚拟机资源实例定义类
 *
 * @author 徐印
 */
public class ResVmInst extends ServiceBaseInput implements Serializable {

	/**
	 * 虚拟机Id
	 */
	private String resVmInstId;

	/**
	 * 虚拟机名称
	 */
	private String resVmInstName;

	/**
	 * 虚拟机名称
	 */
	private String vmSystemName;

	/**
	 * 虚拟机名称前缀
	 */
	private String resVmInstNamePrefix;

	/**
	 * VMware-CPU核数/物理分区-物理CPU数/虚拟分区-虚拟CPU数
	 */
	private int cpu;

	/**
	 * VMware-未使用/物理分区-物理CPU最小核数/虚拟分区-虚拟CPU最小核数
	 */
	private int cpuMin;

	/**
	 * VMware-未使用/物理分区-物理CPU最大核数/虚拟分区-虚拟CPU最大核数
	 */
	private int cpuMax;

	/**
	 * 内存大小(MB)
	 */
	private long memory;

	/**
	 * 内存最小值(MB)
	 */
	private long memoryMin;

	/**
	 * 内存最大值(MB)
	 */
	private long memoryMax;

	/**
	 * VMware-未使用/物理分区-未使用/虚拟分区-物理CPU
	 */
	private float powerCpuUsedUnits;

	/**
	 * 区域
	 */
	private String region;

	/**
	 * 安全组
	 */
	private String securityGroup;

	/**
	 * VMware-未使用/物理分区-未使用/虚拟分区-物理最小CPU
	 */
	private float powerCpuMinUnits;

	/**
	 * VMware-未使用/物理分区-未使用/虚拟分区-物理最大CPU
	 */
	private float powerCpuMaxUnits;

	/**
	 * 镜像ID
	 */
	private String imageSid;

	/**
	 * 镜像版本
	 */
	private String osVersion;

	/**
	 * 镜像版本
	 */
	private String osType;

	/**
	 * 软件列表
	 */
	private List<ResOsSoftware> softwares;


	/**
	 * 初始管理用户
	 */
	private String managementAccount;

	/**
	 * 初始管理密码
	 */
	private String managementPassword;

	/**
	 * 密钥对
	 */
	private String keyPair;

	/**
	 * 分配主机资源ID
	 */
	private List<String> allocateResHostIds;

	/**
	 * 分配资源集群ID
	 */
	private List<String> allocateResVcIds;

	/**
	 * 虚拟机磁盘列表
	 */
	private List<ResVdInst> dataDisks;

	/**
	 * 虚拟机网络列表
	 */
	private List<ResNetworkInst> nets;

	/**
	 * 虚拟机用户
	 */
	private List<VmUser> vmUsers;

	/**
	 * Power分区类型
	 * 1：Mpar；0：Lpar
	 */
	private String partitionType;

	/**
	 * Power主机 CPU资源池ID
	 */
	private String cpuPoolSid;

	/**
	 * 虚拟交换机Sid
	 */
	private String virtualSwitchSid;

	/**
	 * 存储光纤卡的id
	 */
	private String stHbaCardSids;

	/**
	 * 获得 st hba card sids.
	 *
	 * @return the st hba card sids
	 */
	public String getStHbaCardSids() {
		return stHbaCardSids;
	}

	/**
	 * 设定 st hba card sids.
	 *
	 * @param stHbaCardSids the st hba card sids
	 */
	public void setStHbaCardSids(String stHbaCardSids) {
		this.stHbaCardSids = stHbaCardSids;
	}

	/**
	 * 获得 res vm inst id.
	 *
	 * @return 虚拟机Id res vm inst id
	 */
	public String getResVmInstId() {
		return resVmInstId;
	}

	/**
	 * 设定 res vm inst id.
	 *
	 * @param resVmInstId the res vm inst id
	 */
	public void setResVmInstId(String resVmInstId) {
		this.resVmInstId = resVmInstId;
	}

	/**
	 * 获得 res vm inst name.
	 *
	 * @return 用户定义虚拟机名称 res vm inst name
	 */
	public String getResVmInstName() {
		return resVmInstName;
	}

	/**
	 * 设定 res vm inst name.
	 *
	 * @param resVmInstName the res vm inst name
	 */
	public void setResVmInstName(String resVmInstName) {
		this.resVmInstName = resVmInstName;
	}

	/**
	 * 获得 vm system name.
	 *
	 * @return the vm system name
	 */
	public String getVmSystemName() {
		return vmSystemName;
	}

	/**
	 * 设定 vm system name.
	 *
	 * @param vmSystemName the vm system name
	 */
	public void setVmSystemName(String vmSystemName) {
		this.vmSystemName = vmSystemName;
	}

	/**
	 * 获得 image sid.
	 *
	 * @return 镜像ID image sid
	 */
	public String getImageSid() {
		return imageSid;
	}

	/**
	 * 设定 image sid.
	 *
	 * @param imageSid the image sid
	 */
	public void setImageSid(String imageSid) {
		this.imageSid = imageSid;
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
	 * 获得 softwares.
	 *
	 * @return the softwares
	 */
	public List<ResOsSoftware> getSoftwares() {
		return softwares;
	}

	/**
	 * 设定 softwares.
	 *
	 * @param softwares the softwares
	 */
	public void setSoftwares(List<ResOsSoftware> softwares) {
		this.softwares = softwares;
	}

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
	 * 获得 res vm inst name prefix.
	 *
	 * @return 虚拟机名称前缀 res vm inst name prefix
	 */
	public String getResVmInstNamePrefix() {
		return resVmInstNamePrefix;
	}

	/**
	 * 设定 res vm inst name prefix.
	 *
	 * @param resVmInstNamePrefix the res vm inst name prefix
	 */
	public void setResVmInstNamePrefix(String resVmInstNamePrefix) {
		this.resVmInstNamePrefix = resVmInstNamePrefix;
	}

	/**
	 * 获得 management account.
	 *
	 * @return 初始管理用户 management account
	 */
	public String getManagementAccount() {
		return managementAccount;
	}

	/**
	 * 设定 management account.
	 *
	 * @param managementAccount the management account
	 */
	public void setManagementAccount(String managementAccount) {
		this.managementAccount = managementAccount;
	}

	/**
	 * 获得 management password.
	 *
	 * @return 初始管理用户密码 management password
	 */
	public String getManagementPassword() {
		return managementPassword;
	}

	/**
	 * 设定 management password.
	 *
	 * @param managementPassword the management password
	 */
	public void setManagementPassword(String managementPassword) {
		this.managementPassword = managementPassword;
	}

	/**
	 * 获得 key pair.
	 *
	 * @return the keyPair
	 */
	public String getKeyPair() {
		return keyPair;
	}

	/**
	 * 设定 key pair.
	 *
	 * @param keyPair the keyPair to set
	 */
	public void setKeyPair(String keyPair) {
		this.keyPair = keyPair;
	}

	/**
	 * 获得 allocate res host ids.
	 *
	 * @return the allocateResHostIds
	 */
	public List<String> getAllocateResHostIds() {
		return allocateResHostIds;
	}

	/**
	 * 设定 allocate res host ids.
	 *
	 * @param allocateResHostIds the allocateResHostIds to set
	 */
	public void setAllocateResHostIds(List<String> allocateResHostIds) {
		this.allocateResHostIds = allocateResHostIds;
	}

	/**
	 * 获得 allocate res vc ids.
	 *
	 * @return the allocateResVcIds
	 */
	public List<String> getAllocateResVcIds() {
		return allocateResVcIds;
	}

	/**
	 * 设定 allocate res vc ids.
	 *
	 * @param allocateResVcIds the allocateResVcIds to set
	 */
	public void setAllocateResVcIds(List<String> allocateResVcIds) {
		this.allocateResVcIds = allocateResVcIds;
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

	/**
	 * 获得 vm users.
	 *
	 * @return the vm users
	 */
	public List<VmUser> getVmUsers() {
		return vmUsers;
	}

	/**
	 * 设定 vm users.
	 *
	 * @param vmUsers the vm users
	 */
	public void setVmUsers(List<VmUser> vmUsers) {
		this.vmUsers = vmUsers;
	}

	/**
	 * 获得 partition type.
	 *
	 * @return the partition type
	 */
	public String getPartitionType() {
		return partitionType;
	}

	/**
	 * 设定 partition type.
	 *
	 * @param partitionType the partition type
	 */
	public void setPartitionType(String partitionType) {
		this.partitionType = partitionType;
	}

	/**
	 * 获得 cpu pool sid.
	 *
	 * @return the cpu pool sid
	 */
	public String getCpuPoolSid() {
		return cpuPoolSid;
	}

	/**
	 * 设定 cpu pool sid.
	 *
	 * @param cpuPoolSid the cpu pool sid
	 */
	public void setCpuPoolSid(String cpuPoolSid) {
		this.cpuPoolSid = cpuPoolSid;
	}

	/**
	 * 获得 cpu min.
	 *
	 * @return the cpu min
	 */
	public int getCpuMin() {
		return cpuMin;
	}

	/**
	 * 设定 cpu min.
	 *
	 * @param cpuMin the cpu min
	 */
	public void setCpuMin(int cpuMin) {
		this.cpuMin = cpuMin;
	}

	/**
	 * 获得 cpu max.
	 *
	 * @return the cpu max
	 */
	public int getCpuMax() {
		return cpuMax;
	}

	/**
	 * 设定 cpu max.
	 *
	 * @param cpuMax the cpu max
	 */
	public void setCpuMax(int cpuMax) {
		this.cpuMax = cpuMax;
	}

	/**
	 * 获得 memory min.
	 *
	 * @return the memory min
	 */
	public long getMemoryMin() {
		return memoryMin;
	}

	/**
	 * 设定 memory min.
	 *
	 * @param memoryMin the memory min
	 */
	public void setMemoryMin(long memoryMin) {
		this.memoryMin = memoryMin;
	}

	/**
	 * 获得 memory max.
	 *
	 * @return the memory max
	 */
	public long getMemoryMax() {
		return memoryMax;
	}

	/**
	 * 设定 memory max.
	 *
	 * @param memoryMax the memory max
	 */
	public void setMemoryMax(long memoryMax) {
		this.memoryMax = memoryMax;
	}

	/**
	 * 获得 power cpu used units.
	 *
	 * @return the power cpu used units
	 */
	public float getPowerCpuUsedUnits() {
		return powerCpuUsedUnits;
	}

	/**
	 * 设定 power cpu used units.
	 *
	 * @param powerCpuUsedUnits the power cpu used units
	 */
	public void setPowerCpuUsedUnits(float powerCpuUsedUnits) {
		this.powerCpuUsedUnits = powerCpuUsedUnits;
	}

	/**
	 * 获得 power cpu min units.
	 *
	 * @return the power cpu min units
	 */
	public float getPowerCpuMinUnits() {
		return powerCpuMinUnits;
	}

	/**
	 * 设定 power cpu min units.
	 *
	 * @param powerCpuMinUnits the power cpu min units
	 */
	public void setPowerCpuMinUnits(float powerCpuMinUnits) {
		this.powerCpuMinUnits = powerCpuMinUnits;
	}

	/**
	 * 获得 power cpu max units.
	 *
	 * @return the power cpu max units
	 */
	public float getPowerCpuMaxUnits() {
		return powerCpuMaxUnits;
	}

	/**
	 * 设定 power cpu max units.
	 *
	 * @param powerCpuMaxUnits the power cpu max units
	 */
	public void setPowerCpuMaxUnits(float powerCpuMaxUnits) {
		this.powerCpuMaxUnits = powerCpuMaxUnits;
	}

	/**
	 * 获得 virtual switch sid.
	 *
	 * @return the virtual switch sid
	 */
	public String getVirtualSwitchSid() {
		return virtualSwitchSid;
	}

	/**
	 * 设定 virtual switch sid.
	 *
	 * @param virtualSwitchSid the virtual switch sid
	 */
	public void setVirtualSwitchSid(String virtualSwitchSid) {
		this.virtualSwitchSid = virtualSwitchSid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
	}
}
