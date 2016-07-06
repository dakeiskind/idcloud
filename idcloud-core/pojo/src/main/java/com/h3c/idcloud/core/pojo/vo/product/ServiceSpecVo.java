package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;
import java.util.Date;

public class ServiceSpecVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规格SID
     */
    private Long specSid;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 规格描述
     */
    private String description;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 是否显示
     */
    private String isShow;

    /**
     * 规格序列号
     */
    private String sequence;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 规格标签
     */
    private String tag;

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
     * 复选框
     */
    private boolean checkbox;
    
    /**
     * 数值
     */
    private String value;
    
    /**
     * 计价类型
     */
    private String chargeType;
    
    /**
     * 计价类型
     */
    private String chargeTypeName;

    /**
     * 计价单位
     */
    private String chargeUnit;

    /**
     * 单价
     */
    private Float unitPrice;

    /**
     * 折扣率
     */
    private Float discount;

    /**
     * 状态(01:启用, 02:禁用)
     */
    private String status;
    
    /**
     * 状态(01:启用, 02:禁用)
     */
    private String statusName;
    
    /**
     * uid
     */
    private String uid;

    /**
     * @return 规格SID
     */
    public Long getSpecSid() {
        return specSid;
    }

    /**
     * @param specSid 
	 *            规格SID
     */
    public void setSpecSid(Long specSid) {
        this.specSid = specSid;
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
     * @return 是否显示
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * @param isShow 
	 *            是否显示
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
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

	public boolean getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the chargeUnit
	 */
	public String getChargeUnit() {
		return chargeUnit;
	}

	/**
	 * @param chargeUnit the chargeUnit to set
	 */
	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}

	/**
	 * @return the unitPrice
	 */
	public Float getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the discount
	 */
	public Float getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the chargeTypeName
	 */
	public String getChargeTypeName() {
		return chargeTypeName;
	}

	/**
	 * @param chargeTypeName the chargeTypeName to set
	 */
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}