package com.h3c.idcloud.core.pojo.dto.marketing;

import java.io.Serializable;
import java.util.Date;

public class CouponDisDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long distributionDetailSid;

    private Long couponSid;

    private Long userSid;

    private Long accountSid;


    private Integer usedStatus;


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

    public Long getDistributionDetailSid() {
        return distributionDetailSid;
    }

    public void setDistributionDetailSid(Long distributionDetailSid) {
        this.distributionDetailSid = distributionDetailSid;
    }

    public Long getCouponSid() {
        return couponSid;
    }

    public void setCouponSid(Long couponSid) {
        this.couponSid = couponSid;
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
     * @return 0 未使用   1 已使用
     */
    public Integer getUsedStatus() {
        return usedStatus;
    }

    /**
     * @param usedStatus 
	 *            0 未使用   1 已使用
     */
    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
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