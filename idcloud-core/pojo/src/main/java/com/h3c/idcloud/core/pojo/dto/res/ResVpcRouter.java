package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * com.h3c.idcloud.core.pojo.dto.res
 *
 * @author Chaohong.Mao
 */
public class ResVpcRouter implements Serializable {
    private String resRouterSid;
    private String parentTopologySid;
    private String resPoolSid;
    private String routerId;
    private String routerName;
    private String externalGatewayInfo;
    private String status;
    private String statusName;
    private Long mgtObjSid;
    private String ownerId;
    private String zone;
    private String createdBy;
    private Date createdDt;
    private String updatedBy;
    private Date updatedDt;
    private Long version;

    public String getResRouterSid() {
        return resRouterSid;
    }

    public void setResRouterSid(String resRouterSid) {
        this.resRouterSid = resRouterSid;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getExternalGatewayInfo() {
        return externalGatewayInfo;
    }

    public void setExternalGatewayInfo(String externalGatewayInfo) {
        this.externalGatewayInfo = externalGatewayInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
