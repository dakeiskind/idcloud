package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DvSwitchVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.NetworkVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VSwitchVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResNetwork extends ResBase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resNetworkSid;

    /**
     * 资源ID
     */
    private String resNetworkSids;

    /**
     * 管理对象sid
     */
    private Long mgtObjSid;

    /**
     * 关联外部网络SID
     */
    private String extNetId;

    /**
     * 业务sid
     */
    private Long resBizSid;

    /**
     * 资源分区sid
     */
    private String zone;

    /**
     * 私有网络名称
     */
    private String vpcName;

    /**
     * 主业务名称
     */
    private String parentBizName;

    /**
     * 子业务名称
     */
    private String subBizName;

    /**
     * 业务级别
     */
    private String resBizLevel;

    /**
     * 上级拓扑结构ID
     */
    private String parentTopologySid;

    /**
     * 所属资源池ID
     */
    private String resPoolSid;

    /**
     * 网络名称
     */
    private String networkName;

    /**
     * 网络名称查询
     */
    private String networkNameLike;

    /**
     * 网络类型
     */
    private String networkType;

    /**
     * 网络类型名称
     */
    private String networkTypeName;

    /**
     * 描述
     */
    private String description;

    /**
     * IP类型(IPV4，IPV6)
     */
    private String ipType;

    /**
     * IP类型名称
     */
    private String ipTypeName;

    /**
     * 子网
     */
    private String subnet;

    /**
     * 子网掩码
     */
    private String subnetMask;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 首选DNS
     */
    private String dns1;

    /**
     * 备选DNS
     */
    private String dns2;

    /**
     * 保留IP段1开始
     */
    private String ipRetainStart1;

    /**
     * 保留IP段1结束
     */
    private String ipRetainEnd1;

    /**
     * 保留IP段2开始
     */
    private String ipRetainStart2;

    /**
     * 保留IP段2结束
     */
    private String ipRetainEnd2;

    /**
     * 保留IP段3开始
     */
    private String ipRetainStart3;

    /**
     * 保留IP段3结束
     */
    private String ipRetainEnd3;

    /**
     * ip使用数
     */
    private Integer ipUseCount;

    /**
     * ip总数
     */
    private Integer ipTotalCount;

    /**
     * VLAN ID
     */
    private String vlanId;

    /**
     * VLAN ID 名称
     */
    private String vlanIdName;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态
     */
    private String statusName;

    /**
     * 网络标签
     */
    private String networkTag;

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
     * 内部网络个数
     */
    private Integer totalCount;

    /**
     * 外部网络个数
     */
    private Integer pniCount;

    /**
     * 外部网络个数
     */
    private Integer pneCount;

    /**
     * 资源级
     */

    private Long bizSid;

    private String ipAddress;

    private String ipOwnerNetwork;

    private String vmName;

    private Long parentBizSid;

    private String uuid;

    private String ipAvailableCount;
    /**
     * 标准端口组
     */
    private List<ResVs> vSwitches = new ArrayList<ResVs>();
    /**
     * 分布式端口组
     */
    private List<ResVs> dvSwitches = new ArrayList<ResVs>();

    public ResNetwork() {

    }

    /**
     * MQ网络对想转行成平台网络对象
     *
     * @param networkVO
     */
    public ResNetwork(NetworkVO networkVO) {
        if (networkVO != null) {
            List<VSwitchVO> vSwithcVOs = networkVO.getSvSwitchs();
            if (vSwithcVOs != null && vSwithcVOs.size() > 0) {
                for (VSwitchVO vSwithcVO : vSwithcVOs) {
                    this.vSwitches.add(new ResVs(vSwithcVO));

                }
            }

            List<DvSwitchVO> dvSwithcVOs = networkVO.getDvSwitchs();
            if (dvSwithcVOs != null && dvSwithcVOs.size() > 0) {
                for (DvSwitchVO dvSwithcVO : dvSwithcVOs) {
                    this.dvSwitches.add(new ResVs(dvSwithcVO));

                }
            }
        }
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the vSwitches
     */
    public List<ResVs> getvSwitches() {
        return vSwitches;
    }

    /**
     * @param vSwitches the vSwitches to set
     */
    public void setvSwitches(List<ResVs> vSwitches) {
        this.vSwitches = vSwitches;
    }

    /**
     * @return the dvSwitches
     */
    public List<ResVs> getDvSwitches() {
        return dvSwitches;
    }

    /**
     * @param dvSwitches the dvSwitches to set
     */
    public void setDvSwitches(List<ResVs> dvSwitches) {
        this.dvSwitches = dvSwitches;
    }

    public Long getParentBizSid() {
        return parentBizSid;
    }

    public void setParentBizSid(Long parentBizSid) {
        this.parentBizSid = parentBizSid;
    }

    /**
     * @return 资源ID
     */
    public String getResNetworkSid() {
        return resNetworkSid;
    }

    /**
     * @param resNetworkSid 资源ID
     */
    public void setResNetworkSid(String resNetworkSid) {
        this.resNetworkSid = resNetworkSid;
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
     * @return 网络名称
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * @param networkName 网络名称
     */
    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    /**
     * @return 网络类型
     */
    public String getNetworkType() {
        return networkType;
    }

    /**
     * @param networkType 网络类型
     */
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
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
     * @return 子网
     */
    public String getSubnet() {
        return subnet;
    }

    /**
     * @param subnet 子网
     */
    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    /**
     * @return 子网掩码
     */
    public String getSubnetMask() {
        return subnetMask;
    }

    /**
     * @param subnetMask 子网掩码
     */
    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    /**
     * @return 网关
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * @param gateway 网关
     */
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    /**
     * @return 首选DNS
     */
    public String getDns1() {
        return dns1;
    }

    /**
     * @param dns1 首选DNS
     */
    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    /**
     * @return 备选DNS
     */
    public String getDns2() {
        return dns2;
    }

    /**
     * @param dns2 备选DNS
     */
    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    /**
     * @return 保留IP段1开始
     */
    public String getIpRetainStart1() {
        return ipRetainStart1;
    }

    /**
     * @param ipRetainStart1 保留IP段1开始
     */
    public void setIpRetainStart1(String ipRetainStart1) {
        this.ipRetainStart1 = ipRetainStart1;
    }

    /**
     * @return 保留IP段1结束
     */
    public String getIpRetainEnd1() {
        return ipRetainEnd1;
    }

    /**
     * @param ipRetainEnd1 保留IP段1结束
     */
    public void setIpRetainEnd1(String ipRetainEnd1) {
        this.ipRetainEnd1 = ipRetainEnd1;
    }

    /**
     * @return 保留IP段2开始
     */
    public String getIpRetainStart2() {
        return ipRetainStart2;
    }

    /**
     * @param ipRetainStart2 保留IP段2开始
     */
    public void setIpRetainStart2(String ipRetainStart2) {
        this.ipRetainStart2 = ipRetainStart2;
    }

    /**
     * @return 保留IP段2结束
     */
    public String getIpRetainEnd2() {
        return ipRetainEnd2;
    }

    /**
     * @param ipRetainEnd2 保留IP段2结束
     */
    public void setIpRetainEnd2(String ipRetainEnd2) {
        this.ipRetainEnd2 = ipRetainEnd2;
    }

    /**
     * @return 保留IP段3开始
     */
    public String getIpRetainStart3() {
        return ipRetainStart3;
    }

    /**
     * @param ipRetainStart3 保留IP段3开始
     */
    public void setIpRetainStart3(String ipRetainStart3) {
        this.ipRetainStart3 = ipRetainStart3;
    }

    /**
     * @return 保留IP段3结束
     */
    public String getIpRetainEnd3() {
        return ipRetainEnd3;
    }

    /**
     * @param ipRetainEnd3 保留IP段3结束
     */
    public void setIpRetainEnd3(String ipRetainEnd3) {
        this.ipRetainEnd3 = ipRetainEnd3;
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
     * @return 网络标签
     */
    public String getNetworkTag() {
        return networkTag;
    }

    /**
     * @param networkTag 网络标签
     */
    public void setNetworkTag(String networkTag) {
        this.networkTag = networkTag;
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

    public String getNetworkTypeName() {
        return networkTypeName;
    }

    public void setNetworkTypeName(String networkTypeName) {
        this.networkTypeName = networkTypeName;
    }

    public String getIpTypeName() {
        return ipTypeName;
    }

    public void setIpTypeName(String ipTypeName) {
        this.ipTypeName = ipTypeName;
    }

    public String getVlanIdName() {
        return vlanIdName;
    }

    public void setVlanIdName(String vlanIdName) {
        this.vlanIdName = vlanIdName;
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

    public String getNetworkNameLike() {
        return networkNameLike;
    }

    public void setNetworkNameLike(String networkNameLike) {
        this.networkNameLike = networkNameLike;
    }

    public Integer getIpUseCount() {
        return ipUseCount;
    }

    public void setIpUseCount(Integer ipUseCount) {
        this.ipUseCount = ipUseCount;
    }

    public Integer getIpTotalCount() {
        return ipTotalCount;
    }

    public void setIpTotalCount(Integer ipTotalCount) {
        this.ipTotalCount = ipTotalCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPniCount() {
        return pniCount;
    }

    public void setPniCount(Integer pniCount) {
        this.pniCount = pniCount;
    }

    public Integer getPneCount() {
        return pneCount;
    }

    public void setPneCount(Integer pneCount) {
        this.pneCount = pneCount;
    }

    public Long getResBizSid() {
        return resBizSid;
    }

    public void setResBizSid(Long resBizSid) {
        this.resBizSid = resBizSid;
    }

    public String getResBizLevel() {
        return resBizLevel;
    }

    public void setResBizLevel(String resBizLevel) {
        this.resBizLevel = resBizLevel;
    }

    public Long getBizSid() {
        return bizSid;
    }

    public void setBizSid(Long bizSid) {
        this.bizSid = bizSid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getParentBizName() {
        return parentBizName;
    }

    public void setParentBizName(String parentBizName) {
        this.parentBizName = parentBizName;
    }

    public String getSubBizName() {
        return subBizName;
    }

    public void setSubBizName(String subBizName) {
        this.subBizName = subBizName;
    }

    public String getIpOwnerNetwork() {
        return ipOwnerNetwork;
    }

    public void setIpOwnerNetwork(String ipOwnerNetwork) {
        this.ipOwnerNetwork = ipOwnerNetwork;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getResNetworkSids() {
        return resNetworkSids;
    }

    public void setResNetworkSids(String resNetworkSids) {
        this.resNetworkSids = resNetworkSids;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getExtNetId() {
        return extNetId;
    }

    public void setExtNetId(String extNetId) {
        this.extNetId = extNetId;
    }

    /**
     * @return the ipAvailableCount
     */
    public String getIpAvailableCount() {
        return ipAvailableCount;
    }

    /**
     * @param ipAvailableCount the ipAvailableCount to set
     */
    public void setIpAvailableCount(String ipAvailableCount) {
        this.ipAvailableCount = ipAvailableCount;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getVpcName() {
        return vpcName;
    }

    public void setVpcName(String vpcName) {
        this.vpcName = vpcName;
    }
}