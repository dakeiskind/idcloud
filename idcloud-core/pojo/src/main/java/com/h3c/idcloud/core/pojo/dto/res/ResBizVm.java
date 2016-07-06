package com.h3c.idcloud.core.pojo.dto.res;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResBizVm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4943119491357489731L;
	private String resBizVmSid;
	private String resBizVmSids;
	private String resTopologySid;
	private String resTopologyType;
	private String vmName;
	private String vmNameLike;
	private Integer cpuCores;
	private Long memorySize;
	private String osVersion;
	private String allocateResHostSid;
	private String allocateResStorageSid;
	private String ownerHost;
	private String managementAccount;
	private String managementPassword;
	private String secretKey;
	private String uuid;
	private String monitorNodeId;
	private String monitorStatus;
	private String monitorStatusName;
	private String status;
	private String statusName;
	private String vmIp;
	private String cpuUsage;
	private String memUsage;
	private String diskUsage;
	private String createdBy;
	private Date createdDt;
	private String updatedBy;
	private Date updatedDt;
	private Long version;
    
	private Long parentBizSids;
	private Long mgtObjSid;
	private Long parentMgtObjSid;
	private Long mgtObjLevel;
	
	private String mgtObjName;
	private String parentMgtObjName;
	private String name;
	
	private Date dredgeDate;
	
	private Long instanceSid;
	private String instanceName;
	
	private Long storage;
	
	private String orgName;
	
	private String cpuMaxUsage;
	private String memMaxUsage;
	private String diskMaxUsage;
	private double netWorkAvg;
	private double netWorkMax;
	private String instanceStatus;
	private String instanceStatusName;
	private String isFreeName;
	private Long isFree;
	
	/**
     * 统计虚拟机-- 总数
     */
    private int staTotalVm;
    
    /**
     * 统计虚拟机-- 正常数
     */
    private int staNormalVm;
    
    
    /**
     * 统计虚拟机-- 关机数
     */
    private int staDownVm;
    
    /**
     * 统计虚拟机-- 维护数
     */
    private int staPauseVm;
    
    /**
     * 统计虚拟机-- 其他数
     */
    private int staOthersVm;
    
    /**
     *  统计虚拟机 -- 磁盘大小
     */
    private int staVmDisk;
    
    private String cpuUseRate;
    private String memoryUseRate;
    private String storageUseRate;
    private String vmDiskSize;

	/**是否在提醒时间内         1：在；-1：已过期；0：不在**/
    private Integer inNoticeTime;
    
    private String userSid;
    
    public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public Integer getInNoticeTime() {
    	return inNoticeTime;
    }
    
    public void setInNoticeTime(Integer inNoticeTime) {
    	this.inNoticeTime = inNoticeTime;
    }
	
	public String getVmName() {
		return this.vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public Integer getCpuCores() {
		return this.cpuCores;
	}

	public void setCpuCores(Integer cpuCores) {
		this.cpuCores = cpuCores;
	}

	public Long getMemorySize() {
		return this.memorySize;
	}

	public void setMemorySize(Long memorySize) {
		this.memorySize = memorySize;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getManagementAccount() {
		return this.managementAccount;
	}

	public void setManagementAccount(String managementAccount) {
		this.managementAccount = managementAccount;
	}

	public String getManagementPassword() {
		return this.managementPassword;
	}

	public void setManagementPassword(String managementPassword) {
		this.managementPassword = managementPassword;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMonitorNodeId() {
		return this.monitorNodeId;
	}

	public void setMonitorNodeId(String monitorNodeId) {
		this.monitorNodeId = monitorNodeId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return this.updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getOwnerHost() {
		return this.ownerHost;
	}

	public void setOwnerHost(String ownerHost) {
		this.ownerHost = ownerHost;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getVmNameLike() {
		return this.vmNameLike;
	}

	public void setVmNameLike(String vmNameLike) {
		this.vmNameLike = vmNameLike;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVmIp() {
		return this.vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public String getCpuUsage() {
		return this.cpuUsage;
	}

	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public String getMemUsage() {
		return this.memUsage;
	}

	public void setMemUsage(String memUsage) {
		this.memUsage = memUsage;
	}

	public String getDiskUsage() {
		return this.diskUsage;
	}

	public void setDiskUsage(String diskUsage) {
		this.diskUsage = diskUsage;
	}

	public String getResTopologyType() {
		return this.resTopologyType;
	}

	public void setResTopologyType(String resTopologyType) {
		this.resTopologyType = resTopologyType;
	}

	public String getResBizVmSid() {
		return resBizVmSid;
	}

	public void setResBizVmSid(String resBizVmSid) {
		this.resBizVmSid = resBizVmSid;
	}

	public String getResTopologySid() {
		return resTopologySid;
	}

	public void setResTopologySid(String resTopologySid) {
		this.resTopologySid = resTopologySid;
	}

	public String getAllocateResHostSid() {
		return allocateResHostSid;
	}

	public void setAllocateResHostSid(String allocateResHostSid) {
		this.allocateResHostSid = allocateResHostSid;
	}

	public String getAllocateResStorageSid() {
		return allocateResStorageSid;
	}

	public void setAllocateResStorageSid(String allocateResStorageSid) {
		this.allocateResStorageSid = allocateResStorageSid;
	}

	public Long getStorage() {
		return storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	public String getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(String monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public Date getDredgeDate() {
		return dredgeDate;
	}

	public void setDredgeDate(Date dredgeDate) {
		this.dredgeDate = dredgeDate;
	}

	public Long getInstanceSid() {
		return instanceSid;
	}

	public void setInstanceSid(Long instanceSid) {
		this.instanceSid = instanceSid;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCpuMaxUsage() {
		return cpuMaxUsage;
	}

	public void setCpuMaxUsage(String cpuMaxUsage) {
		this.cpuMaxUsage = cpuMaxUsage;
	}

	public String getMemMaxUsage() {
		return memMaxUsage;
	}

	public void setMemMaxUsage(String memMaxUsage) {
		this.memMaxUsage = memMaxUsage;
	}

	public String getDiskMaxUsage() {
		return diskMaxUsage;
	}

	public void setDiskMaxUsage(String diskMaxUsage) {
		this.diskMaxUsage = diskMaxUsage;
	}

	public double getNetWorkAvg() {
		return netWorkAvg;
	}

	public void setNetWorkAvg(double netWorkAvg) {
		this.netWorkAvg = netWorkAvg;
	}

	public double getNetWorkMax() {
		return netWorkMax;
	}

	public void setNetWorkMax(double netWorkMax) {
		this.netWorkMax = netWorkMax;
	}

	public Long getParentBizSids() {
		return parentBizSids;
	}

	public void setParentBizSids(Long parentBizSids) {
		this.parentBizSids = parentBizSids;
	}

	public int getStaTotalVm() {
		return staTotalVm;
	}

	public void setStaTotalVm(int staTotalVm) {
		this.staTotalVm = staTotalVm;
	}

	public int getStaNormalVm() {
		return staNormalVm;
	}

	public void setStaNormalVm(int staNormalVm) {
		this.staNormalVm = staNormalVm;
	}

	public int getStaDownVm() {
		return staDownVm;
	}

	public void setStaDownVm(int staDownVm) {
		this.staDownVm = staDownVm;
	}

	public int getStaPauseVm() {
		return staPauseVm;
	}

	public void setStaPauseVm(int staPauseVm) {
		this.staPauseVm = staPauseVm;
	}

	public int getStaOthersVm() {
		return staOthersVm;
	}

	public void setStaOthersVm(int staOthersVm) {
		this.staOthersVm = staOthersVm;
	}

	public String getResBizVmSids() {
		return resBizVmSids;
	}

	public void setResBizVmSids(String resBizVmSids) {
		this.resBizVmSids = resBizVmSids;
	}

	public String getMonitorStatusName() {
		return monitorStatusName;
	}

	public void setMonitorStatusName(String monitorStatusName) {
		this.monitorStatusName = monitorStatusName;
	}

	public int getStaVmDisk() {
		return staVmDisk;
	}

	public void setStaVmDisk(int staVmDisk) {
		this.staVmDisk = staVmDisk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpuUseRate() {
		return cpuUseRate;
	}

	public void setCpuUseRate(String cpuUseRate) {
		this.cpuUseRate = cpuUseRate;
	}

	public String getMemoryUseRate() {
		return memoryUseRate;
	}

	public void setMemoryUseRate(String memoryUseRate) {
		this.memoryUseRate = memoryUseRate;
	}

	public String getStorageUseRate() {
		return storageUseRate;
	}

	public void setStorageUseRate(String storageUseRate) {
		this.storageUseRate = storageUseRate;
	}

	public String getVmDiskSize() {
		return vmDiskSize;
	}

	public void setVmDiskSize(String vmDiskSize) {
		this.vmDiskSize = vmDiskSize;
	}

	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public String getInstanceStatusName() {
		return instanceStatusName;
	}

	public void setInstanceStatusName(String instanceStatusName) {
		this.instanceStatusName = instanceStatusName;
	}

	public String getIsFreeName() {
		return isFreeName;
	}

	public void setIsFreeName(String isFreeName) {
		this.isFreeName = isFreeName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Long getIsFree() {
		return isFree;
	}

	public void setIsFree(Long isFree) {
		this.isFree = isFree;
	}

	public Long getMgtObjSid() {
		return mgtObjSid;
	}

	public void setMgtObjSid(Long mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	public Long getParentMgtObjSid() {
		return parentMgtObjSid;
	}

	public void setParentMgtObjSid(Long parentMgtObjSid) {
		this.parentMgtObjSid = parentMgtObjSid;
	}

	public Long getMgtObjLevel() {
		return mgtObjLevel;
	}

	public void setMgtObjLevel(Long mgtObjLevel) {
		this.mgtObjLevel = mgtObjLevel;
	}

	public String getMgtObjName() {
		return mgtObjName;
	}

	public void setMgtObjName(String mgtObjName) {
		this.mgtObjName = mgtObjName;
	}

	public String getParentMgtObjName() {
		return parentMgtObjName;
	}

	public void setParentMgtObjName(String parentMgtObjName) {
		this.parentMgtObjName = parentMgtObjName;
	}
	
}