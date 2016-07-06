package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResObjStorageInst implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对象存储实例ID
     */
    private String resInstSid;

    /**
     * 所属对象存储ID
     */
    private String resOsSid;

    /**
     * 访问地址
     */
    private String visitAddress;

    /**
     * 访问Key
     */
    private String visitKey;

    /**
     * 访问用户
     */
    private String visitUser;

    /**
     * 容量
     */
    private Long capacity;

    /**
     * UUID
     */
    private String uuid;

    /**
     * 状态
     */
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

    /**
     * 管理对象Sid
     */
    private Long mgtObjSid;

    /**
     * @return 对象存储实例ID
     */
    public String getResInstSid() {
        return resInstSid;
    }

    /**
     * @param resInstSid 
	 *            对象存储实例ID
     */
    public void setResInstSid(String resInstSid) {
        this.resInstSid = resInstSid;
    }

    /**
     * @return 所属对象存储ID
     */
    public String getResOsSid() {
        return resOsSid;
    }

    /**
     * @param resOsSid 
	 *            所属对象存储ID
     */
    public void setResOsSid(String resOsSid) {
        this.resOsSid = resOsSid;
    }

    /**
     * @return 访问地址
     */
    public String getVisitAddress() {
        return visitAddress;
    }

    /**
     * @param visitAddress 
	 *            访问地址
     */
    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    /**
     * @return 访问Key
     */
    public String getVisitKey() {
        return visitKey;
    }

    /**
     * @param visitKey 
	 *            访问Key
     */
    public void setVisitKey(String visitKey) {
        this.visitKey = visitKey;
    }

    /**
     * @return 访问用户
     */
    public String getVisitUser() {
        return visitUser;
    }

    /**
     * @param visitUser 
	 *            访问用户
     */
    public void setVisitUser(String visitUser) {
        this.visitUser = visitUser;
    }

    /**
     * @return 容量
     */
    public Long getCapacity() {
        return capacity;
    }

    /**
     * @param capacity 
	 *            容量
     */
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    /**
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid 
	 *            UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

}