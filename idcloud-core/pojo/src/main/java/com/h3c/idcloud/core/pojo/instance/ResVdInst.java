/*
 * 惠普云管理平台
 * 概要：虚拟磁盘资源实例定义类
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
import java.util.List;

/**
 * 虚拟磁盘资源实例定义类
 *
 * @author 徐印
 */
public class ResVdInst implements Serializable {

	/**
	 * 虚拟磁盘Id
	 */
	private String resVdInstId;

	/**
	 * 虚拟磁盘名称
	 */
	private String resVdInstName;

	/**
	 * 虚拟磁盘名称前缀
	 */
	private String resVdInstNamePrefix;

	/**
	 * 磁盘大小(MB)
	 */
	private long diskSize;

	/**
	 * 租户Sid
	 */
	private long mgtObjSid;

	/**
	 * 分配存储资源Id
	 */
	private List<String> allocateResStorageIds;

	/**
	 * 磁盘所挂载虚拟机Id
	 */
	private String resVmId;

	/**
	 * 磁盘存储用途
	 */
	private String storagePurpose;

	/**
	 * 操作
	 */
	private String operate;

	/**
	 * 系统盘HBASid
	 */
	private String sysDiskHBASid;

	/**
	 * 卷名
	 */
	private String volumeName;

	/**
	 * 挂载点
	 */
	private String mountPoint;

	/**
	 * 文件系统
	 */
	private String fileSystem;

	/**
	 * 获得 res vd inst name.
	 *
	 * @return 虚拟磁盘名称 res vd inst name
	 */
	public String getResVdInstName() {
		return resVdInstName;
	}

	/**
	 * 设定 res vd inst name.
	 *
	 * @param resVdInstName the res vd inst name
	 */
	public void setResVdInstName(String resVdInstName) {
		this.resVdInstName = resVdInstName;
	}

	/**
	 * 获得 res vd inst name prefix.
	 *
	 * @return 虚拟磁盘名称前缀 res vd inst name prefix
	 */
	public String getResVdInstNamePrefix() {
		return resVdInstNamePrefix;
	}

	/**
	 * 设定 res vd inst name prefix.
	 *
	 * @param resVdInstNamePrefix the res vd inst name prefix
	 */
	public void setResVdInstNamePrefix(String resVdInstNamePrefix) {
		this.resVdInstNamePrefix = resVdInstNamePrefix;
	}

	/**
	 * 获得 disk size.
	 *
	 * @return 磁盘大小(MB) disk size
	 */
	public long getDiskSize() {
		return diskSize;
	}

	/**
	 * 设定 disk size.
	 *
	 * @param diskSize the disk size
	 */
	public void setDiskSize(long diskSize) {
		this.diskSize = diskSize;
	}

	/**
	 * 获得 allocate res storage ids.
	 *
	 * @return the allocateResStorageIds
	 */
	public List<String> getAllocateResStorageIds() {
		return allocateResStorageIds;
	}

	/**
	 * 设定 allocate res storage ids.
	 *
	 * @param allocateResStorageIds the allocateResStorageIds to set
	 */
	public void setAllocateResStorageIds(List<String> allocateResStorageIds) {
		this.allocateResStorageIds = allocateResStorageIds;
	}

	/**
	 * 获得 res vm id.
	 *
	 * @return 磁盘所挂载虚拟机Id res vm id
	 */
	public String getResVmId() {
		return resVmId;
	}

	/**
	 * 设定 res vm id.
	 *
	 * @param resVmId the res vm id
	 */
	public void setResVmId(String resVmId) {
		this.resVmId = resVmId;
	}

	/**
	 * 获得 storage purpose.
	 *
	 * @return 磁盘存储用途 storage purpose
	 */
	public String getStoragePurpose() {
		return storagePurpose;
	}

	/**
	 * 设定 storage purpose.
	 *
	 * @param storagePurpose the storage purpose
	 */
	public void setStoragePurpose(String storagePurpose) {
		this.storagePurpose = storagePurpose;
	}

	/**
	 * 获得 res vd inst id.
	 *
	 * @return 虚拟磁盘Id res vd inst id
	 */
	public String getResVdInstId() {
		return resVdInstId;
	}

	/**
	 * 设定 res vd inst id.
	 *
	 * @param resVdInstId the res vd inst id
	 */
	public void setResVdInstId(String resVdInstId) {
		this.resVdInstId = resVdInstId;
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
	 * 获得 mgt obj sid.
	 *
	 * @return the mgtObjSid
	 */
	public long getMgtObjSid() {
		return mgtObjSid;
	}

	/**
	 * 设定 mgt obj sid.
	 *
	 * @param mgtObjSid the mgtObjSid to set
	 */
	public void setMgtObjSid(long mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	/**
	 * 获得 sys disk hba sid.
	 *
	 * @return the sys disk hba sid
	 */
	public String getSysDiskHBASid() {
		return sysDiskHBASid;
	}

	/**
	 * 设定 sys disk hba sid.
	 *
	 * @param sysDiskHBASid the sys disk hba sid
	 */
	public void setSysDiskHBASid(String sysDiskHBASid) {
		this.sysDiskHBASid = sysDiskHBASid;
	}

	/**
	 * 获得 volume name.
	 *
	 * @return the volume name
	 */
	public String getVolumeName() {
		return volumeName;
	}

	/**
	 * 设定 volume name.
	 *
	 * @param volumeName the volume name
	 */
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	/**
	 * 获得 mount point.
	 *
	 * @return the mount point
	 */
	public String getMountPoint() {
		return mountPoint;
	}

	/**
	 * 设定 mount point.
	 *
	 * @param mountPoint the mount point
	 */
	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	/**
	 * 获得 file system.
	 *
	 * @return the file system
	 */
	public String getFileSystem() {
		return fileSystem;
	}

	/**
	 * 设定 file system.
	 *
	 * @param fileSystem the file system
	 */
	public void setFileSystem(String fileSystem) {
		this.fileSystem = fileSystem;
	}
	
}
