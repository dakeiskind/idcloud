package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResObjStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对象存储资源ID
     */
    private String resOsSid;

    /**
     * 上级拓扑结构ID
     */
    private String parentTopologySid;

    /**
     * 所属资源池ID
     */
    private String resPoolSid;

    /**
     * 对象存储访问地址
     */
    private String osVisitAddress;

    /**
     * 客户端下载地址
     */
    private String clientDownloadUrl;

    /**
     * 总容量
     */
    private Long totalCapacity;

    private String uuid;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;

    /**
     * @return 对象存储资源ID
     */
    public String getResOsSid() {
        return resOsSid;
    }

    /**
     * @param resOsSid 对象存储资源ID
     */
    public void setResOsSid(String resOsSid) {
        this.resOsSid = resOsSid;
    }

    /**
     * @return 上级拓扑结构ID
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * @param parentTopologySid 上级拓扑结构ID
     */
    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    /**
     * @return 所属资源池ID
     */
    public String getResPoolSid() {
        return resPoolSid;
    }

    /**
     * @param resPoolSid 所属资源池ID
     */
    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    /**
     * @return 对象存储访问地址
     */
    public String getOsVisitAddress() {
        return osVisitAddress;
    }

    /**
     * @param osVisitAddress 对象存储访问地址
     */
    public void setOsVisitAddress(String osVisitAddress) {
        this.osVisitAddress = osVisitAddress;
    }

    /**
     * @return 客户端下载地址
     */
    public String getClientDownloadUrl() {
        return clientDownloadUrl;
    }

    /**
     * @param clientDownloadUrl 客户端下载地址
     */
    public void setClientDownloadUrl(String clientDownloadUrl) {
        this.clientDownloadUrl = clientDownloadUrl;
    }

    /**
     * @return 总容量
     */
    public Long getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * @param totalCapacity 总容量
     */
    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}