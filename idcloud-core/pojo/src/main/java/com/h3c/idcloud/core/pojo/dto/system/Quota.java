package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Quota implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配额项ID
     */
    private Long quotaSid;

    /**
     * 配额类型(Windows&Linux, AIX)
     */
    private String quotaType;
    
    /**
     * 配额项名称
     */
    private String quotaName;

    /**
     * 配额项Key
     */
    private String quotaKey;

    /**
     * 配额项值
     */
    private String quotaValue;

    /**
     * 原配额值
     */
    private String quotaOldValue;

    /**
     * 配额对象ID
     */
    private Long quotaObjSid;

    /**
     * 0 业务;1 组织; 2 租户
     */
    private Long quotaObj;

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
     * 操作方式
     */
    private String oper;
    
    /**
     * 剩余配额
     */
    private Long remainQuota;
    
    
    /**
     * 已分配配额
     */
    private Long usagedQuota;
    
    private String quotaSumValue;
    
    
    public String getQuotaSumValue() {
		return quotaSumValue;
	}

	public void setQuotaSumValue(String quotaSumValue) {
		this.quotaSumValue = quotaSumValue;
	}

	/**
     * @return 配额项ID
     */
    public Long getQuotaSid() {
        return quotaSid;
    }

    /**
     * @param quotaSid 
	 *            配额项ID
     */
    public void setQuotaSid(Long quotaSid) {
        this.quotaSid = quotaSid;
    }

    /**
     * @return 配额项名称
     */
    
    public String getQuotaName() {
        return quotaName;
    }

    /**
     * @param quotaName 
	 *            配额项名称
     */
    public void setQuotaName(String quotaName) {
        this.quotaName = quotaName;
    }

    /**
     * @return 配额项Key
     */
    public String getQuotaKey() {
        return quotaKey;
    }

    /**
     * @param quotaKey 
	 *            配额项Key
     */
    public void setQuotaKey(String quotaKey) {
        this.quotaKey = quotaKey;
    }

    /**
     * @return 配额项值
     */
    public String getQuotaValue() {
        return quotaValue;
    }

    /**
     * @param quotaValue 
	 *            配额项值
     */
    public void setQuotaValue(String quotaValue) {
        this.quotaValue = quotaValue;
    }

    /**
     * @return 配额对象ID
     */
    public Long getQuotaObjSid() {
        return quotaObjSid;
    }

    /**
     * @param quotaObjSid 
	 *            配额对象ID
     */
    public void setQuotaObjSid(Long quotaObjSid) {
        this.quotaObjSid = quotaObjSid;
    }

    /**
     * @return 0 业务;1 组织; 2 租户
     */
    public Long getQuotaObj() {
        return quotaObj;
    }

    /**
     * @param quotaObj 
	 *            0 业务;1 组织; 2 租户
     */
    public void setQuotaObj(Long quotaObj) {
        this.quotaObj = quotaObj;
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Long getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Long remainQuota) {
		this.remainQuota = remainQuota;
	}

	public String getQuotaOldValue() {
		return quotaOldValue;
	}

	public void setQuotaOldValue(String quotaOldValue) {
		this.quotaOldValue = quotaOldValue;
	}

	public Long getUsagedQuota() {
		return usagedQuota;
	}

	public void setUsagedQuota(Long usagedQuota) {
		this.usagedQuota = usagedQuota;
	}

	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}
	
	
}