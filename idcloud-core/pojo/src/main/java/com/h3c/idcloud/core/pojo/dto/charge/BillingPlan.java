package com.h3c.idcloud.core.pojo.dto.charge;

import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BillingPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资费计划SID
     */
    private Long billingPlanSid;

    /**
     * 资费计划名
     */
    private String billingPlanName;

    /**
     * 资费计划类型
     */
    private String billingPlanType;
    
    /**
     * 资费计划类型
     */
    private String billingPlanTypeName;

    /**
     * 服务SID
     */
    private Long serviceSid;
    
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 计划状态(01:启用, 02:禁用)
     */
    private String planStatus;
    /**
     * 计划状态(01:启用, 02:禁用)
     */
    private String planStatusName;

    /**
     * 计划适用范围
     */
    private String planScope;

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
     * 规格
     */
    private List<BillingPlanSpecVo> billingPlanSpecVos;

    public List<BillingPlanSpecVo> getBillingPlanSpecVos() {
        return billingPlanSpecVos;
    }

    public void setBillingPlanSpecVos(List<BillingPlanSpecVo> billingPlanSpecVos) {
        this.billingPlanSpecVos = billingPlanSpecVos;
    }

    /**
     * @return 资费计划SID
     */
    public Long getBillingPlanSid() {
        return billingPlanSid;
    }

    /**
     * @param billingPlanSid 
	 *            资费计划SID
     */
    public void setBillingPlanSid(Long billingPlanSid) {
        this.billingPlanSid = billingPlanSid;
    }

    /**
     * @return 资费计划名
     */
    public String getBillingPlanName() {
        return billingPlanName;
    }

    /**
     * @param billingPlanName 
	 *            资费计划名
     */
    public void setBillingPlanName(String billingPlanName) {
        this.billingPlanName = billingPlanName;
    }

    /**
     * @return 资费计划类型
     */
    public String getBillingPlanType() {
        return billingPlanType;
    }

    /**
     * @param billingPlanType 
	 *            资费计划类型
     */
    public void setBillingPlanType(String billingPlanType) {
        this.billingPlanType = billingPlanType;
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
     * @return 计划状态(01:启用, 02:禁用)
     */
    public String getPlanStatus() {
        return planStatus;
    }

    /**
     * @param planStatus 
	 *            计划状态(01:启用, 02:禁用)
     */
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * @return 计划适用范围
     */
    public String getPlanScope() {
        return planScope;
    }

    /**
     * @param planScope 
	 *            计划适用范围
     */
    public void setPlanScope(String planScope) {
        this.planScope = planScope;
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

	/**
	 * @return the billingPlanTypeName
	 */
	public String getBillingPlanTypeName() {
		return billingPlanTypeName;
	}

	/**
	 * @param billingPlanTypeName the billingPlanTypeName to set
	 */
	public void setBillingPlanTypeName(String billingPlanTypeName) {
		this.billingPlanTypeName = billingPlanTypeName;
	}

	

	public String getPlanStatusName() {
		return planStatusName;
	}

	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
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
    
}