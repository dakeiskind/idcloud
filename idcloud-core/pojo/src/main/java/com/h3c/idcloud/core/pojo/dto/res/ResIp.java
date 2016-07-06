package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResIp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resSid;

    /**
     * ip的sids
     */
    private String resIpSids;

    /**
     * 业务名称
     */
    private String resBizName;

    /**
     * 所属网络ID
     */
    private String resNetworkSid;

    /**
     * IP类型(IPV4，IPV6)
     */
    private String ipType;

    /**
     * IP类型名称
     */
    private String ipTypeName;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 模糊查询IP地址
     */
    private String ipAddressLike;

    /**
     * 对应公网IP
     */
    private String mapPublicIp;

    /**
     * 状态
     */
    private String status;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 描述/备注
     */
    private String description;

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
     * 使用状态
     */
    private String usageStatus;

    /**
     * 使用状态名称
     */
    private String usageStatusName;

    /**
     * 资源池类型
     */
    private String resPoolType;

    /**
     * 操作
     */
    private String operate;
    /**
     * 所属资源拓扑
     */
    private String parentTopologySid;

    /**
     * ip所属的网络池类型
     */
    private String networkType;

    /**
     * 所属虚拟机
     */
    private String vmName;

    /**
     * ip总数
     */
    private String totalCount;

    /**
     * ip已使用
     */
    private String usedCount;

    /**
     * ip未使用
     */
    private String unusedCount;


    /**
     * 所属端口组
     */
    private String portGroupName;


    /**
     * 虚拟机主网标识
     */
    private String vmPrimaryNet;

    /**
     * 虚拟机网卡mac
     */
    private String vmNetMac;

    private String objName;

    private String objType;

    private String objSid;

    private String objMonitorNodeId;

    private String subnet;

    private String mgtObjName;

    /**
     * @return the mgtObjName
     */
    public String getMgtObjName() {
        return mgtObjName;
    }

    /**
     * @param mgtObjName the mgtObjName to set
     */
    public void setMgtObjName(String mgtObjName) {
        this.mgtObjName = mgtObjName;
    }

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
     * @return 所属网络ID
     */
    public String getResNetworkSid() {
        return resNetworkSid;
    }

    /**
     * @param resNetworkSid 所属网络ID
     */
    public void setResNetworkSid(String resNetworkSid) {
        this.resNetworkSid = resNetworkSid;
    }

    /**
     * @return IP类型(IPV4，IPV6)
     */
    public String getIpType() {
        return ipType;
    }

    /**
     * @param ipType IP类型(IPV4，IPV6)
     */
    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    /**
     * @return IP地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress IP地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return 对应公网IP
     */
    public String getMapPublicIp() {
        return mapPublicIp;
    }

    /**
     * @param mapPublicIp 对应公网IP
     */
    public void setMapPublicIp(String mapPublicIp) {
        this.mapPublicIp = mapPublicIp;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 状态
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

    public String getIpTypeName() {
        return ipTypeName;
    }

    public void setIpTypeName(String ipTypeName) {
        this.ipTypeName = ipTypeName;
    }

    public String getIpAddressLike() {
        return ipAddressLike;
    }

    public void setIpAddressLike(String ipAddressLike) {
        this.ipAddressLike = ipAddressLike;
    }

    /**
     * @return the resPoolType
     */
    public String getResPoolType() {
        return resPoolType;
    }

    /**
     * @param resPoolType the resPoolType to set
     */
    public void setResPoolType(String resPoolType) {
        this.resPoolType = resPoolType;
    }

    /**
     * @return the operate
     */
    public String getOperate() {
        return operate;
    }

    /**
     * @param operate the operate to set
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getParentTopologySid() {
        return parentTopologySid;
    }

    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getResIpSids() {
        return resIpSids;
    }

    public void setResIpSids(String resIpSids) {
        this.resIpSids = resIpSids;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
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

    /**
     * @return the portGroupName
     */
    public String getPortGroupName() {
        return portGroupName;
    }

    /**
     * @param portGroupName the portGroupName to set
     */
    public void setPortGroupName(String portGroupName) {
        this.portGroupName = portGroupName;
    }

    /**
     * @return the vmPrimaryNet
     */
    public String getVmPrimaryNet() {
        return vmPrimaryNet;
    }

    /**
     * @param vmPrimaryNet the vmPrimaryNet to set
     */
    public void setVmPrimaryNet(String vmPrimaryNet) {
        this.vmPrimaryNet = vmPrimaryNet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the vmNetMac
     */
    public String getVmNetMac() {
        return vmNetMac;
    }

    /**
     * @param vmNetMac the vmNetMac to set
     */
    public void setVmNetMac(String vmNetMac) {
        this.vmNetMac = vmNetMac;
    }


    public String getResBizName() {
        return resBizName;
    }

    public void setResBizName(String resBizName) {
        this.resBizName = resBizName;
    }

    /**
     * @return the objName
     */
    public String getObjName() {
        return objName;
    }

    /**
     * @param objName the objName to set
     */
    public void setObjName(String objName) {
        this.objName = objName;
    }

    /**
     * @return the objType
     */
    public String getObjType() {
        return objType;
    }

    /**
     * @param objType the objType to set
     */
    public void setObjType(String objType) {
        this.objType = objType;
    }

    /**
     * @return the objSid
     */
    public String getObjSid() {
        return objSid;
    }

    /**
     * @param objSid the objSid to set
     */
    public void setObjSid(String objSid) {
        this.objSid = objSid;
    }

    /**
     * @return the objMonitorNodeId
     */
    public String getObjMonitorNodeId() {
        return objMonitorNodeId;
    }

    /**
     * @param objMonitorNodeId the objMonitorNodeId to set
     */
    public void setObjMonitorNodeId(String objMonitorNodeId) {
        this.objMonitorNodeId = objMonitorNodeId;
    }

    /**
     * @return the subnet
     */
    public String getSubnet() {
        return subnet;
    }

    /**
     * @param subnet the subnet to set
     */
    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}