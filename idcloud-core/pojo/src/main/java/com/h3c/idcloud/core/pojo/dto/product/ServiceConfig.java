package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;

public class ServiceConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置SID
     */
    private Long configSid;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 配置标签
     */
    private String tag;

    /**
     * 配置单位
     */
    private String unit;

    /**
     * 配置值
     */
    private String value;

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
     * @return 配置SID
     */
    public Long getConfigSid() {
        return configSid;
    }

    /**
     * @param configSid 
	 *            配置SID
     */
    public void setConfigSid(Long configSid) {
        this.configSid = configSid;
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
     * @return 配置名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            配置名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @return 组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName 
	 *            组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
     * @return 配置标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag 
	 *            配置标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return 配置单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit 
	 *            配置单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return 配置值
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 
	 *            配置值
     */
    public void setValue(String value) {
        this.value = value;
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