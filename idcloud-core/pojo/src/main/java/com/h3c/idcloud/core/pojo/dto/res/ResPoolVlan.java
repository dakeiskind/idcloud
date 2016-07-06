package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResPoolVlan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源池ID
     */
    private String resPoolSid;

    /**
     * 拓扑结构SID
     */
    private String resTopologySid;

    /**
     * 资源池名称
     */
    private String resPoolName;

    /**
     * 资源池名称like
     */
    private String resPoolNameLike;

    /**
     * VLAN池类型
     */
    private String vlanPoolType;

    /**
     * VLAN池类型名称
     */
    private String vlanPoolTypeName;

    /**
     * 开始VLAN ID
     */
    private int startValnId;

    /**
     * 结束VLAN ID
     */
    private int endVlanId;

    /**
     * 描述
     */
    private String description;

    /**
     * 上级拓扑VLAN池ID
     */
    private String parentTopologySid;

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
     * 池中存在vlan的个数
     */
    private Integer vlanCount;

    /**
     * @return 资源池ID
     */
    public String getResPoolSid() {
        return resPoolSid;
    }

    /**
     * @param resPoolSid 资源池ID
     */
    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    /**
     * @return 资源池名称
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * @param resPoolName 资源池名称
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    /**
     * @return VLAN池类型
     */
    public String getVlanPoolType() {
        return vlanPoolType;
    }

    /**
     * @param vlanPoolType VLAN池类型
     */
    public void setVlanPoolType(String vlanPoolType) {
        this.vlanPoolType = vlanPoolType;
    }

    /**
     * @return 开始VLAN ID
     */
    public int getStartValnId() {
        return startValnId;
    }

    /**
     * @param startValnId 开始VLAN ID
     */
    public void setStartValnId(int startValnId) {
        this.startValnId = startValnId;
    }

    /**
     * @return 结束VLAN ID
     */
    public int getEndVlanId() {
        return endVlanId;
    }

    /**
     * @param endVlanId 结束VLAN ID
     */
    public void setEndVlanId(int endVlanId) {
        this.endVlanId = endVlanId;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 上级拓扑VLAN池ID
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * @param parentTopologySid 上级拓扑VLAN池ID
     */
    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 创建人
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
     * @param createdDt 创建时间
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
     * @param updatedBy 更新人
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
     * @param updatedDt 更新时间
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
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public String getResTopologySid() {
        return resTopologySid;
    }

    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    public String getVlanPoolTypeName() {
        return vlanPoolTypeName;
    }

    public void setVlanPoolTypeName(String vlanPoolTypeName) {
        this.vlanPoolTypeName = vlanPoolTypeName;
    }

    public String getResPoolNameLike() {
        return resPoolNameLike;
    }

    public void setResPoolNameLike(String resPoolNameLike) {
        this.resPoolNameLike = resPoolNameLike;
    }

    public Integer getVlanCount() {
        return vlanCount;
    }

    public void setVlanCount(Integer vlanCount) {
        this.vlanCount = vlanCount;
    }


}