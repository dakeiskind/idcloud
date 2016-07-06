package com.h3c.idcloud.core.pojo.dto.marketing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GiftCardBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 生成批次SID
     */
    private Long batchSid;
    
    /**
     * 未激活礼品卡数
     */
    private Integer inactiveNumber;

    public Integer getInactiveNumber() {
		return inactiveNumber;
	}

	public void setInactiveNumber(Integer inactiveNumber) {
		this.inactiveNumber = inactiveNumber;
	}

	/**
     * 礼品卡名称
     */
    private String cardName;

    /**
     * 批次号: yyyymmddhh24mmss
     */
    private String batchNo;

    /**
     * 面额
     */
    private BigDecimal faceValue;

    /**
     * 有效期开始时间
     */
    private Date validStart;

    private String validStartStr;

    /**
     * 有效期截止时间
     */
    private Date validTo;

    private String validToStr;

    public String getValidStartStr() {
        return validStartStr;
    }

    public void setValidStartStr(String validStartStr) {
        this.validStartStr = validStartStr;
    }

    public String getValidToStr() {
        return validToStr;
    }

    public void setValidToStr(String validToStr) {
        this.validToStr = validToStr;
    }

    /**
     * 生成数量
     */
    private Integer quantity;

    /**
     * 状态<br>
	 *             0 - 未生成<br>
	 *             1 - 已生成<br>
	 *             2 - 已作废
     */
    private Integer status;

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
     * @return 生成批次SID
     */
    public Long getBatchSid() {
        return batchSid;
    }

    /**
     * @param batchSid 
	 *            生成批次SID
     */
    public void setBatchSid(Long batchSid) {
        this.batchSid = batchSid;
    }

    /**
     * @return 礼品卡名称
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName 
	 *            礼品卡名称
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return 批次号: yyyymmddhh24mmss
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo 
	 *            批次号: yyyymmddhh24mmss
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return 面额
     */
    public BigDecimal getFaceValue() {
        return faceValue;
    }

    /**
     * @param faceValue 
	 *            面额
     */
    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * @return 有效期开始时间
     */
    public Date getValidStart() {
        return validStart;
    }

    /**
     * @param validStart 
	 *            有效期开始时间
     */
    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    /**
     * @return 有效期截止时间
     */
    public Date getValidTo() {
        return validTo;
    }

    /**
     * @param validTo 
	 *            有效期截止时间
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    /**
     * @return 生成数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity 
	 *            生成数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return 状态<br>
	 *                     0 - 未生成<br>
	 *                     1 - 已生成<br>
	 *                     2 - 已作废
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态<br>
	 *                        0 - 未生成<br>
	 *                        1 - 已生成<br>
	 *                        2 - 已作废
     */
    public void setStatus(Integer status) {
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