package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

public class ResVlan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resSid;

    /**
     * 所属VLAN池ID
     */
    private String resPoolSid;

    /**
     * VLAN ID
     */
    private String vlanId;

    /**
     * VLAN标签
     */
    private String tag;

    /**
     * 使用状态
     */
    private String usageStatus;

    /**
     * 使用状态名称
     */
    private String usageStatusName;


    /**
     * 查询条件---unused 未使用
     */
    private String unused;

    /**
     * 查询条件---poolType ,vlan池类型
     */
    private String poolType;

    /**
     * 查询条件---parentTopologySid 是否在一个网络池下面
     */
    private String parentTopologySid;

    /**
     * 查询一个资源分区下的所有VLAN
     */
    private String findVlanInZone;

    /**
     * 开始VLAN ID
     */
    private int startValnId;

    /**
     * 结束VLAN ID
     */
    private int endVlanId;

    /**
     * 所属交换机
     */
    private String resVsSid;

    /**
     * 端口uuid
     */
    private String uuid;

    /**
     * vlan的总数
     */
    private String totalCount;

    /**
     * vlan的已使用
     */
    private String usedCount;

    /**
     * vlan的未使用
     */
    private String unusedCount;

    /**
     * @return 资源ID
     */
    public String getResSid() {
        return resSid;
    }

    /**
     * @param resSid 资源ID
     */
    public void setResSid(String resSid) {
        this.resSid = resSid;
    }

    /**
     * @return 所属VLAN池ID
     */
    public String getResPoolSid() {
        return resPoolSid;
    }

    /**
     * @param resPoolSid 所属VLAN池ID
     */
    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    /**
     * @return VLAN ID
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * @param vlanId VLAN ID
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * @return VLAN标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag VLAN标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUnused() {
        return unused;
    }

    public void setUnused(String unused) {
        this.unused = unused;
    }

    public String getPoolType() {
        return poolType;
    }

    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    public String getParentTopologySid() {
        return parentTopologySid;
    }

    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    public String getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(String usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getUsageStatusName() {
        return usageStatusName;
    }

    public void setUsageStatusName(String usageStatusName) {
        this.usageStatusName = usageStatusName;
    }

    public String getFindVlanInZone() {
        return findVlanInZone;
    }

    public void setFindVlanInZone(String findVlanInZone) {
        this.findVlanInZone = findVlanInZone;
    }

    public int getStartValnId() {
        return startValnId;
    }

    public void setStartValnId(int startValnId) {
        this.startValnId = startValnId;
    }

    public int getEndVlanId() {
        return endVlanId;
    }

    public void setEndVlanId(int endVlanId) {
        this.endVlanId = endVlanId;
    }

    public String getResVsSid() {
        return resVsSid;
    }

    public void setResVsSid(String resVsSid) {
        this.resVsSid = resVsSid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(String usedCount) {
        this.usedCount = usedCount;
    }

    public String getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(String unusedCount) {
        this.unusedCount = unusedCount;
    }


}