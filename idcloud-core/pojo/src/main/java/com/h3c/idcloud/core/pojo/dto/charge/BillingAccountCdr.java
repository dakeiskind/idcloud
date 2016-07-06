package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillingAccountCdr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6833116452451733376L;

	private Long cdrSid;
	
	private Long billingAccountSid;
	
	private String opType;
	
	private String tradeNo;
	
	private String opUser;
	
	private Date opDate;
	
	private BigDecimal opAmount;
	
	private BigDecimal balance;
	
	private String status;
	
	private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;

    private String opTypeName;

    private String statusName;

	public Long getCdrSid() {
		return cdrSid;
	}

	public void setCdrSid(Long cdrSid) {
		this.cdrSid = cdrSid;
	}

	public Long getBillingAccountSid() {
		return billingAccountSid;
	}

	public void setBillingAccountSid(Long billingAccountSid) {
		this.billingAccountSid = billingAccountSid;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public BigDecimal getOpAmount() {
		return opAmount;
	}

	public void setOpAmount(BigDecimal opAmount) {
		this.opAmount = opAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getOpTypeName() {
		return opTypeName;
	}

	public void setOpTypeName(String opTypeName) {
		this.opTypeName = opTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
