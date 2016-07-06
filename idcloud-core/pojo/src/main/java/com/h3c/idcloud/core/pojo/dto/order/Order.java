package com.h3c.idcloud.core.pojo.dto.order;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 当前订单的验证用户信息
	 */
	private AuthUser authUserInfo;

	/**
	 * 订单支付时间
	 */
	private Date timePurchase;

	/**
	 * 定单SID
	 */
	private Long orderSid;

	/**
	 * 定单编号
	 */
	private String orderId;
	
	/**
	 * 订单所属管理对象
	 */
	private String mgtObjSid;

	/**
	 * 定单名称
	 */
	private String orderName;

	/**
	 * 租户ID
	 */
	private String tanentId;

	/**
	 * 租户名称
	 */
	private String tenantName;

	/**
	 * 租户名称简写
	 */
	private String tenantShortName;

	/**
	 * 服务名称
	 */
	private String serviceName;

	/**
	 * 订单总金额
	 */
	private BigDecimal amount;

	/**
	 * 所有者ID
	 */
	private String ownerId;
	/**
	 * 所有者ID
	 */
	private String userName;

	/**
	 * 状态
	 */
	private String status;
	
	/**
	 *申请中
	 */
	private String statusapplication;
	
	/**
	 *已开通
	 */
	private String statusopened;
	
	/**
	 *已退订
	 */
	private String statusrefused;
	

	/**
	 * 状态名称
	 */
	private String statusName;

	/**
	 * 期望开通时间
	 */
	private Date expectedTime;

	/**
	 * 开通时间
	 */
	private Date dredgeDate;

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
	 * 流程实例ID
	 */
	private String processInstanceId;
	/**
	 * 服务ID
	 */
	private Long serviceSid;

	private List<OrderDetail> orderDetail;

	/**
	 * 订单内的虚拟机实例
	 */
	private List<ServiceInstance> vmServiceInstances;

	/**
	 * 当前订单流程是否到达终审
	 * 
	 */
	private Boolean isFinal;

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
	 * 项目文件
	 */
	private String projectFile;	
	
	private String portlaHost;
	
	private String approveOpinion;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 订单类型名称
	 */
	private String orderTypeName;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	/**
	 * @return 定单SID
	 */
	public Long getOrderSid() {
		return orderSid;
	}

	public String getPortlaHost() {
		return portlaHost;
	}

	public void setPortlaHost(String portlaHost) {
		this.portlaHost = portlaHost;
	}

	/**
	 * @param orderSid
	 *            定单SID
	 */
	public void setOrderSid(Long orderSid) {
		this.orderSid = orderSid;
	}

	/**
	 * @return 定单编号
	 */
	public String getOrderId() {
		return orderId;
	}

	public Date getTimePurchase() {
		return timePurchase;
	}

	public void setTimePurchase(Date timePurchase) {
		this.timePurchase = timePurchase;
	}

	/**
	 * @param orderId
	 *            定单编号
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 定单名称
	 */
	public String getOrderName() {
		return orderName;
	}

	public AuthUser getAuthUserInfo() {
		return authUserInfo;
	}

	public void setAuthUserInfo(AuthUser authUserInfo) {
		this.authUserInfo = authUserInfo;
	}

	/**
	 * @param orderName
	 *            定单名称
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return 状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            状态
	 */
	public void setStatus(String status) {
		this.status = status;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the orderDetail
	 */
	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	/**
	 * @param orderDetail
	 *            the orderDetail to set
	 */
	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
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
	 * @return the serviceSid
	 */
	public Long getServiceSid() {
		return serviceSid;
	}

	/**
	 * @param serviceSid
	 *            the serviceSid to set
	 */
	public void setServiceSid(Long serviceSid) {
		this.serviceSid = serviceSid;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the dredgeDate
	 */
	public Date getDredgeDate() {
		return dredgeDate;
	}

	/**
	 * @param dredgeDate
	 *            the dredgeDate to set
	 */
	public void setDredgeDate(Date dredgeDate) {
		this.dredgeDate = dredgeDate;
	}

	/**
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}

	/**
	 * @param tenantName
	 *            the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
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

	public List<ServiceInstance> getVmServiceInstances() {
		return vmServiceInstances;
	}

	public void setVmServiceInstances(List<ServiceInstance> vmServiceInstances) {
		this.vmServiceInstances = vmServiceInstances;
	}

	public String getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	public String getStatusapplication() {
		return statusapplication;
	}

	public void setStatusapplication(String statusapplication) {
		this.statusapplication = statusapplication;
	}

	public String getStatusopened() {
		return statusopened;
	}

	public void setStatusopened(String statusopened) {
		this.statusopened = statusopened;
	}

	public String getStatusrefused() {
		return statusrefused;
	}

	public String getMgtObjSid() {
		return mgtObjSid;
	}

	public void setMgtObjSid(String mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	public void setStatusrefused(String statusrefused) {
		this.statusrefused = statusrefused;
	}

	public String getProjectFile() {
		return projectFile;
	}

	public void setProjectFile(String projectFile) {
		this.projectFile = projectFile;
	}	

}