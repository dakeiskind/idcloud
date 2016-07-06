package com.h3c.idcloud.core.pojo.dto.marketing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Agreement implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 大客户协议SID
     */
    private Long agreementSid;

    private Long userSid;
    
    /**
     * 附件原文件名
     */
    private String oraginName;

    /**
     * 协议主要信息
     */
    private String brief;

    /**
     * 协议文件
     */
    private String agreementFile;

    /**
     * 协议标题
     */
    private String agreementTitle;

    /**
     * 签订日
     */
    private Date dateSigned;

    /**
     * 签订价格
     */
    private BigDecimal price;

    /**
     * 付款情况描述
     */
    private String payment;

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
    
    private Long accountSid;
    
    private String accountName;    
    
    private String dateSignedFrom;
    
    private String dateSignedTo;

    public String getOraginName() {
		return oraginName;
	}

	public void setOraginName(String oraginName) {
		this.oraginName = oraginName;
	}

	/**
     * @return 大客户协议SID
     */
    public Long getAgreementSid() {
        return agreementSid;
    }

    /**
     * @param agreementSid 
	 *            大客户协议SID
     */
    public void setAgreementSid(Long agreementSid) {
        this.agreementSid = agreementSid;
    }

    public Long getUserSid() {
        return userSid;
    }

    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    /**
     * @return 协议主要信息
     */
    public String getBrief() {
        return brief;
    }

    /**
     * @param brief 
	 *            协议主要信息
     */
    public void setBrief(String brief) {
        this.brief = brief;
    }

    /**
     * @return 协议文件
     */
    public String getAgreementFile() {
        return agreementFile;
    }

    /**
     * @param agreementFile 
	 *            协议文件
     */
    public void setAgreementFile(String agreementFile) {
        this.agreementFile = agreementFile;
    }

    /**
     * @return 协议标题
     */
    public String getAgreementTitle() {
        return agreementTitle;
    }

    /**
     * @param agreementTitle 
	 *            协议标题
     */
    public void setAgreementTitle(String agreementTitle) {
        this.agreementTitle = agreementTitle;
    }

    /**
     * @return 签订日
     */
    public Date getDateSigned() {
        return dateSigned;
    }

    /**
     * @param dateSigned 
	 *            签订日
     */
    public void setDateSigned(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

    /**
     * @return 签订价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            签订价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 付款情况描述
     */
    public String getPayment() {
        return payment;
    }

    /**
     * @param payment 
	 *            付款情况描述
     */
    public void setPayment(String payment) {
        this.payment = payment;
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

	public String getDateSignedFrom() {
		return dateSignedFrom;
	}

	public void setDateSignedFrom(String dateSignedFrom) {
		this.dateSignedFrom = dateSignedFrom;
	}

	public String getDateSignedTo() {
		return dateSignedTo;
	}

	public void setDateSignedTo(String dateSignedTo) {
		this.dateSignedTo = dateSignedTo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(Long accountSid) {
		this.accountSid = accountSid;
	}
}