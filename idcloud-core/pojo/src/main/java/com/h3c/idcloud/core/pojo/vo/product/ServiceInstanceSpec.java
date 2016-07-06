package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;
import java.util.Date;

public class ServiceInstanceSpec implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 实例规格SID
     */
    private Long specSid;

    /**
     * 服务实例SID
     */
    private Long instanceSid;

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
     * 规格值
     */
    private String value;

    /**
     * 新规格值
     */
    private String newValue;

    /**
     * 规格值文本
     */
    private String valueText;
    

    /**
     * 组名称
     */
    private String groupName;

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
     * 状态
     */
    private String status;

	/**
     * 版本号
     */
    private Long version;
    
    /**
     * 操作类型
     */
    private String operate;

    /**
     * @return 实例规格SID
     */
    public Long getSpecSid() {
        return specSid;
    }

    /**
     * @param specSid 
	 *            实例规格SID
     */
    public void setSpecSid(Long specSid) {
        this.specSid = specSid;
    }

    /**
     * @return 服务实例SID
     */
    public Long getInstanceSid() {
        return instanceSid;
    }

    /**
     * @param instanceSid 
	 *            服务实例SID
     */
    public void setInstanceSid(Long instanceSid) {
        this.instanceSid = instanceSid;
    }

    /**
     * @return 规格名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            规格名称
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
     * @param description 
	 *            规格描述
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
     * @param sequence 
	 *            规格序列号
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
     * @param tag 
	 *            规格标签
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
     * @param unit 
	 *            规格单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return 规格值
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 
	 *            规格值
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
     * @param groupName 
	 *            组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
     * @return 状态
     */
    public String getStatus() {
		return status;
	}
    
    /**
     * @param status 
	 *            状态
     */
	public void setStatus(String status) {
		this.status = status;
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

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
    
    
}