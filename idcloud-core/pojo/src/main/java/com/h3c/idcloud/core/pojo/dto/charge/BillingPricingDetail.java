package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.util.Date;

public class BillingPricingDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 计费定价明细SID
     */
    private Long pricingDetailSid;

    /**
     * 计费定价SID
     */
    private Long billingPricingSid;

    /**
     * 服务规则SID
     */
    private Long specSid;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 1.可为固定值数字，<br>
	 *             2.也可为区间值：5-11*2 表示区间为5-11间隔为2, <br>
	 *             3.也可以为OTHER，other为特殊意义表示除配置外的其他所有项的价格定义
     */
    private String value;

    /**
     * 计价单位
     */
    private String chargeUnit;

    /**
     * 价格，如果为固定价格此为固定价格，如果配置值为区间为区间则此单价为增量单价
     */
    private Float unitPrice;

    /**
     * 0:不满一月不按月计费；1:不满一月按月计费
     */
    private Long isMonth;

    /**
     * 折扣率
     */
    private Float discount;

    /**
     * 状态(01:启用, 02:禁用)
     */
    private String status;

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
     * @return 计费定价明细SID
     */
    public Long getPricingDetailSid() {
        return pricingDetailSid;
    }

    /**
     * @param pricingDetailSid 
	 *            计费定价明细SID
     */
    public void setPricingDetailSid(Long pricingDetailSid) {
        this.pricingDetailSid = pricingDetailSid;
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
     * @return 服务规则SID
     */
    public Long getSpecSid() {
        return specSid;
    }

    /**
     * @param specSid 
	 *            服务规则SID
     */
    public void setSpecSid(Long specSid) {
        this.specSid = specSid;
    }

    /**
     * @return 规格名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            规格名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 1.可为固定值数字，<br>
	 *                     2.也可为区间值：5-11*2 表示区间为5-11间隔为2, <br>
	 *                     3.也可以为OTHER，other为特殊意义表示除配置外的其他所有项的价格定义
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 
	 *            1.可为固定值数字，<br>
	 *                        2.也可为区间值：5-11*2 表示区间为5-11间隔为2, <br>
	 *                        3.也可以为OTHER，other为特殊意义表示除配置外的其他所有项的价格定义
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return 计价单位
     */
    public String getChargeUnit() {
        return chargeUnit;
    }

    /**
     * @param chargeUnit 
	 *            计价单位
     */
    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    /**
     * @return 价格，如果为固定价格此为固定价格，如果配置值为区间为区间则此单价为增量单价
     */
    public Float getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice 
	 *            价格，如果为固定价格此为固定价格，如果配置值为区间为区间则此单价为增量单价
     */
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return 0:不满一月不按月计费；1:不满一月按月计费
     */
    public Long getIsMonth() {
        return isMonth;
    }

    /**
     * @param isMonth 
	 *            0:不满一月不按月计费；1:不满一月按月计费
     */
    public void setIsMonth(Long isMonth) {
        this.isMonth = isMonth;
    }

    /**
     * @return 折扣率
     */
    public Float getDiscount() {
        return discount;
    }

    /**
     * @param discount 
	 *            折扣率
     */
    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    /**
     * @return 状态(01:启用, 02:禁用)
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态(01:启用, 02:禁用)
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