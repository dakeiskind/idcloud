package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;

public class BillingType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资费类型
     */
    private String billingType;

    /**
     * 资费类型名称
     */
    private String billingTypeName;

    /**
     * 资费计划类型
     */
    private String billingPlanType;

    /**
     * 单位
     */
    private String unit;

    /**
     * 描述
     */
    private String description;

    /**
     * @return 资费类型
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * @param billingType 
	 *            资费类型
     */
    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    /**
     * @return 资费类型名称
     */
    public String getBillingTypeName() {
        return billingTypeName;
    }

    /**
     * @param billingTypeName 
	 *            资费类型名称
     */
    public void setBillingTypeName(String billingTypeName) {
        this.billingTypeName = billingTypeName;
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
     * @return 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit 
	 *            单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}