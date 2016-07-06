package com.h3c.idcloud.core.pojo.vo.charge;

import java.io.Serializable;

/**
 * Created by swq on 3/9/2016.
 */
public class BillingPlanSpecVo implements Serializable {

    private Long sid;
    /**
     * 是否选择
     */
    private boolean isSelected = false;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 单位
     */
    private String unit;

    /**
     * 取值范围
     */
    private String valueDomain;

    /**
     * 是否参与计费
     */
    private boolean isBill = false;

    /**
     * 是否计费显示
     */
    private String billLabel;

    public String getBillLabel() {
        return billLabel;
    }

    public void setBillLabel(String billLabel) {
        this.billLabel = billLabel;
    }

    /**
     * 计费固定值，此值对于计费项目来说默认yes，非计费项目为手动填写
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValueDomain() {
        return valueDomain;
    }

    public void setValueDomain(String valueDomain) {
        this.valueDomain = valueDomain;
    }

    public boolean isBill() {
        return isBill;
    }

    public void setBill(boolean bill) {
        isBill = bill;
    }
}
