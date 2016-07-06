package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.util.Date;

public class BillingPlanSpec implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long billingPlanSpecSid;

    /**
     * 规格项sids 多个以,分隔
     */
    private String specSids;

    /**
     * 规格项name 多个以,分隔
     */
    private String specNames;

    /**
     * 资费计划SID
     */
    private Long billingPlanSid;

    /**
     * 服务规格SID
     */
    private Long serviceSpecSid;

    /**
     * 规格名称
     */
    private String specName;

    /**
     * 规格描述
     */
    private String specDescription;

    /**
     * 0否 1是
     */
    private Integer isBill;

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

    public String getSpecNames() {
        return specNames;
    }

    public void setSpecNames(String specNames) {
        this.specNames = specNames;
    }

    public Long getBillingPlanSpecSid() {
        return billingPlanSpecSid;
    }

    public void setBillingPlanSpecSid(Long billingPlanSpecSid) {
        this.billingPlanSpecSid = billingPlanSpecSid;
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
     * @return 服务规格SID
     */
    public Long getServiceSpecSid() {
        return serviceSpecSid;
    }

    public String getSpecSids() {
        return specSids;
    }

    public void setSpecSids(String specSids) {
        this.specSids = specSids;
    }

    /**
     * @param serviceSpecSid 
	 *            服务规格SID
     */
    public void setServiceSpecSid(Long serviceSpecSid) {
        this.serviceSpecSid = serviceSpecSid;
    }

    /**
     * @return 规格名称
     */
    public String getSpecName() {
        return specName;
    }

    /**
     * @param specName 
	 *            规格名称
     */
    public void setSpecName(String specName) {
        this.specName = specName;
    }

    /**
     * @return 规格描述
     */
    public String getSpecDescription() {
        return specDescription;
    }

    /**
     * @param specDescription 
	 *            规格描述
     */
    public void setSpecDescription(String specDescription) {
        this.specDescription = specDescription;
    }

    /**
     * @return 0否 1是
     */
    public Integer getIsBill() {
        return isBill;
    }

    /**
     * @param isBill 
	 *            0否 1是
     */
    public void setIsBill(Integer isBill) {
        this.isBill = isBill;
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