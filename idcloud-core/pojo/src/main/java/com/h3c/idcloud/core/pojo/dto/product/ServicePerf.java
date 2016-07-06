package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;

public class ServicePerf implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 性能SID
     */
    private Long perfSid;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 性能名称
     */
    private String name;

    /**
     * 性能描述
     */
    private String description;

    /**
     * 显示风格
     */
    private String displayStyle;

    /**
     * 性能组
     */
    private String perfGroup;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 性能单位
     */
    private String unit;

    /**
     * 取值区域
     */
    private String valueDomain;

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
     * uid
     */
    private String uid;
    
    /**
     * @return 性能SID
     */
    public Long getPerfSid() {
        return perfSid;
    }

    /**
     * @param perfSid 
	 *            性能SID
     */
    public void setPerfSid(Long perfSid) {
        this.perfSid = perfSid;
    }

    /**
     * @return 服务SID
     */
    public Long getServiceSid() {
        return serviceSid;
    }

    /**
     * @param serviceSid 
	 *            服务SID
     */
    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }

    /**
     * @return 性能名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            性能名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 性能描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            性能描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 显示风格
     */
    public String getDisplayStyle() {
        return displayStyle;
    }

    /**
     * @param displayStyle 
	 *            显示风格
     */
    public void setDisplayStyle(String displayStyle) {
        this.displayStyle = displayStyle;
    }

    /**
     * @return 性能组
     */
    public String getPerfGroup() {
        return perfGroup;
    }

    /**
     * @param perfGroup 
	 *            性能组
     */
    public void setPerfGroup(String perfGroup) {
        this.perfGroup = perfGroup;
    }

    /**
     * @return 数据类型
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType 
	 *            数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
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
     * @return 取值区域
     */
    public String getValueDomain() {
        return valueDomain;
    }

    /**
     * @param valueDomain 
	 *            取值区域
     */
    public void setValueDomain(String valueDomain) {
        this.valueDomain = valueDomain;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
    
    
}