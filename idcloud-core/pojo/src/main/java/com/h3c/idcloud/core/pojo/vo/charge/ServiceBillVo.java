package com.h3c.idcloud.core.pojo.vo.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by swq on 4/8/2016.
 */
public class ServiceBillVo implements Serializable {

    /**
     * 账单ID
     */
    private String billId;


    /**
     * 账期为账单出账的年月
     */
    private String billTime;

    /**
     * 消费开始时间
     */
    private String startTime;

    /**
     * 消费结束时间
     */
    private String endTime;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 资费类型
     */
    private String billingTypeName;

    /**
     * 应付金额
     */
    private BigDecimal amount;

    /**
     * 实际支付金额
     */
    private BigDecimal realAmount;

    /**
     * 账单的支付状态 0未支付  1已支付
     */
    private String statusName;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBillingTypeName() {
        return billingTypeName;
    }

    public void setBillingTypeName(String billingTypeName) {
        this.billingTypeName = billingTypeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
