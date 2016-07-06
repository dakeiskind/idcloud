package com.h3c.idcloud.core.pojo.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户中心用户订单明细vo
 * Created by swq on 3/2/2016.
 */
public class UserOrderDetailVo implements Serializable {


    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 明细金额
     */
    private BigDecimal amount;

    /**
     * 计费类型名称
     */
    private String billingTypeName;

    /**
     * 计费类型Ym名称
     */
    private String billingTypeYmName;

    /**
     * 预计时间
     */
    private Date expectedTime;

    /**
     * 到期时间
     */
    private Date expiringDate;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 配置规格描述
     */
    private String specificationDesc;

    /**
     * 购买时长
     */
    private String purchaseLongTime;

    public String getPurchaseLongTime() {
        return purchaseLongTime;
    }

    public void setPurchaseLongTime(String purchaseLongTime) {
        this.purchaseLongTime = purchaseLongTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBillingTypeName() {
        return billingTypeName;
    }

    public void setBillingTypeName(String billingTypeName) {
        this.billingTypeName = billingTypeName;
    }

    public String getBillingTypeYmName() {
        return billingTypeYmName;
    }

    public void setBillingTypeYmName(String billingTypeYmName) {
        this.billingTypeYmName = billingTypeYmName;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSpecificationDesc() {
        return specificationDesc;
    }

    public void setSpecificationDesc(String specificationDesc) {
        this.specificationDesc = specificationDesc;
    }
}
