package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class MgtObjDef implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性ID
     */
    private Long defSid;

    /**
     * 管理业务模型 'biz':业务, 'project':项目 'tenant':租户
     */
    private String mgtBizMode;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性KEY
     */
    private String attrKey;

    /**
     * 数据类型 string, int, boolean
     */
    private String dataType;

    /**
     * 显示类型
     */
    private String displayType;

    /**
     * 性能单位
     */
    private String unit;

    /**
     * 取值区间
     */
    private String valueDomain;

    /**
     * 取值增量
     */
    private String valueIncrement;
    
    /**
     * 验证规则
     */
    private String validateRule;

    /**
     * 显示顺序
     */
    private Integer sortRank;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 状态 0 不启用 1启用
     */
    private Long status;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 修改时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;

    /**
     * @return 属性ID
     */
    public Long getDefSid() {
        return defSid;
    }

    /**
     * @param defSid 
	 *            属性ID
     */
    public void setDefSid(Long defSid) {
        this.defSid = defSid;
    }

    /**
     * @return 管理业务模型 'biz':业务, 'project':项目 'tenant':租户
     */
    public String getMgtBizMode() {
        return mgtBizMode;
    }

    /**
     * @param mgtBizMode 
	 *            管理业务模型 'biz':业务, 'project':项目 'tenant':租户
     */
    public void setMgtBizMode(String mgtBizMode) {
        this.mgtBizMode = mgtBizMode;
    }

    /**
     * @return 属性名称
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * @param attrName 
	 *            属性名称
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    /**
     * @return 属性KEY
     */
    public String getAttrKey() {
        return attrKey;
    }

    /**
     * @param attrKey 
	 *            属性KEY
     */
    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    /**
     * @return 数据类型 string, int, boolean
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType 
	 *            数据类型 string, int, boolean
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return 显示类型
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     * @param displayType 
	 *            显示类型
     */
    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    /**
     * @return 性能单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit 
	 *            性能单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return 取值区间
     */
    public String getValueDomain() {
        return valueDomain;
    }

    /**
     * @param valueDomain 
	 *            取值区间
     */
    public void setValueDomain(String valueDomain) {
        this.valueDomain = valueDomain;
    }

    /**
     * @return 取值增量
     */
    public String getValueIncrement() {
        return valueIncrement;
    }

    /**
     * @param valueIncrement 
	 *            取值增量
     */
    public void setValueIncrement(String valueIncrement) {
        this.valueIncrement = valueIncrement;
    }

    /**
     * @return 显示顺序
     */
    public Integer getSortRank() {
        return sortRank;
    }

    /**
     * @param sortRank 
	 *            显示顺序
     */
    public void setSortRank(Integer sortRank) {
        this.sortRank = sortRank;
    }

    /**
     * @return 配置描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            配置描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 状态 0 不启用 1启用
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态 0 不启用 1启用
     */
    public void setStatus(Long status) {
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
     * @return 修改人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 
	 *            修改人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 修改时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 
	 *            修改时间
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

	public String getValidateRule() {
		return validateRule;
	}

	public void setValidateRule(String validateRule) {
		this.validateRule = validateRule;
	}
}