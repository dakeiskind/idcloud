package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源ID	RES_VPC_SID	varchar(36)	Yes
 * 上级拓扑结构ID	PARENT_TOPOLOGY_SID	varchar(36)
 * 所属资源池ID	RES_POOL_SID	varchar(36)
 * 网络名称	VPC_NAME	varchar(256)	Yes
 * 描述	DESCRIPTION	varchar(1024)
 * 租户_ID	TANENT_ID	varchar(128)
 * UUID	varchar(256)
 * 状态	STATUS	varchar(2)
 * 创建人	CREATED_BY	varchar(32)
 * 创建时间	CREATED_DT	datetime
 * 更新人	UPDATED_BY	varchar(32)
 * 更新时间	UPDATED_DT	datetime
 * 版本号	VERSION	bigint(9)	Yes
 *
 * @author Chaohong.Mao
 */
public class ResVpc implements Serializable {
    private String resVpcSid;
    private String parentTopologySid;
    private String resPoolSid;
    private String zone;
    private String vpcName;
    private String description;
    private String cidr;
    private Long mgtObjSid;
    private String uuid;
    private String status;
    private String statusName;
    private String createdBy;
    private Date createdDt;
    private String updatedBy;
    private Date updatedDt;
    private Long version;

    public String getResVpcSid() {
        return resVpcSid;
    }

    public void setResVpcSid(String resVpcSid) {
        this.resVpcSid = resVpcSid;
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

    public String getVpcName() {
        return vpcName;
    }

    public void setVpcName(String vpcName) {
        this.vpcName = vpcName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
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

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
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
