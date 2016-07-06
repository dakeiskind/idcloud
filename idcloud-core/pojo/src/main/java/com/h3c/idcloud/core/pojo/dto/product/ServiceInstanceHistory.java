package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceInstanceHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务实例SID
     */
    private Long instanceSid;

    /**
     * 表主键
     */
    private Long instanceHistorySid;

    /**
     * 服务SID
     */
    private Long serviceSid;

    private Long parentInstanceSid;

    private String oServiceId;

    /**
     * 服务模板SID
     */
    private Long templateSid;

    /**
     * 实例名称
     */
    private String instanceName;

    private String instanceId;

    private String resInstName;

    /**
     * 开通流程实例ID
     */
    private String processInstanceId;

    /**
     * 退订流程实例ID
     */
    private String processInstanceCancelId;

    /**
     * 流程执行失败描述
     */
    private String processFailedDesc;

    /**
     * 服务异常描述
     */
    private String exceptionDesc;

    /**
     * 计费类型
     */
    private String billingType;

    private String billingTypeId;

    /**
     * 购买时长
     */
    private Long buyLength;

    private String contractId;

    private String projectId;

    private String contractFile;

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
     * 订单ID
     */
    private String orderId;

    /**
     * 定单明细SID
     */
    private Long detailSid;

    /**
     * 实例状态
     */
    private String status;

    /**
     * 目标
     */
    private String target;

    /**
     * 租户ID
     */
    private String tanentId;

    /**
     * 期望开通时间
     */
    private Date expectedTime;

    /**
     * 开通时间
     */
    private Date dredgeDate;

    private Date billingEndTime;

    private Long isFree;

    private Long mgtObjSid;

    /**
     * 实例创建开始时间
     */
    private Date creationDateBegin;

    /**
     * 实例创建结束时间
     */
    private Date creationDateEnd;

    private String specification;

    private Date instanceDelTime;

    private String regionSid;

    private String zoneSid;

    private BigDecimal amount;

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
     * @return 表主键
     */
    public Long getInstanceHistorySid() {
        return instanceHistorySid;
    }

    /**
     * @param instanceHistorySid 
	 *            表主键
     */
    public void setInstanceHistorySid(Long instanceHistorySid) {
        this.instanceHistorySid = instanceHistorySid;
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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getResInstName() {
        return resInstName;
    }

    public void setResInstName(String resInstName) {
        this.resInstName = resInstName;
    }

    /**
     * @return 开通流程实例ID
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @param processInstanceId 
	 *            开通流程实例ID
     */
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * @return 退订流程实例ID
     */
    public String getProcessInstanceCancelId() {
        return processInstanceCancelId;
    }

    /**
     * @param processInstanceCancelId 
	 *            退订流程实例ID
     */
    public void setProcessInstanceCancelId(String processInstanceCancelId) {
        this.processInstanceCancelId = processInstanceCancelId;
    }

    /**
     * @return 流程执行失败描述
     */
    public String getProcessFailedDesc() {
        return processFailedDesc;
    }

    /**
     * @param processFailedDesc 
	 *            流程执行失败描述
     */
    public void setProcessFailedDesc(String processFailedDesc) {
        this.processFailedDesc = processFailedDesc;
    }

    /**
     * @return 服务异常描述
     */
    public String getExceptionDesc() {
        return exceptionDesc;
    }

    /**
     * @param exceptionDesc 
	 *            服务异常描述
     */
    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
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

    public String getBillingTypeId() {
        return billingTypeId;
    }

    public void setBillingTypeId(String billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    /**
     * @return 购买时长
     */
    public Long getBuyLength() {
        return buyLength;
    }

    /**
     * @param buyLength 
	 *            购买时长
     */
    public void setBuyLength(Long buyLength) {
        this.buyLength = buyLength;
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
     * @return 定单明细SID
     */
    public Long getDetailSid() {
        return detailSid;
    }

    /**
     * @param detailSid 
	 *            定单明细SID
     */
    public void setDetailSid(Long detailSid) {
        this.detailSid = detailSid;
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
     * @return 期望开通时间
     */
    public Date getExpectedTime() {
        return expectedTime;
    }

    /**
     * @param expectedTime 
	 *            期望开通时间
     */
    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
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

    public Date getBillingEndTime() {
        return billingEndTime;
    }

    public void setBillingEndTime(Date billingEndTime) {
        this.billingEndTime = billingEndTime;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Date getInstanceDelTime() {
        return instanceDelTime;
    }

    public void setInstanceDelTime(Date instanceDelTime) {
        this.instanceDelTime = instanceDelTime;
    }

    public String getRegionSid() {
        return regionSid;
    }

    public void setRegionSid(String regionSid) {
        this.regionSid = regionSid;
    }

    public String getZoneSid() {
        return zoneSid;
    }

    public void setZoneSid(String zoneSid) {
        this.zoneSid = zoneSid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}