package com.h3c.idcloud.core.pojo.dto.charge;

import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BillingPricing implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规格组合信息用于web页面
     */
    private List<BillingPlanSpecVo> billingPlanSpecVoList;

    /**
     * 计费定价SID
     */
    private Long billingPricingSid;

    /**
     * 计费计划SID
     */
    private Long billingPlanSid;

    /**
     * 计费规格项sid，多个以,分隔
     */
    private String billSpecSids;

    /**
     * 不计费规格项sid，多个以,分隔
     */
    private String noBillSpecSids;

    private String billingConfigName;

    private String billingConfigDescription;

    private String chargeType;

    private String chargeTypeName;

    /**
     * 计费类型
     */
    private String billingType;

    private String billingTypeName;

    /**
     * 状态
     */
    private String status;

    private String statusName;

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

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getBillingTypeName() {
        return billingTypeName;
    }

    public void setBillingTypeName(String billingTypeName) {
        this.billingTypeName = billingTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 版本号
     */
    private Long version;

    public List<BillingPlanSpecVo> getBillingPlanSpecVoList() {
        return billingPlanSpecVoList;
    }

    public void setBillingPlanSpecVoList(List<BillingPlanSpecVo> billingPlanSpecVoList) {
        this.billingPlanSpecVoList = billingPlanSpecVoList;
    }

    /**
     * @return 计费定价SID
     */
    public Long getBillingPricingSid() {
        return billingPricingSid;
    }

    /**
     * @param billingPricingSid 
	 *            计费定价SID
     */
    public void setBillingPricingSid(Long billingPricingSid) {
        this.billingPricingSid = billingPricingSid;
    }

    /**
     * @return 计费计划SID
     */
    public Long getBillingPlanSid() {
        return billingPlanSid;
    }

    /**
     * @param billingPlanSid 
	 *            计费计划SID
     */
    public void setBillingPlanSid(Long billingPlanSid) {
        this.billingPlanSid = billingPlanSid;
    }

    /**
     * @return 计费规格项sid，多个以,分隔
     */
    public String getBillSpecSids() {
        return billSpecSids;
    }

    /**
     * @param billSpecSids 
	 *            计费规格项sid，多个以,分隔
     */
    public void setBillSpecSids(String billSpecSids) {
        this.billSpecSids = billSpecSids;
    }

    /**
     * @return 不计费规格项sid，多个以,分隔
     */
    public String getNoBillSpecSids() {
        return noBillSpecSids;
    }

    /**
     * @param noBillSpecSids 
	 *            不计费规格项sid，多个以,分隔
     */
    public void setNoBillSpecSids(String noBillSpecSids) {
        this.noBillSpecSids = noBillSpecSids;
    }

    public String getBillingConfigName() {
        return billingConfigName;
    }

    public void setBillingConfigName(String billingConfigName) {
        this.billingConfigName = billingConfigName;
    }

    public String getBillingConfigDescription() {
        return billingConfigDescription;
    }

    public void setBillingConfigDescription(String billingConfigDescription) {
        this.billingConfigDescription = billingConfigDescription;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
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