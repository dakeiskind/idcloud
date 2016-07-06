package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceInstanceVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 服务实例SID
	 */
	private Long instanceSid;
	
	/**
	 * 实例关联的资源sid
	 */
	private String resSid;
	
	private String visitAddress;

	/**
	 * 父服务实例Sid
	 */
	private Long parentInstanceSid;
	
	/**
	 * 父外部服务实例ID
	 */
	private String oServiceId;
	
	
	/**
	 * 主机资源实例SID
	 */
	private Long resInstanceSid;

	/**
	 * 实例名称
	 */
	private String instanceName;

	/**
	 * 计费类型
	 */
	private String billingType;
	
	/**
	 * 服务实例配置
	 */
	private String serviceConfig;

	/**
	 * 购买时长
	 */
	private Long buyLength;

	/**
	 * 计费类型名称
	 */
	private String billingTypeName;

	/**
	 * 计费类型ID
	 */
	private String billingTypeId;

	/**
	 * 实例描述
	 */
	private String description;

	/**
	 * 到期时间
	 */
	private Date expiringDate;

	/**
	 * 所有者ID
	 */
	private String ownerId;

	/**
	 * 订单明细ID
	 */
	private Long detailSid;

	/**
	 * 所有者名称
	 */
	private String ownerName;
	
	/**
	 * 所有者email
	 */
	private String ownerEmail;

	/**
	 * 服务SID
	 */
	private Long serviceSid;

	/**
	 * 服务名称
	 */
	private String serviceName;

	/**
	 * 服务模板SID
	 */
	private Long templateSid;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 实例状态
	 */
	private String status;

	/**
	 * 资源实例状态名称
	 */
	private String statusName;

	/**
	 * 资源实例状态
	 */
	private String resStatus;

	/**
	 * 目标
	 */
	private String target;

	/**
	 * 租户ID
	 */
	private String tanentId;

	/**
	 * 管理对象sid
	 */
	private Long mgtObjSid;

	/**
	 * 租户名称
	 */
	private String tenantName;

	/**
	 * 租户名称简写
	 */
	private String tenantShortName;

	/**
	 * 期望开通时间
	 */
	private Date expectedTime;

	/**
	 * 开通时间
	 */
	private Date dredgeDate;

	/**
	 * 实例创建开始时间
	 */
	private Date creationDateBegin;

	/**
	 * 实例创建结束时间
	 */
	private Date creationDateEnd;

	/**
	 * 主机资源实例对应分配实例ID
	 */
	private String allocateInstanceId;

	/**
	 * 主机资源实例对应资源池Sid
	 */
	private long resPoolSid;

	/**
	 * 存储资源实例对应资源Sid
	 */
	private long allocateResStorageSid;
	
	/**
	 * 服务退订时间
	 */
	private Date destroyDate;
	
	/**
	 * 最后一次付费时间
	 */
	private Date billingEndTime;
	
	/**
	 * Sharepoint申请的配额
	 */
	private long allocateStorageCapacity;
	
	/**
	 * 是否闲置
	 */
	private Long isFree;
	
	private String isFreeName;
	
	/**
	 * 磁盘大小
	 */
	private String diskSize;
	
	/**
	 * 新磁盘大小
	 */
	private String newDiskSize;
	
	private String vmIp;
	
	private String resInsVdStatus;
	
	private String resInsVdStatusName;
	
	private String ipStatusName;
	
	private String floatingIpSid;
	
	private String floatingIpStatus;
	
	private String userSid;

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public String getFloatingIpStatus() {
		return floatingIpStatus;
	}

	public void setFloatingIpStatus(String floatingIpStatus) {
		this.floatingIpStatus = floatingIpStatus;
	}

	public String getFloatingIpSid() {
		return floatingIpSid;
	}

	public void setFloatingIpSid(String floatingIpSid) {
		this.floatingIpSid = floatingIpSid;
	}

	public String getIpStatusName() {
		return ipStatusName;
	}

	public void setIpStatusName(String ipStatusName) {
		this.ipStatusName = ipStatusName;
	}

	/**
	 * @return the allocateResStorageSid
	 */
	public long getAllocateResStorageSid() {
		return allocateResStorageSid;
	}

	/**
	 * @param allocateResStorageSid
	 *            the allocateResStorageSid to set
	 */
	public void setAllocateResStorageSid(long allocateResStorageSid) {
		this.allocateResStorageSid = allocateResStorageSid;
	}

	/**
	 * 创建人
	 */
	private String createdBy;

	/**
	 * 创建时间
	 */
	private Date createdDt;

	/**
	 * 更新人
	 */
	private String updatedBy;

	/**
	 * 更新时间
	 */
	private Date updatedDt;

	/**
	 * 版本号
	 */
	private Long version;

	/**
	 * 服务实例资源可用检查结果
	 */
	private boolean checkedResult;

	/**
	 * 自动开通流程实例ID
	 */
	private String processInstanceId;

	/**
	 * 退订流程实例ID
	 */
	private String processInstanceCancelId;

	/**
	 * 服务实例开通错误描述
	 */
	private String processFailedDesc;

	/**
	 * 服务异常描述
	 */
	private String exceptionDesc;

	/**
	 * 主机资源实例状态名称
	 */
	private String resInsVmStatusName;

	/**
	 * 主机资源实例状态
	 */
	private String resInsVmStatus;

	/**
	 * 服务实例规格
	 */
	private List<ServiceInstanceSpec> serviceInstanceSpec;

	/**
	 * 操作系统名称
	 */
	private String osTypeName;

	/**
	 * VLAN ID
	 */
	private String vlanId;

	/**
	 * 计费类型单位
	 */
	private String billingTypeUnit;

	/**
	 * 计费类型单位
	 */
	private Integer totalServiceNum;

	/**
	 * 计费类型单位
	 */
	private Integer validServiceNum;

	/**
	 * 用户服务数
	 */
	private Integer userServiceNum;

	/**
	 * 租户配额数
	 */
	private Integer quotaServiceNum;

	/**
	 * 用户服务数
	 */
	private Integer userServiceVmNum;

	/**
	 * 用户服务数
	 */
	private Integer userServiceStNum;

	/**
	 * 用户服务数
	 */
	private Integer userServiceCpuNum;

	/**
	 * 用户服务数
	 */
	private Integer userServiceMemoryNum;

	/**
	 * 用户服务数
	 */
	private Integer userServiceStdiskNum;

	/**
	 * 租户剩余配额数
	 */
	private Integer restQuotaServiceNum;
	/**
	 * 服务实例下主机资源实例对应节点ID
	 */
	private String monitorNodeId;

	/**
	 * 分配的IP（内网）
	 */
	private String allocateIp;

	/**
	 * 虚拟化平台类型
	 */
	private String virtualPlatformType;

	/**
	 * 资源实例名称
	 */
	private String resInstanceName;

	/**
	 * 块存储容量
	 */
	private String allocateDiskSize;

	/**
	 * 应用主机
	 */
	private String allocateVmName;
    /**
     * 购买时长
     */
    private String purchaseLongTime;
	/**
	 * 应用主机
	 */
	private String resVmInsName;

	/**
	 * 存储类型
	 */
	private String volumeType;

	/**
	 * 存储类型名称
	 */
	private String volumeTypeName;

	/**
	 * 变更流程实例
	 */
	private String processInstanceChangeId;

	/**
	 * 存储实例分配的对应主机实例Sid
	 */
	private String resInstanceHostSid;

	private String allocateSharepointAddress;

	/**
	 * 商务合同号
	 */
	private String contractId;


	/**
	 * 项目立项号
	 */
	private String projectId;

	/**
	 * 合同文件
	 */
	private String contractFile;

	/**
	 * 防火墙端口
	 */
	private String fwPort;

	private String computeResSet;

	private String computeResSetName;

	private String internetVlan;

	private String internetVlanName;

	private String intranetVlan;

	private String intranetVlanName;

	private String vmName;

	private String vdName;

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * 用于查询关联的资源的字段
	 */
	/**
	 * 虚机数量
	 */
	private Integer vmCount;
	private Integer cpuCores;
	private Long memorySize;
	private Long totalStorage;
	private Long availableCapacity;

	private String imagePath;

	private Integer vmInstanceCount;
	private Integer blockStorageInstanceCount;
	private Integer objectStorageInstanceCount;
	private Integer floatingIpInstanceCount;
	private Integer cdnInstanceCount;

	private String mgtObjName;
	private String mgtObjNameLike;

	private Date currentTime;
	private Integer billingDay;
	private String serviceType;

	private String resIpSid;

	public String getAllocateSharepointAddress() {
		return allocateSharepointAddress;
	}

	public void setAllocateSharepointAddress(String allocateSharepointAddress) {
		this.allocateSharepointAddress = allocateSharepointAddress;
	}

	/**
	 * @return 服务实例SID
	 */
	public Long getInstanceSid() {
		return instanceSid;
	}

	/**
	 * @param instanceSid
	 *            服务实例SID
	 */
	public void setInstanceSid(Long instanceSid) {
		this.instanceSid = instanceSid;
	}

	/**
	 * @return 实例名称
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName
	 *            实例名称
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return 计费类型
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType
	 *            计费类型
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return 计费类型ID
	 */
	public String getBillingTypeId() {
		return billingTypeId;
	}

	/**
	 * @param billingTypeId
	 *            计费类型ID
	 */
	public void setBillingTypeId(String billingTypeId) {
		this.billingTypeId = billingTypeId;
	}

	/**
	 * @return 实例描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            实例描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return 到期时间
	 */
	public Date getExpiringDate() {
		return expiringDate;
	}

	/**
	 * @param expiringDate
	 *            到期时间
	 */
	public void setExpiringDate(Date expiringDate) {
		this.expiringDate = expiringDate;
	}

	/**
	 * @return 所有者ID
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            所有者ID
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return 服务SID
	 */
	public Long getServiceSid() {
		return serviceSid;
	}

	/**
	 * @param serviceSid
	 *            服务SID
	 */
	public void setServiceSid(Long serviceSid) {
		this.serviceSid = serviceSid;
	}

	/**
	 * @return 服务模板SID
	 */
	public Long getTemplateSid() {
		return templateSid;
	}

	/**
	 * @param templateSid
	 *            服务模板SID
	 */
	public void setTemplateSid(Long templateSid) {
		this.templateSid = templateSid;
	}

	/**
	 * @return 订单ID
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            订单ID
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 实例状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            实例状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 目标
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            目标
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return 租户ID
	 */
	public String getTanentId() {
		return tanentId;
	}

	/**
	 * @param tanentId
	 *            租户ID
	 */
	public void setTanentId(String tanentId) {
		this.tanentId = tanentId;
	}

	/**
	 * @return 开通时间
	 */
	public Date getDredgeDate() {
		return dredgeDate;
	}

	/**
	 * @param dredgeDate
	 *            开通时间
	 */
	public void setDredgeDate(Date dredgeDate) {
		this.dredgeDate = dredgeDate;
	}

	/**
	 * @return 实例创建开始时间
	 */
	public Date getCreationDateBegin() {
		return creationDateBegin;
	}

	/**
	 * @param creationDateBegin
	 *            实例创建开始时间
	 */
	public void setCreationDateBegin(Date creationDateBegin) {
		this.creationDateBegin = creationDateBegin;
	}

	/**
	 * @return 实例创建结束时间
	 */
	public Date getCreationDateEnd() {
		return creationDateEnd;
	}

	/**
	 * @param creationDateEnd
	 *            实例创建结束时间
	 */
	public void setCreationDateEnd(Date creationDateEnd) {
		this.creationDateEnd = creationDateEnd;
	}

	/**
	 * @return 创建人
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            创建人
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return 创建时间
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt
	 *            创建时间
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return 更新人
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            更新人
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return 更新时间
	 */
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt
	 *            更新时间
	 */
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * @return 版本号
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            版本号
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the checkedResult
	 */
	public boolean isCheckedResult() {
		return checkedResult;
	}

	/**
	 * @param checkedResult
	 *            the checkedResult to set
	 */
	public void setCheckedResult(boolean checkedResult) {
		this.checkedResult = checkedResult;
	}

	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId
	 *            the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the processInstanceCancelId
	 */
	public String getProcessInstanceCancelId() {
		return processInstanceCancelId;
	}

	/**
	 * @param processInstanceCancelId
	 *            the processInstanceCancelId to set
	 */
	public void setProcessInstanceCancelId(String processInstanceCancelId) {
		this.processInstanceCancelId = processInstanceCancelId;
	}

	/**
	 * @return the serviceInstanceSpec
	 */
	public List<ServiceInstanceSpec> getServiceInstanceSpec() {
		return serviceInstanceSpec;
	}

	/**
	 * @param serviceInstanceSpec
	 *            the serviceInstanceSpec to set
	 */
	public void setServiceInstanceSpec(
			List<ServiceInstanceSpec> serviceInstanceSpec) {
		this.serviceInstanceSpec = serviceInstanceSpec;
	}

	/**
	 * @return the expectedTime
	 */
	public Date getExpectedTime() {
		return expectedTime;
	}

	/**
	 * @param expectedTime
	 *            the expectedTime to set
	 */
	public void setExpectedTime(Date expectedTime) {
		this.expectedTime = expectedTime;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the billingTypeName
	 */
	public String getBillingTypeName() {
		return billingTypeName;
	}

	/**
	 * @param billingTypeName
	 *            the billingTypeName to set
	 */
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}

	/**
	 * @return the resInsVmStatusName
	 */
	public String getResInsVmStatusName() {
		return resInsVmStatusName;
	}

	/**
	 * @param resInsVmStatusName
	 *            the resInsVmStatusName to set
	 */
	public void setResInsVmStatusName(String resInsVmStatusName) {
		this.resInsVmStatusName = resInsVmStatusName;
	}

	/**
	 * @return the resInsVmStatus
	 */
	public String getResInsVmStatus() {
		return resInsVmStatus;
	}

	/**
	 * @param resInsVmStatus
	 *            the resInsVmStatus to set
	 */
	public void setResInsVmStatus(String resInsVmStatus) {
		this.resInsVmStatus = resInsVmStatus;
	}

	/**
	 * @return the vlanId
	 */
	public String getVlanId() {
		return vlanId;
	}

	/**
	 * @param vlanId
	 *            the vlanId to set
	 */
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	/**
	 * @return the osTypeName
	 */
	public String getOsTypeName() {
		return osTypeName;
	}

	/**
	 * @param osTypeName
	 *            the osTypeName to set
	 */
	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}

	/**
	 * @return the buyLength
	 */
	public Long getBuyLength() {
		return buyLength;
	}

	/**
	 * @param buyLength
	 *            the buyLength to set
	 */
	public void setBuyLength(Long buyLength) {
		this.buyLength = buyLength;
	}

	/**
	 * @return the billingTypeUnit
	 */
	public String getBillingTypeUnit() {
		return billingTypeUnit;
	}

	/**
	 * @param billingTypeUnit
	 *            the billingTypeUnit to set
	 */
	public void setBillingTypeUnit(String billingTypeUnit) {
		this.billingTypeUnit = billingTypeUnit;
	}

	/**
	 * @return the processFailedDesc
	 */
	public String getProcessFailedDesc() {
		return processFailedDesc;
	}

	/**
	 * @param processFailedDesc
	 *            the processFailedDesc to set
	 */
	public void setProcessFailedDesc(String processFailedDesc) {
		this.processFailedDesc = processFailedDesc;
	}

	/**
	 * @return the detailSid
	 */
	public Long getDetailSid() {
		return detailSid;
	}

	/**
	 * @param detailSid
	 *            the detailSid to set
	 */
	public void setDetailSid(Long detailSid) {
		this.detailSid = detailSid;
	}

	/**
	 * @return the exceptionDesc
	 */
	public String getExceptionDesc() {
		return exceptionDesc;
	}

	/**
	 * @param exceptionDesc
	 *            the exceptionDesc to set
	 */
	public void setExceptionDesc(String exceptionDesc) {
		this.exceptionDesc = exceptionDesc;
	}

	/**
	 * @return the monitorNodeId
	 */
	public String getMonitorNodeId() {
		return monitorNodeId;
	}

	/**
	 * @param monitorNodeId
	 *            the monitorNodeId to set
	 */
	public void setMonitorNodeId(String monitorNodeId) {
		this.monitorNodeId = monitorNodeId;
	}

	/**
	 * @return the totalServiceNum
	 */
	public Integer getTotalServiceNum() {
		return totalServiceNum;
	}

	/**
	 * @param totalServiceNum
	 *            the totalServiceNum to set
	 */
	public void setTotalServiceNum(Integer totalServiceNum) {
		this.totalServiceNum = totalServiceNum;
	}

	/**
	 * @return the validServiceNum
	 */
	public Integer getValidServiceNum() {
		return validServiceNum;
	}

	/**
	 * @param validServiceNum
	 *            the validServiceNum to set
	 */
	public void setValidServiceNum(Integer validServiceNum) {
		this.validServiceNum = validServiceNum;
	}

	/**
	 * @return the resInstanceSid
	 */
	public Long getResInstanceSid() {
		return resInstanceSid;
	}

	/**
	 * @param resInstanceSid
	 *            the resInstanceSid to set
	 */
	public void setResInstanceSid(Long resInstanceSid) {
		this.resInstanceSid = resInstanceSid;
	}

	/**
	 * @return the allocateIp
	 */
	public String getAllocateIp() {
		return allocateIp;
	}

	/**
	 * @param allocateIp
	 *            the allocateIp to set
	 */
	public void setAllocateIp(String allocateIp) {
		this.allocateIp = allocateIp;
	}

	/**
	 * @return the tenantShortName
	 */
	public String getTenantShortName() {
		return tenantShortName;
	}

	/**
	 * @param tenantShortName
	 *            the tenantShortName to set
	 */
	public void setTenantShortName(String tenantShortName) {
		this.tenantShortName = tenantShortName;
	}

	/**
	 * @return the allocateInstanceId
	 */
	public String getAllocateInstanceId() {
		return allocateInstanceId;
	}

	/**
	 * @param allocateInstanceId
	 *            the allocateInstanceId to set
	 */
	public void setAllocateInstanceId(String allocateInstanceId) {
		this.allocateInstanceId = allocateInstanceId;
	}

	/**
	 * @return the resPoolSid
	 */
	public long getResPoolSid() {
		return resPoolSid;
	}

	/**
	 * @param resPoolSid
	 *            the resPoolSid to set
	 */
	public void setResPoolSid(long resPoolSid) {
		this.resPoolSid = resPoolSid;
	}

	/**
	 * @return the virtualPlatformType
	 */
	public String getVirtualPlatformType() {
		return virtualPlatformType;
	}

	/**
	 * @param virtualPlatformType
	 *            the virtualPlatformType to set
	 */
	public void setVirtualPlatformType(String virtualPlatformType) {
		this.virtualPlatformType = virtualPlatformType;
	}

	public String getResInstanceName() {
		return resInstanceName;
	}

	public void setResInstanceName(String resInstanceName) {
		this.resInstanceName = resInstanceName;
	}

	public String getAllocateDiskSize() {
		return allocateDiskSize;
	}

	public void setAllocateDiskSize(String allocateDiskSize) {
		this.allocateDiskSize = allocateDiskSize;
	}

	public String getAllocateVmName() {
		return allocateVmName;
	}

	public void setAllocateVmName(String allocateVmName) {
		this.allocateVmName = allocateVmName;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public String getVolumeTypeName() {
		return volumeTypeName;
	}

	/**
	 * @return the resInstanceHostSid
	 */
	public String getResInstanceHostSid() {
		return resInstanceHostSid;
	}

	/**
	 * @param resInstanceHostSid
	 *            the resInstanceHostSid to set
	 */
	public void setResInstanceHostSid(String resInstanceHostSid) {
		this.resInstanceHostSid = resInstanceHostSid;
	}

	public void setVolumeTypeName(String volumeTypeName) {
		this.volumeTypeName = volumeTypeName;
	}

	/**
	 * @return the resStatus
	 */
	public String getResStatus() {
		return resStatus;
	}

	/**
	 * @param resStatus
	 *            the resStatus to set
	 */
	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	/**
	 * @return the resVmInsName
	 */
	public String getResVmInsName() {
		return resVmInsName;
	}

	/**
	 * @param resVmInsName
	 *            the resVmInsName to set
	 */
	public void setResVmInsName(String resVmInsName) {
		this.resVmInsName = resVmInsName;
	}

	public Integer getUserServiceNum() {
		return userServiceNum;
	}

	public void setUserServiceNum(Integer userServiceNum) {
		this.userServiceNum = userServiceNum;
	}

	public Integer getQuotaServiceNum() {
		return quotaServiceNum;
	}

	public void setQuotaServiceNum(Integer quotaServiceNum) {
		this.quotaServiceNum = quotaServiceNum;
	}

	public Integer getRestQuotaServiceNum() {
		return restQuotaServiceNum;
	}

	public void setRestQuotaServiceNum(Integer restQuotaServiceNum) {
		this.restQuotaServiceNum = restQuotaServiceNum;
	}

	public Integer getUserServiceVmNum() {
		return userServiceVmNum;
	}

	public void setUserServiceVmNum(Integer userServiceVmNum) {
		this.userServiceVmNum = userServiceVmNum;
	}

	public Integer getUserServiceStNum() {
		return userServiceStNum;
	}

	public void setUserServiceStNum(Integer userServiceStNum) {
		this.userServiceStNum = userServiceStNum;
	}

	public Integer getUserServiceCpuNum() {
		return userServiceCpuNum;
	}

	public void setUserServiceCpuNum(Integer userServiceCpuNum) {
		this.userServiceCpuNum = userServiceCpuNum;
	}

	public Integer getUserServiceMemoryNum() {
		return userServiceMemoryNum;
	}

	public void setUserServiceMemoryNum(Integer userServiceMemoryNum) {
		this.userServiceMemoryNum = userServiceMemoryNum;
	}

	public Integer getUserServiceStdiskNum() {
		return userServiceStdiskNum;
	}

	public void setUserServiceStdiskNum(Integer userServiceStdiskNum) {
		this.userServiceStdiskNum = userServiceStdiskNum;
	}

	public String getPurchaseLongTime() {
		return purchaseLongTime;
	}

	public void setPurchaseLongTime(String purchaseLongTime) {
		this.purchaseLongTime = purchaseLongTime;
	}

	public long getAllocateStorageCapacity() {
		return allocateStorageCapacity;
	}

	public void setAllocateStorageCapacity(long allocateStorageCapacity) {
		this.allocateStorageCapacity = allocateStorageCapacity;
	}

	public Date getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}

	public Date getBillingEndTime() {
		return billingEndTime;
	}

	public void setBillingEndTime(Date billingEndTime) {
		this.billingEndTime = billingEndTime;
	}

	public Long getMgtObjSid() {
		return mgtObjSid;
	}

	public void setMgtObjSid(Long mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	public String getProcessInstanceChangeId() {
		return processInstanceChangeId;
	}

	public void setProcessInstanceChangeId(String processInstanceChangeId) {
		this.processInstanceChangeId = processInstanceChangeId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getContractFile() {
		return contractFile;
	}

	public void setContractFile(String contractFile) {
		this.contractFile = contractFile;
	}

	public String getFwPort() {
		return fwPort;
	}

	public void setFwPort(String fwPort) {
		this.fwPort = fwPort;
	}

	public String getComputeResSetName() {
		return computeResSetName;
	}

	public void setComputeResSetName(String computeResSetName) {
		this.computeResSetName = computeResSetName;
	}

	public String getInternetVlanName() {
		return internetVlanName;
	}

	public void setInternetVlanName(String internetVlanName) {
		this.internetVlanName = internetVlanName;
	}

	public String getIntranetVlanName() {
		return intranetVlanName;
	}

	public void setIntranetVlanName(String intranetVlanName) {
		this.intranetVlanName = intranetVlanName;
	}

	public String getComputeResSet() {
		return computeResSet;
	}

	public void setComputeResSet(String computeResSet) {
		this.computeResSet = computeResSet;
	}

	public String getInternetVlan() {
		return internetVlan;
	}

	public void setInternetVlan(String internetVlan) {
		this.internetVlan = internetVlan;
	}

	public String getIntranetVlan() {
		return intranetVlan;
	}

	public void setIntranetVlan(String intranetVlan) {
		this.intranetVlan = intranetVlan;
	}

	public Long getParentInstanceSid() {
		return parentInstanceSid;
	}

	public void setParentInstanceSid(Long parentInstanceSid) {
		this.parentInstanceSid = parentInstanceSid;
	}

	public String getoServiceId() {
		return oServiceId;
	}

	public void setoServiceId(String oServiceId) {
		this.oServiceId = oServiceId;
	}

	public Long getIsFree() {
		return isFree;
	}

	public void setIsFree(Long isFree) {
		this.isFree = isFree;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public String getNewDiskSize() {
		return newDiskSize;
	}

	public void setNewDiskSize(String newDiskSize) {
		this.newDiskSize = newDiskSize;
	}

	public Integer getVmCount() {
		return vmCount;
	}

	public void setVmCount(Integer vmCount) {
		this.vmCount = vmCount;
	}

	public Integer getCpuCores() {
		return cpuCores;
	}

	public void setCpuCores(Integer cpuCores) {
		this.cpuCores = cpuCores;
	}

	public Long getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(Long memorySize) {
		this.memorySize = memorySize;
	}

	public Long getTotalStorage() {
		return totalStorage;
	}

	public void setTotalStorage(Long totalStorage) {
		this.totalStorage = totalStorage;
	}

	public Long getAvailableCapacity() {
		return availableCapacity;
	}

	public void setAvailableCapacity(Long availableCapacity) {
		this.availableCapacity = availableCapacity;
	}

	public String getResSid() {
		return resSid;
	}

	public void setResSid(String resSid) {
		this.resSid = resSid;
	}

	public String getIsFreeName() {
		return isFreeName;
	}

	public void setIsFreeName(String isFreeName) {
		this.isFreeName = isFreeName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public String getVdName() {
		return vdName;
	}

	public void setVdName(String vdName) {
		this.vdName = vdName;
	}

	public Integer getVmInstanceCount() {
		return vmInstanceCount;
	}

	public void setVmInstanceCount(Integer vmInstanceCount) {
		this.vmInstanceCount = vmInstanceCount;
	}

	public Integer getBlockStorageInstanceCount() {
		return blockStorageInstanceCount;
	}

	public void setBlockStorageInstanceCount(Integer blockStorageInstanceCount) {
		this.blockStorageInstanceCount = blockStorageInstanceCount;
	}

	public Integer getObjectStorageInstanceCount() {
		return objectStorageInstanceCount;
	}

	public void setObjectStorageInstanceCount(Integer objectStorageInstanceCount) {
		this.objectStorageInstanceCount = objectStorageInstanceCount;
	}

	public Integer getFloatingIpInstanceCount() {
		return floatingIpInstanceCount;
	}

	public void setFloatingIpInstanceCount(Integer floatingIpInstanceCount) {
		this.floatingIpInstanceCount = floatingIpInstanceCount;
	}

	public String getMgtObjName() {
		return mgtObjName;
	}

	public void setMgtObjName(String mgtObjName) {
		this.mgtObjName = mgtObjName;
	}

	public String getMgtObjNameLike() {
		return mgtObjNameLike;
	}

	public void setMgtObjNameLike(String mgtObjNameLike) {
		this.mgtObjNameLike = mgtObjNameLike;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public Integer getBillingDay() {
		return billingDay;
	}

	public void setBillingDay(Integer billingDay) {
		this.billingDay = billingDay;
	}

	public String getServiceConfig() {
		return serviceConfig;
	}

	public void setServiceConfig(String serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	public String getResInsVdStatus() {
		return resInsVdStatus;
	}

	public void setResInsVdStatus(String resInsVdStatus) {
		this.resInsVdStatus = resInsVdStatus;
	}

	public String getResInsVdStatusName() {
		return resInsVdStatusName;
	}

	public void setResInsVdStatusName(String resInsVdStatusName) {
		this.resInsVdStatusName = resInsVdStatusName;
	}

	public String getVisitAddress() {
		return visitAddress;
	}

	public void setVisitAddress(String visitAddress) {
		this.visitAddress = visitAddress;
	}

	public String getResIpSid() {
		return resIpSid;
	}

	public void setResIpSid(String resIpSid) {
		this.resIpSid = resIpSid;
	}

	/**
	 * @return the cdnInstanceCount
	 */
	public Integer getCdnInstanceCount() {
		return cdnInstanceCount;
	}

	/**
	 * @param cdnInstanceCount the cdnInstanceCount to set
	 */
	public void setCdnInstanceCount(Integer cdnInstanceCount) {
		this.cdnInstanceCount = cdnInstanceCount;
	}
	
}