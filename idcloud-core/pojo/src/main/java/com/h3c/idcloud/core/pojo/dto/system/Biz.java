package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Biz implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long bizSid;

	private String customerId;

	private Long parentBizSid;

	private Long parentBizSids;

	private String name;

	private String bizNameLike;

	private String owner;

	private String ownerTel;

	private String description;

	private String createdBy;

	private Date createdDt;

	private String updatedBy;

	private Date updatedDt;

	private Long version;

	private Long networktype;

	private Long fwport;

	private Long isbizcont;

	private Long isprono;

	private Long isproattach;

	private Long level;

	private Long orgSid;

	/** 非持久化字段 start*/

	private String parentBizName;

	private  String parentBizNameLike;

	private String bizTypeText;

	private String bizTypeValue;

	private String quotaKey;

	/** 非持久化字段 end*/

	private String pm;

	/** 状态 */
	private String status;

	private String mode;

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getPmTel() {
		return pmTel;
	}

	public void setPmTel(String pmTel) {
		this.pmTel = pmTel;
	}

	public String getPmEmail() {
		return pmEmail;
	}

	public void setPmEmail(String pmEmail) {
		this.pmEmail = pmEmail;
	}

	private String pmTel;

	private String pmEmail;


	public Long getBizSid() {
		return bizSid;
	}

	public void setBizSid(Long bizSid) {
		this.bizSid = bizSid;
	}

	public Long getParentBizSid() {
		return parentBizSid;
	}

	public void setParentBizSid(Long parentBizSid) {
		this.parentBizSid = parentBizSid;
	}

	public String getParentBizName() {
		return parentBizName;
	}

	public void setParentBizName(String parentBizName) {
		this.parentBizName = parentBizName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getNetworktype() {
		return networktype;
	}

	public void setNetworktype(Long networktype) {
		this.networktype = networktype;
	}

	public Long getFwport() {
		return fwport;
	}

	public void setFwport(Long fwport) {
		this.fwport = fwport;
	}

	public Long getIsbizcont() {
		return isbizcont;
	}

	public void setIsbizcont(Long isbizcont) {
		this.isbizcont = isbizcont;
	}

	public Long getIsprono() {
		return isprono;
	}

	public void setIsprono(Long isprono) {
		this.isprono = isprono;
	}

	public Long getIsproattach() {
		return isproattach;
	}

	public void setIsproattach(Long isproattach) {
		this.isproattach = isproattach;
	}

	public String getBizTypeText() {
		return bizTypeText;
	}

	public void setBizTypeText(String bizTypeText) {
		this.bizTypeText = bizTypeText;
	}

	public String getBizTypeValue() {
		return bizTypeValue;
	}

	public void setBizTypeValue(String bizTypeValue) {
		this.bizTypeValue = bizTypeValue;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getQuotaKey() {
		return quotaKey;
	}

	public void setQuotaKey(String quotaKey) {
		this.quotaKey = quotaKey;
	}

	public Long getOrgSid() {
		return orgSid;
	}

	public void setOrgSid(Long orgSid) {
		this.orgSid = orgSid;
	}

	public String getBizNameLike() {
		return bizNameLike;
	}

	public void setBizNameLike(String bizNameLike) {
		this.bizNameLike = bizNameLike;
	}

	public String getParentBizNameLike() {
		return parentBizNameLike;
	}

	public void setParentBizNameLike(String parentBizNameLike) {
		this.parentBizNameLike = parentBizNameLike;
	}

	public Long getParentBizSids() {
		return parentBizSids;
	}

	public void setParentBizSids(Long parentBizSids) {
		this.parentBizSids = parentBizSids;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}