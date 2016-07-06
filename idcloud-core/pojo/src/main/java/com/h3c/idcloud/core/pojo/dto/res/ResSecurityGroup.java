package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * Res security group 类.
 *
 * @author Chaohong.Mao
 */
public class ResSecurityGroup implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Id.
     */
    private Long id;

    /**
     * 资源分区SID.
     */
    private String zone;

    /**
     * The Security group name.
     */
    private String securityGroupName;

    /**
     * The Description.
     */
    private String description;

    /**
     * The Mgt obj sid.
     */
    private Long mgtObjSid;

    /**
     * The Uuid.
     */
    private String uuid;

    /**
     * The Created by.
     */
    private String createdBy;

    /**
     * The Created dt.
     */
    private Date createdDt;

    /**
     * The Updated by.
     */
    private String updatedBy;

    /**
     * The Updated dt.
     */
    private Date updatedDt;

    /**
     * The Version.
     */
    private Long version;

    /**
     * The Status.
     */
    private String status;

    /**
     * The Status name.
     */
    private String statusName;

    /**
     * 获得 status name.
     *
     * @return the status name
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 设定 status name.
     *
     * @param statusName the status name
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 获得 id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设定 id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获得 security group name.
     *
     * @return the security group name
     */
    public String getSecurityGroupName() {
        return securityGroupName;
    }

    /**
     * 设定 security group name.
     *
     * @param securityGroupName the security group name
     */
    public void setSecurityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
    }

    /**
     * 获得 description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设定 description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获得 mgt obj sid.
     *
     * @return the mgt obj sid
     */
    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * 设定 mgt obj sid.
     *
     * @param mgtObjSid the mgt obj sid
     */
    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    /**
     * 获得 uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设定 uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获得 created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设定 created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获得 created dt.
     *
     * @return the created dt
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * 设定 created dt.
     *
     * @param createdDt the created dt
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * 获得 updated by.
     *
     * @return the updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设定 updated by.
     *
     * @param updatedBy the updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获得 updated dt.
     *
     * @return the updated dt
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * 设定 updated dt.
     *
     * @param updatedDt the updated dt
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * 获得 version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 设定 version.
     *
     * @param version the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 获得 status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设定 status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}