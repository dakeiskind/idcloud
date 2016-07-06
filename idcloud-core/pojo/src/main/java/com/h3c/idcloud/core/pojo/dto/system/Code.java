package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Code implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微代码SID
     */
    private Long codeSid;

    /**
     * 微代码
     */
    private String codeCategory;

    /**
     * 微代码值
     */
    private String codeValue;

    /**
     * 微代码显示值
     */
    private String codeDisplay;

    /**
     * 上级微代码值
     */
    private String parentCodeValue;

    /**
     * 是否启用(1:启用;0:不启用)
     */
    private String enabled;

    private String enabledName;

    /**
     * 排序
     */
    private int sort;

    private int sortNo;

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
     * 属性1
     */
    private String attribute1;
    /**
     * 属性2
     */
    private String attribute2;
    /**
     * 属性3
     */
    private String attribute3;
    /**
     * 属性4
     */
    private String attribute4;
    /**
     * 属性5
     */
    private String attribute5;
    /**
     * 属性6
     */
    private String attribute6;

    /**
     * 模板可部署软件类型
     */
    private String softWareValue;

    /**
     * @return 微代码SID
     */
    public Long getCodeSid() {
        return codeSid;
    }

    /**
     * @param codeSid
     *            微代码SID
     */
    public void setCodeSid(Long codeSid) {
        this.codeSid = codeSid;
    }

    /**
     * @return 微代码
     */
    public String getCodeCategory() {
        return codeCategory;
    }

    /**
     * @param codeCategory
     *            微代码
     */
    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    /**
     * @return 微代码值
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * @param codeValue
     *            微代码值
     */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    /**
     * @return 微代码显示值
     */
    public String getCodeDisplay() {
        return codeDisplay;
    }

    /**
     * @param codeDisplay
     *            微代码显示值
     */
    public void setCodeDisplay(String codeDisplay) {
        this.codeDisplay = codeDisplay;
    }

    /**
     * @return 上级微代码值
     */
    public String getParentCodeValue() {
        return parentCodeValue;
    }

    /**
     * @param parentCodeValue
     *            上级微代码值
     */
    public void setParentCodeValue(String parentCodeValue) {
        this.parentCodeValue = parentCodeValue;
    }

    /**
     * @return 是否启用(1:启用;0:不启用)
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            是否启用(1:启用;0:不启用)
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return 排序
     */
    public int getSort() {
        return sort;
    }

    /**
     * @param sort
     *            排序
     */
    public void setSort(int sort) {
        this.sort = sort;
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

    /**
     * @return the attribute3
     */
    public String getAttribute3() {
        return attribute3;
    }

    /**
     * @param attribute3 the attribute3 to set
     */
    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    /**
     * @return the attribute1
     */
    public String getAttribute1() {
        return attribute1;
    }

    /**
     * @param attribute1 the attribute1 to set
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * @return the attribute2
     */
    public String getAttribute2() {
        return attribute2;
    }

    /**
     * @param attribute2 the attribute2 to set
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    /**
     * @return the attribute4
     */
    public String getAttribute4() {
        return attribute4;
    }

    /**
     * @param attribute4 the attribute4 to set
     */
    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    /**
     * @return the attribute5
     */
    public String getAttribute5() {
        return attribute5;
    }

    /**
     * @param attribute5 the attribute5 to set
     */
    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    /**
     * @return the attribute6
     */
    public String getAttribute6() {
        return attribute6;
    }

    /**
     * @param attribute6 the attribute6 to set
     */
    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    /**
     * @return the softWareValue
     */
    public String getSoftWareValue() {
        return softWareValue;
    }

    /**
     * @param softWareValue the softWareValue to set
     */
    public void setSoftWareValue(String softWareValue) {
        this.softWareValue = softWareValue;
    }

    /**
     * @return the sortNo
     */
    public int getSortNo() {
        return sortNo;
    }

    /**
     * @param sortNo the sortNo to set
     */
    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * @return the enabledName
     */
    public String getEnabledName() {
        return enabledName;
    }

    /**
     * @param enabledName the enabledName to set
     */
    public void setEnabledName(String enabledName) {
        this.enabledName = enabledName;
    }
	
}