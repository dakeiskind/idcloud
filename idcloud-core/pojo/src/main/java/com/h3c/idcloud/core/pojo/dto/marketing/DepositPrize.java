package com.h3c.idcloud.core.pojo.dto.marketing;

import java.io.Serializable;
import java.util.Date;

public class DepositPrize implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 充值奖励优惠SID
     */
    private Long depositPrizeSid;

    /**
     * 充值奖励名称
     */
    private String title;

    /**
     * 描述
     */
    private String des;

    /**
     * 有效期开始
     */
    private Date validStartDt;
    
    private String validStartDtStr;

    /**
     * 有效期截至
     */
    private Date validToDt;
    
    private String validToDtStr;

    /**
     * 充值额度1
     */
    private Long minDeposit1;

    /**
     * 送现金1
     */
    private Long cashGiven1;

    /**
     * 充值额度2
     */
    private Long minDeposit2;

    /**
     * 送现金2
     */
    private Long cashGiven2;

    /**
     * 充值额度3
     */
    private Long minDeposit3;

    /**
     * 送现金3
     */
    private Long cashGiven3;

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

    public String getValidStartDtStr() {
		return validStartDtStr;
	}

	public void setValidStartDtStr(String validStartDtStr) {
		this.validStartDtStr = validStartDtStr;
	}

	public String getValidToDtStr() {
		return validToDtStr;
	}

	public void setValidToDtStr(String validToDtStr) {
		this.validToDtStr = validToDtStr;
	}

	/**
     * @return 充值奖励优惠SID
     */
    public Long getDepositPrizeSid() {
        return depositPrizeSid;
    }

    /**
     * @param depositPrizeSid 
	 *            充值奖励优惠SID
     */
    public void setDepositPrizeSid(Long depositPrizeSid) {
        this.depositPrizeSid = depositPrizeSid;
    }

    /**
     * @return 充值奖励名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            充值奖励名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 描述
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des 
	 *            描述
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * @return 有效期开始
     */
    public Date getValidStartDt() {
        return validStartDt;
    }

    /**
     * @param validStartDt 
	 *            有效期开始
     */
    public void setValidStartDt(Date validStartDt) {
        this.validStartDt = validStartDt;
    }

    /**
     * @return 有效期截至
     */
    public Date getValidToDt() {
        return validToDt;
    }

    /**
     * @param validToDt 
	 *            有效期截至
     */
    public void setValidToDt(Date validToDt) {
        this.validToDt = validToDt;
    }

    /**
     * @return 充值额度1
     */
    public Long getMinDeposit1() {
        return minDeposit1;
    }

    /**
     * @param minDeposit1 
	 *            充值额度1
     */
    public void setMinDeposit1(Long minDeposit1) {
        this.minDeposit1 = minDeposit1;
    }

    /**
     * @return 送现金1
     */
    public Long getCashGiven1() {
        return cashGiven1;
    }

    /**
     * @param cashGiven1 
	 *            送现金1
     */
    public void setCashGiven1(Long cashGiven1) {
        this.cashGiven1 = cashGiven1;
    }

    /**
     * @return 充值额度2
     */
    public Long getMinDeposit2() {
        return minDeposit2;
    }

    /**
     * @param minDeposit2 
	 *            充值额度2
     */
    public void setMinDeposit2(Long minDeposit2) {
        this.minDeposit2 = minDeposit2;
    }

    /**
     * @return 送现金2
     */
    public Long getCashGiven2() {
        return cashGiven2;
    }

    /**
     * @param cashGiven2 
	 *            送现金2
     */
    public void setCashGiven2(Long cashGiven2) {
        this.cashGiven2 = cashGiven2;
    }

    /**
     * @return 充值额度3
     */
    public Long getMinDeposit3() {
        return minDeposit3;
    }

    /**
     * @param minDeposit3 
	 *            充值额度3
     */
    public void setMinDeposit3(Long minDeposit3) {
        this.minDeposit3 = minDeposit3;
    }

    /**
     * @return 送现金3
     */
    public Long getCashGiven3() {
        return cashGiven3;
    }

    /**
     * @param cashGiven3 
	 *            送现金3
     */
    public void setCashGiven3(Long cashGiven3) {
        this.cashGiven3 = cashGiven3;
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