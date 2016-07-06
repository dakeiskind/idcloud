package com.h3c.idcloud.core.pojo.dto.res;


import com.google.common.base.Strings;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataStoreVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VmVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResHost implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resHostSid;

    /**
     * 关联的主机
     */
    private String resNetworkSid;

    /**
     * 多个资源ID
     */
    private String resHostSids;

    /**
     * 业务sid
     */
    private Long resBizSid;

    private String isViosFlag;

    /**
     * 所属业务
     */
    private String mgtObjName;

    /**
     * 所属集群
     */
    private String ownerVc;

    /**
     * 业务级别
     */
    private String resBizLevel;

    /**
     * 拓扑结构SID
     */
    private String resTopologySid;

    /**
     * 拓扑结构类型
     */
    private String resHostTopologyType;

    /**
     * 上级拓扑结构ID
     */
    private String parentTopologySid;

    /**
     * 上级拓扑结构ID
     */
    private String parentVeTopologySid;

    /**
     * 上级拓扑结构名称
     */
    private String parentTopologyName;

    /**
     * 所属资源池ID
     */
    private String resPoolSid;

    /**
     * 不属于资源池
     */
    private String isNullResPoolSid;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 与主机名相似
     */
    private String hostNameLike;

    /**
     * CPU个数
     */
    private Integer cpuNumber;

    /**
     * CPU核数
     */
    private Integer cpuCores;

    /**
     * CPU总核数
     */
    private Integer cpuTotalCores;

    /**
     * CPU分配核数（资源检查时用）
     */
    private Integer cpuAllotCores;

    /**
     * CPU分配率
     */
    private String cpuAllotRate;

    /**
     * CPU主频
     */
    private Double cpuGhz;

    /**
     * CPU使用主频
     */
    private Double useCpuGhz;

    /**
     * CPU使用率
     */
    private String hostCpuUsage;

    /**
     * 内存使用大小
     */
    private Double useMemorySize;

    /**
     * CPU类型
     */
    private String cpuType;

    /**
     * 可用CPU
     */
    private Double cpuAvailable;

    /**
     * 可用内存
     */
    private Long memoryAvailable;


    /**
     * 内存大小
     */
    private Double memorySize;

    /**
     * 内存大小-GB
     */
    private String memorySizeGb;

    /**
     * 内存分配大小
     */
    private Long memoryAllotSize;

    /**
     * 内存分配大小（资源检查时用）
     */
    private long vmMemoryAllotSize;

    /**
     * 内存使用率
     */
    private String hostMemoryUsage;

    /**
     * 内存分配率
     */
    private String memoryAllotRate;

    /**
     * 主机下的存储（资源检查时用）
     */
    private List<ResStorage> resStorages = new ArrayList<ResStorage>();

    /**
     * 内存使用率
     */
    private Float memoryUsage;

    /**
     * CPU使用率
     */
    private Float cpuUsage;

    /**
     * 网卡个数
     */
    private Integer nicNumber;

    /**
     * 主机IP
     */
    private String hostIp;

    /**
     * 主机IP查询
     */
    private String hostIpLike;

    /**
     * 主机IP查询
     */
    private String vmIpLike;

    /**
     * 管理IP
     */
    private String managementIp;

    /**
     * 主机操作系统
     */
    private String hostOsType;

    /**
     * 主机操作系统名称
     */
    private String hostOsTypeName;

    /**
     * 管理用户
     */
    private String managementUser;

    /**
     * 管理用户密码
     */
    private String managementPwd;

    /**
     * 制造商
     */
    private String vendor;

    /**
     * 主机型号
     */
    private String model;

    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 主机状态
     */
    private String status;

    /**
     * 主机状态名称
     */
    private String statusName;

    /**
     * UUID
     */
    private String uuid;

    /**
     * 区域ID
     */
    private String zone;

    /**
     * 监控节点ID
     */
    private String monitorNodeId;

    /**
     * 部署节点ID
     */
    private String deployNodeId;

    /**
     * 是否安装VIOS
     */
    private String virtualIoServerCapable;

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
     * 内存分配大小
     */
    private Long vmMemorySize;

    /**
     * 内存分配大小
     */
    private int vmCpuCore;

    /**
     * 统计主机-- 总数
     */
    private int staTotalHost;

    /**
     * 统计主机-- 正常数
     */
    private int staNormalHost;

    /**
     * 统计主机-- 离线数
     */
    private int staOffineHost;

    /**
     * 统计主机-- 维护数
     */
    private int staMaintainHost;

    /**
     * 统计主机-- 故障数
     */
    private int staFaultHost;

    /**
     * 统计内存的总量
     */
    private Long memoryNum;

    /**
     * 主机下的虚拟机
     */
    private Integer vmCount;

    /**
     * 存储总容量
     */
    private Long totalCapacity;

    private String viosUser;

    private String hostEquipType;

    private String equipCategoryName;

    private String equipHostOsType;

    private String staTotalServer;

    private Long sysDiskSize;

    private List<ResOsSoftware> softwares = new ArrayList<ResOsSoftware>();

    private String hostVe;

    private List<ResOsUser> resOsUsers;
    /**
     * 主机下虚拟机列表
     */
    private List<ResVm> vms = new ArrayList<ResVm>();
    /***
     * 主机下存储
     */
    private List<ResStorage> dataStorages = new ArrayList<ResStorage>();
    /**
     * 主机下网络
     */
    private Map<String, Object> netWorks = new HashMap<String, Object>();
    /**
     * 主机下网络
     */
    private ResNetwork resNetWorks = new ResNetwork();
    private String resPoolName;
    /**
     * 主机CPU POOL
     */
    private String cpuPoolSid;
    /**
     * 主机的物理位置
     */
    private String equipRoomSid;
    /**
     * 主机所属机柜
     */
    private String equipCabinetSid;
    private String equipRackSid;
    /**
     * 设备分类
     */
    private String equipCategory;
    /**
     * 设备类型
     */
    private String equipType;
    /**
     * 位置编号
     */
    private String locationNumber;
    /**
     * 名称
     */
    private String name;
    private String nameLike;
    /**
     * 序列号
     */
    private String phySerialNumber;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String phyModel;
    /**
     * 远程管理IP1
     */
    private String remoteMgtIp1;
    /**
     * 远程管理IP2
     */
    private String remoteMgtIp2;
    /**
     * 远程管理用户
     */
    private String remoteMgtUser;
    /**
     * 远程管理密码
     */
    private String remoteMgtPwd;
    /**
     * 关联IP地址
     */
    private String relevanceIp;
    /**
     * 主机所属机房
     */
    private String roomName;
    /**
     * 主机所属机柜
     */
    private String cabinetName;
    /**
     * 主机所属机架
     */
    private String rackName;
    private Long mgtObjSid;
    private String viosPassWord;
    private String cabinetFrameName;
    private String maintCompany;
    private String maintTel;
    private Date purchaseDate;
    private Long warrantyPeriod;
    private String equipSupplier;
    private Date startEndDate;

    public ResHost() {

    }

    /**
     * MQ主机对象转换平台主机对象
     *
     * @param hostVo
     */
    public ResHost(HostVO hostVo) {
        this.uuid = hostVo.getUuid();
        // 主机状态设置
        if (!Strings.isNullOrEmpty(hostVo.getStatus())) {
            if ("poweredOn".equals(hostVo.getStatus())
                    || "up".equalsIgnoreCase(hostVo.getStatus())
                    || "operating".equalsIgnoreCase(hostVo.getStatus())) {
                this.status = WebConstants.ResHostStatus.NORMAL;
            }
            if ("poweredOff".equals(hostVo.getStatus())
                    || "down".equalsIgnoreCase(hostVo.getStatus())
                    || "Failed Authentication".equals(hostVo.getStatus())) {
                this.status = WebConstants.ResHostStatus.OUTLINE;
            }
            if ("standBy".equals(hostVo.getStatus())) {
                this.status = WebConstants.ResHostStatus.STANDBY;
            }
            if ("unknown".equals(hostVo.getStatus())) {
                this.status = WebConstants.ResHostStatus.UNKNOWN;
            }
        }

        if (!Strings.isNullOrEmpty(hostVo.getCpuUsage())) {
            this.useCpuGhz = Double.parseDouble(hostVo.getCpuUsage());
        }
        if (!Strings.isNullOrEmpty(hostVo.getMenUsage())) {
//			this.memoryUsage = Float.parseFloat(hostVo.getMenUsage());
            this.useMemorySize = Double.parseDouble(hostVo.getMenUsage());

        }
        this.hostName = hostVo.getHostName();

        if (!Strings.isNullOrEmpty(hostVo.getCpuNumber())) {
//			this.cpuNumber = Integer.parseInt(hostVo.getCpuNumber());
            if (hostVo.getCpuNumber().matches("[0-9]+(\\.[0-9]+)?")) {
                this.cpuNumber = new BigDecimal(hostVo.getCpuNumber()).intValue();
            } else {
                this.cpuNumber = 0;
            }
        }
        if (!Strings.isNullOrEmpty(hostVo.getCpuCores())) {
            this.cpuCores = Integer.parseInt(hostVo.getCpuCores());
        }
        this.cpuType = hostVo.getCpuType();

        if (!Strings.isNullOrEmpty(hostVo.getMemorySize())) {
            this.memorySize = Double.parseDouble(hostVo.getMemorySize()) / 1024 / 1024;
        }
        if (!Strings.isNullOrEmpty(hostVo.getNicNumber())) {
            this.nicNumber = Integer.parseInt(hostVo.getNicNumber());
        }
        this.hostIp = hostVo.getHostIp();
        this.hostOsType = hostVo.getHostOsType();
        this.model = hostVo.getServerModel();
        this.virtualIoServerCapable = hostVo.getViosCapable();
        if (!Strings.isNullOrEmpty(hostVo.getAvailCpu())) {
            this.cpuAvailable = Double.parseDouble(hostVo.getAvailCpu());
        }
        if (!Strings.isNullOrEmpty(hostVo.getAvailMem())) {
            this.memoryAvailable = Long.parseLong(hostVo.getAvailMem());
        }

        this.serialNumber = hostVo.getUuid();
        if (!Strings.isNullOrEmpty(hostVo.getCpuGhz())) {
            this.cpuGhz = Double.parseDouble(hostVo.getCpuGhz());
        }
        if (hostVo.getVms() != null && hostVo.getVms().size() > 0) {
            for (VmVO vmVo : hostVo.getVms()) {
                this.vms.add(new ResVm(vmVo));
            }
        }

        if (hostVo.getDataStorages() != null
                && hostVo.getDataStorages().size() > 0) {
            for (DataStoreVO dataStoreVo : hostVo.getDataStorages()) {
                this.resStorages.add(new ResStorage(dataStoreVo));
            }
        }
        this.resNetWorks = new ResNetwork(hostVo.getNetWorks());
    }

    public List<ResOsUser> getResOsUsers() {
        return resOsUsers;
    }

    public void setResOsUsers(List<ResOsUser> resOsUsers) {
        this.resOsUsers = resOsUsers;
    }

    public String getHostVe() {
        return hostVe;
    }

    public void setHostVe(String hostVe) {
        this.hostVe = hostVe;
    }

    public List<ResOsSoftware> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<ResOsSoftware> softwares) {
        this.softwares = softwares;
    }

    public Long getSysDiskSize() {
        return sysDiskSize;
    }

    public void setSysDiskSize(Long sysDiskSize) {
        this.sysDiskSize = sysDiskSize;
    }

    /**
     * @return the viosPassWord
     */
    public String getViosPassWord() {
        return viosPassWord;
    }

    /**
     * @param viosPassWord the viosPassWord to set
     */
    public void setViosPassWord(String viosPassWord) {
        this.viosPassWord = viosPassWord;
    }

    /**
     * @return the viosUser
     */
    public String getViosUser() {
        return viosUser;
    }

    /**
     * @param viosUser the viosUser to set
     */
    public void setViosUser(String viosUser) {
        this.viosUser = viosUser;
    }

    public String getEquipCategoryName() {
        return equipCategoryName;
    }

    public void setEquipCategoryName(String equipCategoryName) {
        this.equipCategoryName = equipCategoryName;
    }

    public String getHostEquipType() {
        return hostEquipType;
    }

    public void setHostEquipType(String hostEquipType) {
        this.hostEquipType = hostEquipType;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getResPoolName() {
        return resPoolName;
    }

    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    public List<ResStorage> getDataStorages() {
        return dataStorages;
    }

    public void setDataStorages(List<ResStorage> dataStorages) {
        this.dataStorages = dataStorages;
    }

    public Map<String, Object> getNetWorks() {
        return netWorks;
    }

    public void setNetWorks(Map<String, Object> netWorks) {
        this.netWorks = netWorks;
    }

    public List<ResVm> getVms() {
        return vms;
    }

    public void setVms(List<ResVm> vms) {
        this.vms = vms;
    }

    public String getHostCpuUsage() {
        return hostCpuUsage;
    }

    public void setHostCpuUsage(String hostCpuUsage) {
        this.hostCpuUsage = hostCpuUsage;
    }

    public String getHostMemoryUsage() {
        return hostMemoryUsage;
    }

    public void setHostMemoryUsage(String hostMemoryUsage) {
        this.hostMemoryUsage = hostMemoryUsage;
    }

    /**
     * @return 资源ID
     */
    public String getResHostSid() {
        return resHostSid;
    }

    /**
     * @param resHostSid 资源ID
     */
    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
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
     * @return the cpuUsage
     */
    public Float getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage the cpuUsage to set
     */
    public void setCpuUsage(Float cpuUsage) {
        this.cpuUsage = cpuUsage;
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
     * @return 主机名
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName 主机名
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return CPU个数
     */
    public Integer getCpuNumber() {
        return cpuNumber;
    }

    /**
     * @param cpuNumber CPU个数
     */
    public void setCpuNumber(Integer cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    /**
     * @return CPU总核数
     */
    public Integer getCpuCores() {
        return cpuCores;
    }

    /**
     * @param cpuCores CPU总核数
     */
    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    /**
     * @return CPU主频
     */
    public Double getCpuGhz() {
        return cpuGhz;
    }

    /**
     * @param cpuGhz CPU主频
     */
    public void setCpuGhz(Double cpuGhz) {
        this.cpuGhz = cpuGhz;
    }

    /**
     * @return CPU类型
     */
    public String getCpuType() {
        return cpuType;
    }

    /**
     * @param cpuType CPU类型
     */
    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    /**
     * @return 内存大小
     */
    public Double getMemorySize() {
        return memorySize;
    }

    /**
     * @param memorySize 内存大小
     */
    public void setMemorySize(Double memorySize) {
        this.memorySize = memorySize;
    }

    /**
     * @return 网卡个数
     */
    public Integer getNicNumber() {
        return nicNumber;
    }

    /**
     * @param nicNumber 网卡个数
     */
    public void setNicNumber(Integer nicNumber) {
        this.nicNumber = nicNumber;
    }

    /**
     * @return 主机IP
     */
    public String getHostIp() {
        return hostIp;
    }

    /**
     * @param hostIp 主机IP
     */
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    /**
     * @return 管理IP
     */
    public String getManagementIp() {
        return managementIp;
    }

    /**
     * @param managementIp 管理IP
     */
    public void setManagementIp(String managementIp) {
        this.managementIp = managementIp;
    }

    /**
     * @return 主机操作系统
     */
    public String getHostOsType() {
        return hostOsType;
    }

    /**
     * @param hostOsType 主机操作系统
     */
    public void setHostOsType(String hostOsType) {
        this.hostOsType = hostOsType;
    }

    /**
     * @return 制造商
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * @param vendor 制造商
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * @return 序列号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber 序列号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return 主机状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 主机状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return 监控节点ID
     */
    public String getMonitorNodeId() {
        return monitorNodeId;
    }

    /**
     * @param monitorNodeId 监控节点ID
     */
    public void setMonitorNodeId(String monitorNodeId) {
        this.monitorNodeId = monitorNodeId;
    }

    /**
     * @return 部署节点ID
     */
    public String getDeployNodeId() {
        return deployNodeId;
    }

    /**
     * @param deployNodeId 部署节点ID
     */
    public void setDeployNodeId(String deployNodeId) {
        this.deployNodeId = deployNodeId;
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

    public String getHostOsTypeName() {
        return hostOsTypeName;
    }

    public void setHostOsTypeName(String hostOsTypeName) {
        this.hostOsTypeName = hostOsTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getHostNameLike() {
        return hostNameLike;
    }

    public void setHostNameLike(String hostNameLike) {
        this.hostNameLike = hostNameLike;
    }

    /**
     * @return the memoryUsage
     */
    public Float getMemoryUsage() {
        return memoryUsage;
    }

    /**
     * @param memoryUsage the memoryUsage to set
     */
    public void setMemoryUsage(Float memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Integer getCpuTotalCores() {
        return cpuTotalCores;
    }

    public void setCpuTotalCores(Integer cpuTotalCores) {
        this.cpuTotalCores = cpuTotalCores;
    }

    public Integer getCpuAllotCores() {
        return cpuAllotCores;
    }

    public void setCpuAllotCores(Integer cpuAllotCores) {
        this.cpuAllotCores = cpuAllotCores;
    }

    public Long getMemoryAllotSize() {
        return memoryAllotSize;
    }

    public void setMemoryAllotSize(Long memoryAllotSize) {
        this.memoryAllotSize = memoryAllotSize;
    }

    /**
     * @return the vmMemoryAllotSize
     */
    public long getVmMemoryAllotSize() {
        return vmMemoryAllotSize;
    }

    /**
     * @param vmMemoryAllotSize the vmMemoryAllotSize to set
     */
    public void setVmMemoryAllotSize(long vmMemoryAllotSize) {
        this.vmMemoryAllotSize = vmMemoryAllotSize;
    }

    public Double getUseCpuGhz() {
        return useCpuGhz;
    }

    public void setUseCpuGhz(Double useCpuGhz) {
        this.useCpuGhz = useCpuGhz;
    }

    public Double getUseMemorySize() {
        return useMemorySize;
    }

    public void setUseMemorySize(Double useMemorySize) {

        this.useMemorySize = useMemorySize;
    }

    /**
     * @return the vmMemorySize
     */
    public Long getVmMemorySize() {
        return vmMemorySize;
    }

    /**
     * @param vmMemorySize the vmMemorySize to set
     */
    public void setVmMemorySize(Long vmMemorySize) {
        this.vmMemorySize = vmMemorySize;
    }

    /**
     * @return the vmCpuCore
     */
    public int getVmCpuCore() {
        return vmCpuCore;
    }

    /**
     * @param vmCpuCore the vmCpuCore to set
     */
    public void setVmCpuCore(int vmCpuCore) {
        this.vmCpuCore = vmCpuCore;
    }

    /**
     * @return the resStorages
     */
    public List<ResStorage> getResStorages() {
        return resStorages;
    }

    /**
     * @param resStorages the resStorages to set
     */
    public void setResStorages(List<ResStorage> resStorages) {
        this.resStorages = resStorages;
    }

    public int getStaTotalHost() {
        return staTotalHost;
    }

    public void setStaTotalHost(int staTotalHost) {
        this.staTotalHost = staTotalHost;
    }

    public int getStaNormalHost() {
        return staNormalHost;
    }

    public void setStaNormalHost(int staNormalHost) {
        this.staNormalHost = staNormalHost;
    }

    public int getStaOffineHost() {
        return staOffineHost;
    }

    public void setStaOffineHost(int staOffineHost) {
        this.staOffineHost = staOffineHost;
    }

    public int getStaMaintainHost() {
        return staMaintainHost;
    }

    public void setStaMaintainHost(int staMaintainHost) {
        this.staMaintainHost = staMaintainHost;
    }

    public int getStaFaultHost() {
        return staFaultHost;
    }

    public void setStaFaultHost(int staFaultHost) {
        this.staFaultHost = staFaultHost;
    }

    public Long getMemoryNum() {
        return memoryNum;
    }

    public void setMemoryNum(Long memoryNum) {
        this.memoryNum = memoryNum;
    }

    public Integer getVmCount() {
        return vmCount;
    }

    public void setVmCount(Integer vmCount) {
        this.vmCount = vmCount;
    }

    public Long getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getMemorySizeGb() {
        return memorySizeGb;
    }

    public void setMemorySizeGb(String memorySizeGb) {
        this.memorySizeGb = memorySizeGb;
    }

    public String getCpuAllotRate() {
        return cpuAllotRate;
    }

    public void setCpuAllotRate(String cpuAllotRate) {
        this.cpuAllotRate = cpuAllotRate;
    }

    public String getMemoryAllotRate() {
        return memoryAllotRate;
    }

    public void setMemoryAllotRate(String memoryAllotRate) {
        this.memoryAllotRate = memoryAllotRate;
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

    public String getParentTopologyName() {
        return parentTopologyName;
    }

    public void setParentTopologyName(String parentTopologyName) {
        this.parentTopologyName = parentTopologyName;
    }

    public String getIsNullResPoolSid() {
        return isNullResPoolSid;
    }

    public void setIsNullResPoolSid(String isNullResPoolSid) {
        this.isNullResPoolSid = isNullResPoolSid;
    }

    public String getResHostSids() {
        return resHostSids;
    }

    public void setResHostSids(String resHostSids) {
        this.resHostSids = resHostSids;
    }

    public String getOwnerVc() {
        return ownerVc;
    }

    public void setOwnerVc(String ownerVc) {
        this.ownerVc = ownerVc;
    }

    /**
     * @return the resNetWorks
     */
    public ResNetwork getResNetWorks() {
        return resNetWorks;
    }

    /**
     * @param resNetWorks the resNetWorks to set
     */
    public void setResNetWorks(ResNetwork resNetWorks) {
        this.resNetWorks = resNetWorks;
    }

    public String getMgtObjName() {
        return mgtObjName;
    }

    public void setMgtObjName(String mgtObjName) {
        this.mgtObjName = mgtObjName;
    }

    public String getVirtualIoServerCapable() {
        return virtualIoServerCapable;
    }

    public void setVirtualIoServerCapable(String virtualIoServerCapable) {
        this.virtualIoServerCapable = virtualIoServerCapable;
    }

    /**
     * @return the isViosFlag
     */
    public String getIsViosFlag() {
        return isViosFlag;
    }

    /**
     * @param isViosFlag the isViosFlag to set
     */
    public void setIsViosFlag(String isViosFlag) {
        this.isViosFlag = isViosFlag;
    }

    /**
     * @return the cabinetName
     */
    public String getCabinetName() {
        return cabinetName;
    }

    /**
     * @param cabinetName the cabinetName to set
     */
    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    /**
     * @return the resHostTopologyType
     */
    public String getResHostTopologyType() {
        return resHostTopologyType;
    }

    /**
     * @param resHostTopologyType the resHostTopologyType to set
     */
    public void setResHostTopologyType(String resHostTopologyType) {
        this.resHostTopologyType = resHostTopologyType;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the managementUser
     */
    public String getManagementUser() {
        return managementUser;
    }

    /**
     * @param managementUser the managementUser to set
     */
    public void setManagementUser(String managementUser) {
        this.managementUser = managementUser;
    }

    /**
     * @return the managementPwd
     */
    public String getManagementPwd() {
        return managementPwd;
    }

    /**
     * @param managementPwd the managementPwd to set
     */
    public void setManagementPwd(String managementPwd) {
        this.managementPwd = managementPwd;
    }

    /**
     * @return the cpuAvailable
     */
    public Double getCpuAvailable() {
        return cpuAvailable;
    }

    /**
     * @param cpuAvailable the cpuAvailable to set
     */
    public void setCpuAvailable(Double cpuAvailable) {
        this.cpuAvailable = cpuAvailable;
    }

    /**
     * @return the memoryAvailable
     */
    public Long getMemoryAvailable() {
        return memoryAvailable;
    }

    /**
     * @param memoryAvailable the memoryAvailable to set
     */
    public void setMemoryAvailable(Long memoryAvailable) {
        this.memoryAvailable = memoryAvailable;
    }

    public String getCpuPoolSid() {
        return cpuPoolSid;
    }

    public void setCpuPoolSid(String cpuPoolSid) {
        this.cpuPoolSid = cpuPoolSid;
    }

    /**
     * @return the equipRoomSid
     */
    public String getEquipRoomSid() {
        return equipRoomSid;
    }

    /**
     * @param equipRoomSid the equipRoomSid to set
     */
    public void setEquipRoomSid(String equipRoomSid) {
        this.equipRoomSid = equipRoomSid;
    }

    /**
     * @return the equipCabinetSid
     */
    public String getEquipCabinetSid() {
        return equipCabinetSid;
    }

    /**
     * @param equipCabinetSid the equipCabinetSid to set
     */
    public void setEquipCabinetSid(String equipCabinetSid) {
        this.equipCabinetSid = equipCabinetSid;
    }

    /**
     * @return the equipCategory
     */
    public String getEquipCategory() {
        return equipCategory;
    }

    /**
     * @param equipCategory the equipCategory to set
     */
    public void setEquipCategory(String equipCategory) {
        this.equipCategory = equipCategory;
    }

    /**
     * @return the equipType
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * @param equipType the equipType to set
     */
    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    /**
     * @return the locationNumber
     */
    public String getLocationNumber() {
        return locationNumber;
    }

    /**
     * @param locationNumber the locationNumber to set
     */
    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nameLike
     */
    public String getNameLike() {
        return nameLike;
    }

    /**
     * @param nameLike the nameLike to set
     */
    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    /**
     * @return the phySerialNumber
     */
    public String getPhySerialNumber() {
        return phySerialNumber;
    }

    /**
     * @param phySerialNumber the phySerialNumber to set
     */
    public void setPhySerialNumber(String phySerialNumber) {
        this.phySerialNumber = phySerialNumber;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the phyModel
     */
    public String getPhyModel() {
        return phyModel;
    }

    /**
     * @param phyModel the phyModel to set
     */
    public void setPhyModel(String phyModel) {
        this.phyModel = phyModel;
    }

    /**
     * @return the remoteMgtIp1
     */
    public String getRemoteMgtIp1() {
        return remoteMgtIp1;
    }

    /**
     * @param remoteMgtIp1 the remoteMgtIp1 to set
     */
    public void setRemoteMgtIp1(String remoteMgtIp1) {
        this.remoteMgtIp1 = remoteMgtIp1;
    }

    /**
     * @return the remoteMgtIp2
     */
    public String getRemoteMgtIp2() {
        return remoteMgtIp2;
    }

    /**
     * @param remoteMgtIp2 the remoteMgtIp2 to set
     */
    public void setRemoteMgtIp2(String remoteMgtIp2) {
        this.remoteMgtIp2 = remoteMgtIp2;
    }

    /**
     * @return the remoteMgtUser
     */
    public String getRemoteMgtUser() {
        return remoteMgtUser;
    }

    /**
     * @param remoteMgtUser the remoteMgtUser to set
     */
    public void setRemoteMgtUser(String remoteMgtUser) {
        this.remoteMgtUser = remoteMgtUser;
    }

    /**
     * @return the remoteMgtPwd
     */
    public String getRemoteMgtPwd() {
        return remoteMgtPwd;
    }

    /**
     * @param remoteMgtPwd the remoteMgtPwd to set
     */
    public void setRemoteMgtPwd(String remoteMgtPwd) {
        this.remoteMgtPwd = remoteMgtPwd;
    }

    /**
     * @return the relevanceIp
     */
    public String getRelevanceIp() {
        return relevanceIp;
    }

    /**
     * @param relevanceIp the relevanceIp to set
     */
    public void setRelevanceIp(String relevanceIp) {
        this.relevanceIp = relevanceIp;
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the rackName
     */
    public String getRackName() {
        return rackName;
    }

    /**
     * @param rackName the rackName to set
     */
    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    /**
     * @return the equipRackSid
     */
    public String getEquipRackSid() {
        return equipRackSid;
    }

    /**
     * @param equipRackSid the equipRackSid to set
     */
    public void setEquipRackSid(String equipRackSid) {
        this.equipRackSid = equipRackSid;
    }

    /**
     * @return the resNetworkSid
     */
    public String getResNetworkSid() {
        return resNetworkSid;
    }

    /**
     * @param resNetworkSid the resNetworkSid to set
     */
    public void setResNetworkSid(String resNetworkSid) {
        this.resNetworkSid = resNetworkSid;
    }

    /**
     * @return the hostIpLike
     */
    public String getHostIpLike() {
        return hostIpLike;
    }

    /**
     * @param hostIpLike the hostIpLike to set
     */
    public void setHostIpLike(String hostIpLike) {
        this.hostIpLike = hostIpLike;
    }

    /**
     * @return the vmIpLike
     */
    public String getVmIpLike() {
        return vmIpLike;
    }

    /**
     * @param vmIpLike the vmIpLike to set
     */
    public void setVmIpLike(String vmIpLike) {
        this.vmIpLike = vmIpLike;
    }

    /**
     * @return the equipHostOsType
     */
    public String getEquipHostOsType() {
        return equipHostOsType;
    }

    /**
     * @param equipHostOsType the equipHostOsType to set
     */
    public void setEquipHostOsType(String equipHostOsType) {
        this.equipHostOsType = equipHostOsType;
    }

    /**
     * @return the cabinetFrameName
     */
    public String getCabinetFrameName() {
        return cabinetFrameName;
    }

    /**
     * @param cabinetFrameName the cabinetFrameName to set
     */
    public void setCabinetFrameName(String cabinetFrameName) {
        this.cabinetFrameName = cabinetFrameName;
    }

    /**
     * @return the staTotalServer
     */
    public String getStaTotalServer() {
        return staTotalServer;
    }

    /**
     * @param staTotalServer the staTotalServer to set
     */
    public void setStaTotalServer(String staTotalServer) {
        this.staTotalServer = staTotalServer;
    }

    /**
     * @return the parentVeTopologySid
     */
    public String getParentVeTopologySid() {
        return parentVeTopologySid;
    }

    /**
     * @param parentVeTopologySid the parentVeTopologySid to set
     */
    public void setParentVeTopologySid(String parentVeTopologySid) {
        this.parentVeTopologySid = parentVeTopologySid;
    }

    /**
     * @return the maintCompany
     */
    public String getMaintCompany() {
        return maintCompany;
    }

    /**
     * @param maintCompany the maintCompany to set
     */
    public void setMaintCompany(String maintCompany) {
        this.maintCompany = maintCompany;
    }

    /**
     * @return the maintTel
     */
    public String getMaintTel() {
        return maintTel;
    }

    /**
     * @param maintTel the maintTel to set
     */
    public void setMaintTel(String maintTel) {
        this.maintTel = maintTel;
    }

    /**
     * @return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * @return the warrantyPeriod
     */
    public Long getWarrantyPeriod() {
        return warrantyPeriod;
    }

    /**
     * @param warrantyPeriod the warrantyPeriod to set
     */
    public void setWarrantyPeriod(Long warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    /**
     * @return the equipSupplier
     */
    public String getEquipSupplier() {
        return equipSupplier;
    }

    /**
     * @param equipSupplier the equipSupplier to set
     */
    public void setEquipSupplier(String equipSupplier) {
        this.equipSupplier = equipSupplier;
    }

    /**
     * @return the startEndDate
     */
    public Date getStartEndDate() {
        return startEndDate;
    }

    /**
     * @param startEndDate the startEndDate to set
     */
    public void setStartEndDate(Date startEndDate) {
        this.startEndDate = startEndDate;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}