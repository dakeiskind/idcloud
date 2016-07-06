package com.h3c.idcloud.core.pojo.dto.marketing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 优惠券SID
     */
    private Long couponSid;

    /**
     * 优惠券详情的sid
     */
    private Long distributionDetailSid;

    public Long getDistributionDetailSid() {
        return distributionDetailSid;
    }

    public void setDistributionDetailSid(Long distributionDetailSid) {
        this.distributionDetailSid = distributionDetailSid;
    }

    /**
     * 优惠券代码
     */
    private String couponCode;

    private Long userSid;

    public Long getUserSid() {
        return userSid;
    }

    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

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
     * 折扣率0-1
     */
    private Float discountRate;

    /**
     * 适用用户级别,来源于微代码LEVEL_NAME 
     */
    private String userLevel;

    /**
     * 用户群体，来源于微代码USER_GROUP
     */
    private String userGroup;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 分发状态<br>
	 *             0 - 未分发<br>
	 *             1 - 已分发<br>
	 *             2 - 分发失败<br>
	 *             
     */
    private Short distributeStatus;

    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 分发渠道
     */
    private String distributeChannel;

    /**
     * 分发时间
     */
    private Date distributeDt;

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
     * @return 优惠券SID
     */
    public Long getCouponSid() {
        return couponSid;
    }

    /**
     * @param couponSid 
	 *            优惠券SID
     */
    public void setCouponSid(Long couponSid) {
        this.couponSid = couponSid;
    }

    /**
     * @return 优惠券代码
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * @param couponCode 
	 *            优惠券代码
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
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
     * @return 折扣率0-1
     */
    public Float getDiscountRate() {
        return discountRate;
    }

    /**
     * @param discountRate 
	 *            折扣率0-1
     */
    public void setDiscountRate(Float discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * @return 适用用户级别,来源于微代码LEVEL_NAME 
     */
    public String getUserLevel() {
        return userLevel;
    }

    /**
     * @param userLevel 
	 *            适用用户级别,来源于微代码LEVEL_NAME 
     */
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * @return 用户群体，来源于微代码USER_GROUP
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * @param userGroup 
	 *            用户群体，来源于微代码USER_GROUP
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @return 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks 
	 *            备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return 分发状态<br>
	 *                     0 - 新建<br>
	 *                     1 - 已分发<br>
	 *                     2 - 分发失败<br>
	 *                     
     */
    public Short getDistributeStatus() {
        return distributeStatus;
    }

    /**
     * @param distributeStatus 
	 *            分发状态<br>
	 *                        0 - 新建<br>
	 *                        1 - 已分发<br>
	 *                        2 - 分发失败<br>
	 *                        
     */
    public void setDistributeStatus(Short distributeStatus) {
        this.distributeStatus = distributeStatus;
    }

    public String getDistributeChannel() {
        return distributeChannel;
    }

    public void setDistributeChannel(String distributeChannel) {
        this.distributeChannel = distributeChannel;
    }

    /**
     * @return 分发时间
     */
    public Date getDistributeDt() {
        return distributeDt;
    }

    /**
     * @param distributeDt 
	 *            分发时间
     */
    public void setDistributeDt(Date distributeDt) {
        this.distributeDt = distributeDt;
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