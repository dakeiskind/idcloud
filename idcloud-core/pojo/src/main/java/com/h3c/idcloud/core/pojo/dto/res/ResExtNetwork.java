package com.h3c.idcloud.core.pojo.dto.res;


import java.io.Serializable;
import java.util.Date;

public class ResExtNetwork extends ResBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private String resExtNetworkSid;
    private String parentTopologySid;
    private String resPoolSid;
    private String networkName;
    private String networkType;
    private String zone;
    private String ipPool;
    private String description;
    private String ipType;
    private String uuid;
    private String status;
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

    public String getResExtNetworkSid() {
        return resExtNetworkSid;
    }

    public void setResExtNetworkSid(String resExtNetworkSid) {
        this.resExtNetworkSid = resExtNetworkSid;
    }

    public String getParentTopologySid() {
        return parentTopologySid;
    }

    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    public String getResPoolSid() {
        return resPoolSid;
    }

    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIpPool() {
        return ipPool;
    }

    public void setIpPool(String ipPool) {
        this.ipPool = ipPool;
    }
}