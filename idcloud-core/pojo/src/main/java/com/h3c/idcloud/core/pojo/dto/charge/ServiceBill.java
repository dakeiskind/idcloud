package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceBill implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long serviceBillSid;

    /**
     * 账单ID
     */
    private String billId;

    /**
     * 对应服务实例SID
     */
    private Long serviceInstanceSid;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务代码
     */
    private String serviceCode;

    /**
     * 账期为账单出账的年月
     */
    private String billTime;

    /**
     * 应付金额
     */
    private BigDecimal amount;

    /**
     * 实际支付金额
     */
    private BigDecimal realAmount;

    /**
     * 结算时常
     */
    private Integer duration;

    /**
     * 账单的支付状态 0未支付  1已支付
     */
    private Integer status;

    private Date startTime;

    private Date endTime;

    /**
     * 按量计费的用量，1.针对单一如服务为快存储，此为块存储用量其他类推。 2.如为组合次为json存储{"cpu":"2","memory":"2"}
     */
    private String serviceUsage;

    private Long userSid;

    private Long accountSid;

    /**
     * 资费类型
     */
    private String billingType;

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

    private String startTimeLabel;

    private BigDecimal dayTotalAmount;

    public String getStartTimeLabel() {
        return startTimeLabel;
    }

    public void setStartTimeLabel(String startTimeLabel) {
        this.startTimeLabel = startTimeLabel;
    }

    public BigDecimal getDayTotalAmount() {
        return dayTotalAmount;
    }

    public void setDayTotalAmount(BigDecimal dayTotalAmount) {
        this.dayTotalAmount = dayTotalAmount;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getServiceBillSid() {
        return serviceBillSid;
    }

    public void setServiceBillSid(Long serviceBillSid) {
        this.serviceBillSid = serviceBillSid;
    }

    /**
     * @return 账单ID
     */
    public String getBillId() {
        return billId;
    }

    /**
     * @param billId 
	 *            账单ID
     */
    public void setBillId(String billId) {
        this.billId = billId;
    }

    /**
     * @return 对应服务实例SID
     */
    public Long getServiceInstanceSid() {
        return serviceInstanceSid;
    }

    /**
     * @param serviceInstanceSid 
	 *            对应服务实例SID
     */
    public void setServiceInstanceSid(Long serviceInstanceSid) {
        this.serviceInstanceSid = serviceInstanceSid;
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
     * @return 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName 
	 *            服务名称
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return 账期为账单出账的年月
     */
    public String getBillTime() {
        return billTime;
    }

    /**
     * @param billTime 
	 *            账期为账单出账的年月
     */
    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    /**
     * @return 应付金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            应付金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 实际支付金额
     */
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    /**
     * @param realAmount 
	 *            实际支付金额
     */
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    /**
     * @return 结算时常
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration 
	 *            结算时常
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return 账单的支付状态 0未支付  1已支付
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            账单的支付状态 0未支付  1已支付
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return 按量计费的用量，1.针对单一如服务为快存储，此为块存储用量其他类推。 2.如为组合次为json存储{"cpu":"2","memory":"2"}
     */
    public String getServiceUsage() {
        return serviceUsage;
    }

    /**
     * @param serviceUsage 
	 *            按量计费的用量，1.针对单一如服务为快存储，此为块存储用量其他类推。 2.如为组合次为json存储{"cpu":"2","memory":"2"}
     */
    public void setServiceUsage(String serviceUsage) {
        this.serviceUsage = serviceUsage;
    }

    public Long getUserSid() {
        return userSid;
    }

    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    public Long getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(Long accountSid) {
        this.accountSid = accountSid;
    }

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