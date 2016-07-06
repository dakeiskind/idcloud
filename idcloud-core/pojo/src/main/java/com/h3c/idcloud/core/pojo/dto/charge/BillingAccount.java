package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillingAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6851772324659966012L;

	private Long accountSid;
	
	private BigDecimal balance;
	
	private String status;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;
    
    private String statusName;

	/**
	 * 账户名称
	 */
	private String accountName;

	/**
	 * 用户积分
	 */
	private Long usableCredit;

	/**
	 * 赠送余额(充值送+礼品卡)
	 */
	private BigDecimal giftBalance;

	/**
	 * 赠送空间
	 */
	private Long defaultSpace;

	/**
	 * 购买空间
	 */
	private Long orderSpace;

	private Long accountLevelSid;

	private String accountType;

	private String accountTypeName;

	private String accountLevelName;


	public Long getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(Long accountSid) {
		this.accountSid = accountSid;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getUsableCredit() {
		return usableCredit;
	}

	public void setUsableCredit(Long usableCredit) {
		this.usableCredit = usableCredit;
	}

	public BigDecimal getGiftBalance() {
		return giftBalance;
	}

	public void setGiftBalance(BigDecimal giftBalance) {
		this.giftBalance = giftBalance;
	}

	public Long getDefaultSpace() {
		return defaultSpace;
	}

	public void setDefaultSpace(Long defaultSpace) {
		this.defaultSpace = defaultSpace;
	}

	public Long getOrderSpace() {
		return orderSpace;
	}

	public void setOrderSpace(Long orderSpace) {
		this.orderSpace = orderSpace;
	}

	public Long getAccountLevelSid() {
		return accountLevelSid;
	}

	public void setAccountLevelSid(Long accountLevelSid) {
		this.accountLevelSid = accountLevelSid;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getAccountLevelName() {
		return accountLevelName;
	}

	public void setAccountLevelName(String accountLevelName) {
		this.accountLevelName = accountLevelName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}
