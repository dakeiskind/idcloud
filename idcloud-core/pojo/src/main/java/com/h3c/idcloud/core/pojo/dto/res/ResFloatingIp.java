package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResFloatingIp implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resSid;
    private String resExtNetworkSid;
    private String resIpSid;
    private String status;
    private String mappingNetworkSid;
    private String mappingIpSid;
    private String mappingVmSid;
    private int bandwidth;
    private Long mgtObjSid;
    private String uuid;
    private String zone;
    private String createdBy;
    private Date createdDt;
    private String updatedBy;
    private Date updatedDt;
    private Long version;
    private String netWorkType;
    private String ipAddress;

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getNetWorkType() {
        return netWorkType;
    }

    public void setNetWorkType(String netWorkType) {
        this.netWorkType = netWorkType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the resSid
     */
    public String getResSid() {
        return resSid;
    }

    /**
     * @param resSid the resSid to set
     */
    public void setResSid(String resSid) {
        this.resSid = resSid;
    }

    /**
     * @return the resNetworkSid
     */
    public String getResExtNetworkSid() {
        return resExtNetworkSid;
    }

    /**
     * @param resNetworkSid the resNetworkSid to set
     */
    public void setResExtNetworkSid(String resNetworkSid) {
        this.resExtNetworkSid = resNetworkSid;
    }

    public String getResIpSid() {
        return resIpSid;
    }

    public void setResIpSid(String resIpSid) {
        this.resIpSid = resIpSid;
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

    public String getMappingIpSid() {
        return mappingIpSid;
    }

    public void setMappingIpSid(String mappingIpSid) {
        this.mappingIpSid = mappingIpSid;
    }

    public String getMappingVmSid() {
        return mappingVmSid;
    }

    public void setMappingVmSid(String mappingVmSid) {
        this.mappingVmSid = mappingVmSid;
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

    public String getMappingNetworkSid() {
        return mappingNetworkSid;
    }

    public void setMappingNetworkSid(String mappingNetworkSid) {
        this.mappingNetworkSid = mappingNetworkSid;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}