package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResSoftwareConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置SID
     */
    private Long configSid;

    /**
     * 软件SID
     */
    private String resSortwareSid;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 规格描述
     */
    private String description;

    /**
     * 规格序列号
     */
    private String sequence;

    /**
     * 规格标签
     */
    private String tag;

    /**
     * 规格单位
     */
    private String unit;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 取值区域
     */
    private String valueDomain;

    /**
     * 规格值
     */
    private String value;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 是否显示
     */
    private String isShow;

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
     * @return 配置SID
     */
    public Long getConfigSid() {
        return configSid;
    }

    /**
     * @param configSid 配置SID
     */
    public void setConfigSid(Long configSid) {
        this.configSid = configSid;
    }

    /**
     * @return 软件SID
     */
    public String getResSortwareSid() {
        return resSortwareSid;
    }

    /**
     * @param resSortwareSid 软件SID
     */
    public void setResSortwareSid(String resSortwareSid) {
        this.resSortwareSid = resSortwareSid;
    }

    /**
     * @return 规格名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 规格名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 规格描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 规格描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 规格序列号
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @param sequence 规格序列号
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * @return 规格标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag 规格标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return 规格单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit 规格单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return 数据类型
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType 数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return 取值区域
     */
    public String getValueDomain() {
        return valueDomain;
    }

    /**
     * @param valueDomain 取值区域
     */
    public void setValueDomain(String valueDomain) {
        this.valueDomain = valueDomain;
    }

    /**
     * @return 规格值
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 规格值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return 组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName 组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return 是否显示
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * @param isShow 是否显示
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 创建人
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
     * @param createdDt 创建时间
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
     * @param updatedBy 更新人
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
     * @param updatedDt 更新时间
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
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}