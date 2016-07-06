package com.h3c.idcloud.core.pojo.vo.charge;

import java.io.Serializable;

/**
 * 用于页面传输参数vo定义
 * Created by swq on 4/15/2016.
 */
public class PricingDetailParamVo implements Serializable{

    /**
     * 规格项sid这里指的是plan spec定义的规格sid不是罪原始服务规格sid
     */
    private Long specSid;

    /**
     * 计费规格名称en
     */
    private String name;

    /**
     * 资费组合sid
     */
    private Long billingPricingSid;

    /**
     * 组合计费项规格中文名称
     */
    private String moduleName;

    /**
     * 组合计费项规格项名称en
     */
    private String moduleCode;

    /**
     * 组合计费项规格值 1.参与计价 可区间 1~100*1  可固定值 1 2.不参与计价 任意定义可匹配值
     */
    private String moduleValue;

    /**
     * 组合计费项计价类型 00不计价  01增量计费  02固定计费
     */
    private String billingChargeType;

    /**
     * 组合计费项单价，1.如果是增量则为增量单价 2.如果是固定则为固定值单价
     */
    private String unitPrice;

    /**
     * 初始价格，只针对增量计费有关为值初始价格，固定计费没有此项且无意义
     */
    private String initPrice;

    /**
     * 当前组合明细的折扣
     */
    private Float discount = 1F;

    public Long getSpecSid() {
        return specSid;
    }

    public void setSpecSid(Long specSid) {
        this.specSid = specSid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBillingPricingSid() {
        return billingPricingSid;
    }

    public void setBillingPricingSid(Long billingPricingSid) {
        this.billingPricingSid = billingPricingSid;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleValue() {
        return moduleValue;
    }

    public void setModuleValue(String moduleValue) {
        this.moduleValue = moduleValue;
    }

    public String getBillingChargeType() {
        return billingChargeType;
    }

    public void setBillingChargeType(String billingChargeType) {
        this.billingChargeType = billingChargeType;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(String initPrice) {
        this.initPrice = initPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }
}
