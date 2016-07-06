package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class ApproveRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long approveRecordSid;

    private String processInstanceId;
    
    private Long processObjectId;

    private String processType;
    
    private String processTypeName;

    private String approvorId;
    
    private String approvorAction;

    private String approvorActionName;
    
    private String approveStatus;
    
    private String approveStatusName;

    private String approveOpinion;

    private Date approveDate;
    
    private String approveObject;
    
    private Long approveObjectId;
    
    private String target;
    

    /**
     * 定单编号
     */
    private String orderId;
    
    /**
     * 租户ID
     */
    private String tanentId;
    
    /**
     * 租户名称
     */
    private String tenantName;
    
    /**
     * 状态
     */
    private String serviceName;

    /**
     * 变更审核目标版本
     */
    private Long processTargetId;

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
     * 申请人
     */
    private String proposeBy;

    /**
     * 申请时间
     */
    private Date proposeDt;

    public Long getApproveRecordSid() {
        return approveRecordSid;
    }

    public void setApproveRecordSid(Long approveRecordSid) {
        this.approveRecordSid = approveRecordSid;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getApprovorId() {
        return approvorId;
    }

    public void setApprovorId(String approvorId) {
        this.approvorId = approvorId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

	/**
	 * @return the approveStatusName
	 */
	public String getApproveStatusName() {
		return approveStatusName;
	}

	/**
	 * @param approveStatusName the approveStatusName to set
	 */
	public void setApproveStatusName(String approveStatusName) {
		this.approveStatusName = approveStatusName;
	}

	/**
	 * @return the processTypeName
	 */
	public String getProcessTypeName() {
		return processTypeName;
	}

	/**
	 * @param processTypeName the processTypeName to set
	 */
	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the tanentId
	 */
	public String getTanentId() {
		return tanentId;
	}

	/**
	 * @param tanentId the tanentId to set
	 */
	public void setTanentId(String tanentId) {
		this.tanentId = tanentId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the approveObject
	 */
	public String getApproveObject() {
		return approveObject;
	}

	/**
	 * @param approveObject the approveObject to set
	 */
	public void setApproveObject(String approveObject) {
		this.approveObject = approveObject;
	}

	/**
	 * @return the approvorAction
	 */
	public String getApprovorAction() {
		return approvorAction;
	}

	/**
	 * @param approvorAction the approvorAction to set
	 */
	public void setApprovorAction(String approvorAction) {
		this.approvorAction = approvorAction;
	}

	/**
	 * @return the approvorActionName
	 */
	public String getApprovorActionName() {
		return approvorActionName;
	}

	/**
	 * @param approvorActionName the approvorActionName to set
	 */
	public void setApprovorActionName(String approvorActionName) {
		this.approvorActionName = approvorActionName;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}

	/**
	 * @param tenantName the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public Long getApproveObjectId() {
		return approveObjectId;
	}

	public void setApproveObjectId(Long approveObjectId) {
		this.approveObjectId = approveObjectId;
	}

	public Long getProcessObjectId() {
		return processObjectId;
	}

	public void setProcessObjectId(Long processObjectId) {
		this.processObjectId = processObjectId;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getProcessTargetId() {
		return processTargetId;
	}

	public void setProcessTargetId(Long processTargetId) {
		this.processTargetId = processTargetId;
	}

	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getProposeBy() {
		return proposeBy;
	}

	public void setProposeBy(String proposeBy) {
		this.proposeBy = proposeBy;
	}

	public Date getProposeDt() {
		return proposeDt;
	}

	public void setProposeDt(Date proposeDt) {
		this.proposeDt = proposeDt;
	}

}