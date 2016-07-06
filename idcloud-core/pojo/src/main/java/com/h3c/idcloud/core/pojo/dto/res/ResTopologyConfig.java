package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResTopologyConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置项ID
     */
    private String configId;

    /**
     * 配置项名称
     */
    private String configName;

    /**
     * 配置项Key
     */
    private String configKey;

    /**
     * 配置项值
     */
    private String configValue;
    
    /**
     * 配置项值名称
     */
    private String configValueName;

    /**
     * 资源拓扑结构ID
     */
    private String resTopologySid;

    /**
     * 描述
     */
    private String description;

    /**
     * 显示顺序
     */
    private Long sortRank;

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
     * @return 配置项ID
     */
    public String getConfigId() {
        return configId;
    }

    /**
     * @param configId 
	 *            配置项ID
     */
    public void setConfigId(String configId) {
        this.configId = configId;
    }

    /**
     * @return 配置项名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * @param configName 
	 *            配置项名称
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * @return 配置项Key
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * @param configKey 
	 *            配置项Key
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    /**
     * @return 配置项值
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * @param configValue 
	 *            配置项值
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    /**
     * @return 资源拓扑结构ID
     */
    public String getResTopologySid() {
        return resTopologySid;
    }

    /**
     * @param resTopologySid 
	 *            资源拓扑结构ID
     */
    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 显示顺序
     */
    public Long getSortRank() {
        return sortRank;
    }

    /**
     * @param sortRank 
	 *            显示顺序
     */
    public void setSortRank(Long sortRank) {
        this.sortRank = sortRank;
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

	public String getConfigValueName() {
		return configValueName;
	}

	public void setConfigValueName(String configValueName) {
		this.configValueName = configValueName;
	}
    
    
}