package com.h3c.idcloud.core.pojo.dto.marketing;

import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.user.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class GiftCard implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
	 * 是否自动开始
	 */
	private String isAutoStart;

    /**
     * 礼品卡SID
     */
    private Long cardSid;

    /**
     * 生成批次SID
     */
    private Long batchSid;

    /**
     * 生成批次号
     */
    private String batchNo;

    /**
     * 礼品卡名称
     */
    private String cardName;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 礼品卡卡密 16为 数字+英文  4个一组<br>
	 *             
     */
    private String cardPassword;

    /**
     * 有效期开始时间
     */
    private Date validStart;

    /**
     * 有效期截止时间
     */
    private Date validTo;

    /**
     * 面额,单位元
     */
    private BigDecimal faceValue;

    /**
     * 0  未激活   刚创建成功<br>
	 *             1  已激活   创建后需要激活后才能使用<br>
	 *             2  已充值<br>
	 *             3  已作废<br>
	 *             
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 激活时间
     */
    private Date activatedTime;

    /**
     * 激活人
     */
    private Long activatedUserSid;

    /**
     * 充值用户
     */
    private String chargeAccountSid;

    /**
     * 充值时间
     */
    private Date chargeTime;

    /**
     * 充值操作人
     */
    private Long chargeOperUserSid;
    
    /**
     * 礼品卡分发人
     */
    private Long distributeUserSid;
    
    private String disOwner;
    
    private User distributeUser;



    public User getDistributeUser() {
		return distributeUser;
	}

	public void setDistributeUser(User distributeUser) {
		this.distributeUser = distributeUser;
	}

	private String userClient;

    
    public String getUserClient() {
		return userClient;
	}

	public void setUserClient(String userClient) {
		this.userClient = userClient;
	}

	private User chargeUser;
    
    private BillingAccount chargeAccount;

    
    public Long getDistributeUserSid() {
		return distributeUserSid;
	}

	public void setDistributeUserSid(Long distributeUserSid) {
		this.distributeUserSid = distributeUserSid;
	}

	
	public String getChargeAccountSid() {
		return chargeAccountSid;
	}

	public void setChargeAccountSid(String chargeAccountSid) {
		this.chargeAccountSid = chargeAccountSid;
	}

	public void setChargeAccount(BillingAccount chargeAccount) {
		this.chargeAccount = chargeAccount;
	}

	public User getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(User chargetUser) {
		this.chargeUser = chargetUser;
	}

	public String getIsAutoStart() {
		return isAutoStart;
	}

	public void setIsAutoStart(String isAutoStart) {
		this.isAutoStart = isAutoStart;
	}

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
     * @return 礼品卡SID
     */
    public Long getCardSid() {
        return cardSid;
    }

    /**
     * @param cardSid 
	 *            礼品卡SID
     */
    public void setCardSid(Long cardSid) {
        this.cardSid = cardSid;
    }

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
     * @return 卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo 
	 *            卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return 礼品卡卡密 16为 数字+英文  4个一组<br>
	 *                     
     */
    public String getCardPassword() {
        return cardPassword;
    }

    /**
     * @param cardPassword 
	 *            礼品卡卡密 16为 数字+英文  4个一组<br>
	 *                        
     */
    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
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
     * @return 面额,单位元
     */
    public BigDecimal getFaceValue() {
        return faceValue;
    }

    /**
     * @param faceValue 
	 *            面额,单位元
     */
    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * @return 0  未激活   刚创建成功<br>
	 *                     1  已激活   创建后需要激活后才能使用<br>
	 *                     2  已充值<br>
	 *                     3  已作废<br>
	 *                     
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            0  未激活   刚创建成功<br>
	 *                        1  已激活   创建后需要激活后才能使用<br>
	 *                        2  已充值<br>
	 *                        3  已作废<br>
	 *                        
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return 激活时间
     */
    public Date getActivatedTime() {
        return activatedTime;
    }

    /**
     * @param activatedTime 
	 *            激活时间
     */
    public void setActivatedTime(Date activatedTime) {
        this.activatedTime = activatedTime;
    }

    /**
     * @return 激活人
     */
    public Long getActivatedUserSid() {
        return activatedUserSid;
    }

    /**
     * @param activatedUserSid 
	 *            激活人
     */
    public void setActivatedUserSid(Long activatedUserSid) {
        this.activatedUserSid = activatedUserSid;
    }

    public BillingAccount getChargeAccount() {
		return chargeAccount;
	}

	/**
     * @return 充值时间
     */
    public Date getChargeTime() {
        return chargeTime;
    }

    /**
     * @param chargeTime 
	 *            充值时间
     */
    public void setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
    }

    /**
     * @return 充值操作人
     */
    public Long getChargeOperUserSid() {
        return chargeOperUserSid;
    }

    /**
     * @param chargeOperUserSid 
	 *            充值操作人
     */
    public void setChargeOperUserSid(Long chargeOperUserSid) {
        this.chargeOperUserSid = chargeOperUserSid;
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

	public String getDisOwner() {
		return disOwner;
	}

	public void setDisOwner(String disOwner) {
		this.disOwner = disOwner;
	}
}