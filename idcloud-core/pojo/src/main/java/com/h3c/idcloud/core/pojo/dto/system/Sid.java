package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Sid implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 流水号SID
     */
    private Long sid;

    /**
     * 流水号类别代码
     */
    private String sidCategory;

    /**
     * 流水号类别名称
     */
    private String sidCategoryNm;

    /**
     * 流水号固定值
     */
    private String sidFrefix;

    /**
     * 流水号长度
     */
    private Integer sidLength;

    /**
     * 是否带有日期(true：带日期 false：不带日期)
     */
    private String isDate;

    /**
     * 日期格式(yyyyMMddhhmmss)
     */
    private String dateFormat;

    /**
     * 流水号记录日期
     */
    private String curDate;

    /**
     * 流水号记录番号
     */
    private Long curNo;

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
     * @return 流水号SID
     */
    public Long getSid() {
        return sid;
    }

    /**
     * @param sid 
	 *            流水号SID
     */
    public void setSid(Long sid) {
        this.sid = sid;
    }

    /**
     * @return 流水号类别代码
     */
    public String getSidCategory() {
        return sidCategory;
    }

    /**
     * @param sidCategory 
	 *            流水号类别代码
     */
    public void setSidCategory(String sidCategory) {
        this.sidCategory = sidCategory;
    }

    /**
     * @return 流水号类别名称
     */
    public String getSidCategoryNm() {
        return sidCategoryNm;
    }

    /**
     * @param sidCategoryNm 
	 *            流水号类别名称
     */
    public void setSidCategoryNm(String sidCategoryNm) {
        this.sidCategoryNm = sidCategoryNm;
    }

    /**
     * @return 流水号固定值
     */
    public String getSidFrefix() {
        return sidFrefix;
    }

    /**
     * @param sidFrefix 
	 *            流水号固定值
     */
    public void setSidFrefix(String sidFrefix) {
        this.sidFrefix = sidFrefix;
    }

    /**
     * @return 流水号长度
     */
    public Integer getSidLength() {
        return sidLength;
    }

    /**
     * @param sidLength 
	 *            流水号长度
     */
    public void setSidLength(Integer sidLength) {
        this.sidLength = sidLength;
    }

    /**
     * @return 是否带有日期(true：带日期 false：不带日期)
     */
    public String getIsDate() {
        return isDate;
    }

    /**
     * @param isDate 
	 *            是否带有日期(true：带日期 false：不带日期)
     */
    public void setIsDate(String isDate) {
        this.isDate = isDate;
    }

    /**
     * @return 日期格式(yyyyMMddhhmmss)
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormat 
	 *            日期格式(yyyyMMddhhmmss)
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return 流水号记录日期
     */
    public String getCurDate() {
        return curDate;
    }

    /**
     * @param curDate 
	 *            流水号记录日期
     */
    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    /**
     * @return 流水号记录番号
     */
    public Long getCurNo() {
        return curNo;
    }

    /**
     * @param curNo 
	 *            流水号记录番号
     */
    public void setCurNo(Long curNo) {
        this.curNo = curNo;
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