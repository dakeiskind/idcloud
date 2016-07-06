package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Org implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orgSid;

    private String orgName;
    
    private String orgText;

    private String orgValue;

    private Long sortRank;

    private Long parentOrgSid;
    
    private String parentOrgName;

    private String orgDesc;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;

    public Long getOrgSid() {
        return orgSid;
    }

    public void setOrgSid(Long orgSid) {
        this.orgSid = orgSid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getSortRank() {
        return sortRank;
    }

    public void setSortRank(Long sortRank) {
        this.sortRank = sortRank;
    }

    public Long getParentOrgSid() {
        return parentOrgSid;
    }

    public void setParentOrgSid(Long parentOrgSid) {
        this.parentOrgSid = parentOrgSid;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

	public String getOrgText() {
		return orgText;
	}

	public void setOrgText(String orgText) {
		this.orgText = orgText;
	}

	public String getOrgValue() {
		return orgValue;
	}

	public void setOrgValue(String orgValue) {
		this.orgValue = orgValue;
	}

	public String getParentOrgName() {
		return parentOrgName;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
    
    
}