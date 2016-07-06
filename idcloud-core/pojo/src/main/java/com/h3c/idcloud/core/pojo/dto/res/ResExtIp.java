package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResExtIp implements Serializable {
    private static final long serialVersionUID = 1L;
    private String resSid;
    private String resExtNetworkSid;
    private String ipType;
    private String ipAddress;
    private String mapPublicIp;
    private String status;
    private String description;
    private String uuid;
    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private Date createdDt;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedDt;

    /** 版本号 */
    private Long version;

    public String getResSid() {
        return resSid;
    }

    public void setResSid(String resSid) {
        this.resSid = resSid;
    }

    public String getResExtNetworkSid() {
        return resExtNetworkSid;
    }

    public void setResExtNetworkSid(String resExtNetworkSid) {
        this.resExtNetworkSid = resExtNetworkSid;
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMapPublicIp() {
        return mapPublicIp;
    }

    public void setMapPublicIp(String mapPublicIp) {
        this.mapPublicIp = mapPublicIp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }
}
