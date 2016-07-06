package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResCdnInst implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cdnInstSid;

    private String cdnSid;

    private long mgtObjSid;

    private String status;

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

    public String getCdnInstSid() {
        return cdnInstSid;
    }

    public void setCdnInstSid(String cdnInstSid) {
        this.cdnInstSid = cdnInstSid;
    }

    public String getCdnSid() {
        return cdnSid;
    }

    public void setCdnSid(String cdnSid) {
        this.cdnSid = cdnSid;
    }


    /**
	 * @return the mgtObjSid
	 */
	public long getMgtObjSid() {
		return mgtObjSid;
	}

	/**
	 * @param mgtObjSid the mgtObjSid to set
	 */
	public void setMgtObjSid(long mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}