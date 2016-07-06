package com.h3c.idcloud.core.pojo.dto.res;


import com.google.common.base.Strings;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IpVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.MparVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VdVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VmVO;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ResVm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源实例ID
     */
    private String resVmSid;

    /**
     * 拓扑资源结构SID
     */
    private String resTopologySid;

    /**
     * 拓扑资源结构类型
     */
    private String resTopologyType;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 查询虚拟机名称
     */
    private String vmNameLike;

    /**
     * CPU核数
     */
    private Integer cpuCores;

    /**
     * CPU主频
     */
    private Double vmCpuGhz;

    /**
     * 内存大小
     */
    private Long memorySize;

    /**
     * 内存最小使用量
     */
    private Long memoryMin;

    /**
     * 内存最大使用量
     */
    private Long memoryMax;

    /**
     * 内存大小---GB
     */
    private String memorySizeGb;

    /**
     * CPU使用主频
     */
    private Double useCpuGhz;

    /**
     * CPU使用率
     */
    private String cpuUseRate;

    /**
     * 内存使用大小
     */
    private Double useMemorySize;

    /**
     * 内存使用率
     */
    private String memoryUseRate;

    /**
     * 操作系统类型
     */
    private String osVersion;

    /**
     * 操作系统类型名称
     */
    private String osVersionName;

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 分配主机ID
     */
    private String allocateResHostSid;

    /**
     * 分配主机ID
     */
    private String allocateResStorageSid;

    /**
     * 所属主机
     */
    private String ownerHost;

    /**
     * 主机管理帐号
     */
    private String managementAccount;

    /**
     * 主机管理密码
     */
    private String managementPassword;

    /**
     * 秘钥
     */
    private String secretKey;

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
     * 虚拟机状态
     */
    private String status;

    /**
     * 虚拟机状态名称
     */
    private String statusName;

    /**
     * 虚拟机IP
     */
    private String vmIp;

    /**
     * 虚拟机IP
     */
    private String extIp;

    /**
     * 虚拟机IP模糊查询
     */
    private String vmIpLike;

    /***
     * 虚拟机ip列表
     */
    private List<Map<String, String>> ips;
    /**
     * cpu使用率
     */
    private long cpuUsage;

    /**
     * 磁盘总大小（资源检查时用）
     */
    private long allVdSize;

    /**
     * 统计虚拟机-- 总数
     */
    private int staTotalVm;

    /**
     * 统计虚拟机-- 正常数
     */
    private int staNormalVm;


    /**
     * 统计虚拟机-- 关机数
     */
    private int staDownVm;

    /**
     * 统计虚拟机-- 维护数
     */
    private int staPauseVm;

    /**
     * 统计虚拟机-- 其他数
     */
    private int staOthersVm;

    /**
     * 虚拟机所在的存储
     */
    private List<ResStorage> resStoList;

    /**
     * 内存使用率
     */
    private double memUsage;

    /**
     * 磁盘使用率
     */
    private double diskUasge;

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

    /** 所有者id */
    private String ownerId;

    /**
     * 虚拟机下的磁盘
     */
    private List<ResVd> resVdList = new ArrayList<ResVd>();

    /**
     * 虚拟机下的网络
     */
    private List<ResIp> resIpList;

    /**
     * 虚拟机下的网络
     */
    private List<ResVmNetwork> resVmNetList = new ArrayList<ResVmNetwork>();

    /**
     * 虚拟机的所有软件
     */
    private List<ResOsSoftware> softwares = new ArrayList<ResOsSoftware>();

    /**
     * 版本号
     */
    private Long version;

    /**
     * 置备存储
     */
    private Long provisionStorage;

    private Long useStoreSize;

    private String hostName;

    /**
     * Power-物理CPU最大使用量
     */
    private float powerCpuMaxUnits;

    /**
     * Power-物理CPU最小使用量
     */
    private float powerCpuMinUnits;

    /**
     * Power-物理CPU使用量
     */
    private float powerCpuUsedUnits;

    /**
     * 分配VIOS资源ID
     */
    private String resViosId;

    /**
     * 分配VIOS资源ID
     */
    private String parId;

    private String resCpuPoolSid;

    private Long sysDiskSize;

    private String osType;

    private String virtualPlatformType;

    private ResHost resHost;

    private List<ResOsUser> resOsUsers;
    /**
     * 分配VIOS资源ID
     */
    private String parProfile;
    private Integer parType;
    private Integer cpuCoresMax;
    private Integer cpuCoresMin;
    private Long mgtObjSid;
    private String mgtObjName;
    /**
     * 分配存储容量
     */
    private Integer allotStorageVolume;

    public ResVm() {

    }

    /**
     * MQ虚拟机对象转换平台虚拟机对象
     *
     * @param vmVo
     */
    public ResVm(VmVO vmVo) {
        if (!Strings.isNullOrEmpty(vmVo.getCpuCores())) {
            this.cpuCores = Integer.parseInt(vmVo.getCpuCores());
        }
        if (!Strings.isNullOrEmpty(vmVo.getMemorySize())) {
            this.memorySize = Long.parseLong(vmVo.getMemorySize());
        }
        this.osVersion = vmVo.getOsVersion();
        this.vmName = vmVo.getVmName();
        this.status = vmVo.getStatus();
        // 判断虚拟机状态
        if ("poweredOn".equals(vmVo.getStatus()) || "active".equals(vmVo.getStatus().toLowerCase())) {
            this.status = WebConstants.ResVmStatus.NORMAL;
        } else if ("poweredOff".equals(vmVo.getStatus()) || "active".equals(vmVo.getStatus().toLowerCase())) {
            this.status = WebConstants.ResVmStatus.POWEREDOFF;
        } else if ("suspended".equals(vmVo.getStatus().toLowerCase())) {
            this.status = WebConstants.ResVmStatus.SUSPENDED;
        } else if ("paused".equals(vmVo.getStatus().toLowerCase())) {
            this.status = WebConstants.ResVmStatus.PAUSED;
        } else {
            this.status = WebConstants.ResVmStatus.FAILURE;
        }
        this.uuid = vmVo.getUuid();
        if (!Strings.isNullOrEmpty(vmVo.getUseStoreSize())) {
            this.useStoreSize = Long.parseLong(vmVo.getUseStoreSize());
        }
        if (!Strings.isNullOrEmpty(vmVo.getProvisionStorage())) {
            this.provisionStorage = Long.parseLong(vmVo.getProvisionStorage());
        }
        if (!Strings.isNullOrEmpty(vmVo.getCpuUsage())) {
            this.cpuUsage = Long.parseLong(vmVo.getCpuUsage());
        }
        if (!Strings.isNullOrEmpty(vmVo.getCpuUsage())) {
            this.useCpuGhz = Double.parseDouble(vmVo.getCpuUsage());
        }
        if (!Strings.isNullOrEmpty(vmVo.getMemUsage())) {
            this.useMemorySize = Double.parseDouble(vmVo.getMemUsage());
        }
        if (!Strings.isNullOrEmpty(vmVo.getDiskUasge())) {
            this.diskUasge = Double.parseDouble(vmVo.getDiskUasge());
        }
        this.hostName = vmVo.getHostName();
        if (vmVo.getIps() != null && vmVo.getIps().size() > 0) {
            for (IpVO ipvo : vmVo.getIps()) {
                this.resVmNetList.add(new ResVmNetwork(ipvo));
            }
        }
        if (vmVo.getResVdList() != null && vmVo.getResVdList().size() > 0) {
            for (VdVO vdVo : vmVo.getResVdList()) {
                this.resVdList.add(new ResVd(vdVo));
            }
        }
    }

    public ResVm(MparVO mparVO) {
        this.cpuCores = mparVO.getDesiredProcs();
        if (mparVO.getDesiredMem() != null && !Strings.isNullOrEmpty(mparVO.getDesiredMem().toString())) {
            this.memorySize = Long.parseLong(mparVO.getDesiredMem().toString());
        }
        this.osVersion = mparVO.getOsVersion();
        this.vmName = mparVO.getMparName();
        // 判断虚拟机状态
        if ("Running".equals(mparVO.getState())) {
            this.status = WebConstants.ResVmStatus.NORMAL;
        } else if ("Not Activated".equals(mparVO.getState()) || "Shutting Down".equals(mparVO.getState())) {
            this.status = WebConstants.ResVmStatus.POWEREDOFF;
        } else if ("Error".equals(mparVO.getState())) {
            this.status = WebConstants.ResVmStatus.FAILURE;
        } else if ("Starting".equals(mparVO.getState())) {
            this.status = WebConstants.ResVmStatus.BOOTING;
        } else if ("Open Firmware".equals(mparVO.getState())) {
            this.status = WebConstants.ResVmStatus.FIRMWARE;
        } else if ("Not Available".equals(mparVO.getState())) {
            this.status = WebConstants.ResVmStatus.UNAVAILABLE;
        }
        this.uuid = mparVO.getUuid();
        this.hostName = mparVO.getHostName();
        // 物理CPU
        this.powerCpuMaxUnits = mparVO.getMaxProcUnits();
        this.powerCpuMinUnits = mparVO.getMinProcUnits();
        this.powerCpuUsedUnits = mparVO.getDesiredProcUnits();
        // 虚拟CPU
        this.cpuCores = mparVO.getDesiredProcs();
        this.cpuCoresMax = mparVO.getMaxProcs();
        this.cpuCoresMin = mparVO.getMinProcs();
        // 内存
        if (mparVO.getMinMem() != null) {
            this.memoryMin = mparVO.getMinMem().longValue();
        }
        if (mparVO.getMaxMem() != null) {
            this.memoryMax = mparVO.getMaxMem().longValue();
        }

        this.parProfile = mparVO.getDefaultProfile();
        this.parId = mparVO.getMparId();

        // 根据PROC_MODE区分VIOC的类型L-PAR（ded）和Micro-PAR（shared）
        // 0---L-PAR;   1---Mirco-PAR
        if ("shared".equalsIgnoreCase(mparVO.getProcMode())) {
            this.parType = 1;
        } else {
            this.parType = 0;
        }

        // Ip网络设置
        if (mparVO.getIp() != null && !"".equals(mparVO.getIp()) && !"null".equals(mparVO.getIp())) {
            ResVmNetwork resVmNetwork = new ResVmNetwork();
            resVmNetwork.setIpAddress(mparVO.getIp());
            this.resVmNetList.add(resVmNetwork);
        }
    }

    public List<ResOsUser> getResOsUsers() {
        return resOsUsers;
    }

    public void setResOsUsers(List<ResOsUser> resOsUsers) {
        this.resOsUsers = resOsUsers;
    }

    public ResHost getResHost() {
        return resHost;
    }

    public void setResHost(ResHost resHost) {
        this.resHost = resHost;
    }

    public String getVirtualPlatformType() {
        return virtualPlatformType;
    }

    public void setVirtualPlatformType(String virtualPlatformType) {
        this.virtualPlatformType = virtualPlatformType;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public Long getSysDiskSize() {
        return sysDiskSize;
    }

    public void setSysDiskSize(Long sysDiskSize) {
        this.sysDiskSize = sysDiskSize;
    }

    /**
     * @return the resCpuPoolSid
     */
    public String getResCpuPoolSid() {
        return resCpuPoolSid;
    }

    /**
     * @param resCpuPoolSid the resCpuPoolSid to set
     */
    public void setResCpuPoolSid(String resCpuPoolSid) {
        this.resCpuPoolSid = resCpuPoolSid;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getMgtObjName() {
        return mgtObjName;
    }

    public void setMgtObjName(String mgtObjName) {
        this.mgtObjName = mgtObjName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return 分配主机ID
     */
    public String getAllocateResHostSid() {
        return allocateResHostSid;
    }

    /**
     * @param allocateResHostSid 分配主机ID
     */
    public void setAllocateResHostSid(String allocateResHostSid) {
        this.allocateResHostSid = allocateResHostSid;
    }

    public String getAllocateResStorageSid() {
        return allocateResStorageSid;
    }

    public void setAllocateResStorageSid(String allocateResStorageSid) {
        this.allocateResStorageSid = allocateResStorageSid;
    }

    /**
     * @return the allVdSize
     */
    public long getAllVdSize() {
        return allVdSize;
    }

    /**
     * @param allVdSize the allVdSize to set
     */
    public void setAllVdSize(long allVdSize) {
        this.allVdSize = allVdSize;
    }

    /**
     * @return CPU核数
     */
    public Integer getCpuCores() {
        return cpuCores;
    }

    /**
     * @param cpuCores CPU核数
     */
    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    public long getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(long cpuUsage) {
        this.cpuUsage = cpuUsage;
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

    public double getDiskUasge() {
        return diskUasge;
    }

    public void setDiskUasge(double diskUasge) {
        this.diskUasge = diskUasge;
    }

    /**
     * @return 主机管理帐号
     */
    public String getManagementAccount() {
        return managementAccount;
    }

    /**
     * @param managementAccount 主机管理帐号
     */
    public void setManagementAccount(String managementAccount) {
        this.managementAccount = managementAccount;
    }

    /**
     * @return 主机管理密码
     */
    public String getManagementPassword() {
        return managementPassword;
    }

    /**
     * @param managementPassword 主机管理密码
     */
    public void setManagementPassword(String managementPassword) {
        this.managementPassword = managementPassword;
    }

    /**
     * @return 内存大小
     */
    public Long getMemorySize() {
        return memorySize;
    }

    /**
     * @param memorySize 内存大小
     */
    public void setMemorySize(Long memorySize) {
        this.memorySize = memorySize;
    }

    public double getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(double memUsage) {
        this.memUsage = memUsage;
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

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsVersionName() {
        return osVersionName;
    }

    public void setOsVersionName(String osVersionName) {
        this.osVersionName = osVersionName;
    }

    public String getOwnerHost() {
        return ownerHost;
    }

    public void setOwnerHost(String ownerHost) {
        this.ownerHost = ownerHost;
    }

    public Long getProvisionStorage() {
        return provisionStorage;
    }

    public void setProvisionStorage(Long provisionStorage) {
        this.provisionStorage = provisionStorage;
    }

    /**
     * @return the resIpList
     */
    public List<ResIp> getResIpList() {
        return resIpList;
    }

    /**
     * @param resIpList the resIpList to set
     */
    public void setResIpList(List<ResIp> resIpList) {
        this.resIpList = resIpList;
    }

    /**
     * @return the resStoList
     */
    public List<ResStorage> getResStoList() {
        return resStoList;
    }

    /**
     * @param resStoList the resStoList to set
     */
    public void setResStoList(List<ResStorage> resStoList) {
        this.resStoList = resStoList;
    }

    public String getResTopologySid() {
        return resTopologySid;
    }

    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    public String getResTopologyType() {
        return resTopologyType;
    }

    public void setResTopologyType(String resTopologyType) {
        this.resTopologyType = resTopologyType;
    }

    /**
     * @return the resVdList
     */
    public List<ResVd> getResVdList() {
        return resVdList;
    }

    /**
     * @param resVdList the resVdList to set
     */
    public void setResVdList(List<ResVd> resVdList) {
        this.resVdList = resVdList;
    }

    /**
     * @return 资源实例ID
     */
    public String getResVmSid() {
        return resVmSid;
    }

    /**
     * @param resVmSid 资源实例ID
     */
    public void setResVmSid(String resVmSid) {
        this.resVmSid = resVmSid;
    }

    /**
     * @return 秘钥
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * @param secretKey 秘钥
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getStaDownVm() {
        return staDownVm;
    }

    public void setStaDownVm(int staDownVm) {
        this.staDownVm = staDownVm;
    }

    public int getStaNormalVm() {
        return staNormalVm;
    }

    public void setStaNormalVm(int staNormalVm) {
        this.staNormalVm = staNormalVm;
    }

    public int getStaOthersVm() {
        return staOthersVm;
    }

    public void setStaOthersVm(int staOthersVm) {
        this.staOthersVm = staOthersVm;
    }

    public int getStaPauseVm() {
        return staPauseVm;
    }

    public void setStaPauseVm(int staPauseVm) {
        this.staPauseVm = staPauseVm;
    }

    public int getStaTotalVm() {
        return staTotalVm;
    }

    public void setStaTotalVm(int staTotalVm) {
        this.staTotalVm = staTotalVm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public Long getUseStoreSize() {
        return useStoreSize;
    }

    public void setUseStoreSize(Long useStoreSize) {
        this.useStoreSize = useStoreSize;
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

    public String getVmIp() {
        return vmIp;
    }

    public void setVmIp(String vmIp) {
        this.vmIp = vmIp;
    }

    public String getVmIpLike() {
        return vmIpLike;
    }

    public void setVmIpLike(String vmIpLike) {
        this.vmIpLike = vmIpLike;
    }

    /**
     * @return 虚拟机名称
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * @param vmName 虚拟机名称
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmNameLike() {
        return vmNameLike;
    }

    public void setVmNameLike(String vmNameLike) {
        this.vmNameLike = vmNameLike;
    }

    public List<Map<String, String>> getIps() {
        return ips;
    }

    public void setIps(List<Map<String, String>> ips) {
        this.ips = ips;
    }

    public String getMemorySizeGb() {
        return memorySizeGb;
    }

    public void setMemorySizeGb(String memorySizeGb) {
        this.memorySizeGb = memorySizeGb;
    }

    public Double getVmCpuGhz() {
        return vmCpuGhz;
    }

    public void setVmCpuGhz(Double vmCpuGhz) {
        this.vmCpuGhz = vmCpuGhz;
    }

    public String getCpuUseRate() {
        return cpuUseRate;
    }

    public void setCpuUseRate(String cpuUseRate) {
        this.cpuUseRate = cpuUseRate;
    }

    public String getMemoryUseRate() {
        return memoryUseRate;
    }

    public void setMemoryUseRate(String memoryUseRate) {
        this.memoryUseRate = memoryUseRate;
    }

    public Integer getAllotStorageVolume() {
        return allotStorageVolume;
    }

    public void setAllotStorageVolume(Integer allotStorageVolume) {
        this.allotStorageVolume = allotStorageVolume;
    }

    public List<ResVmNetwork> getResVmNetList() {
        return resVmNetList;
    }

    public void setResVmNetList(List<ResVmNetwork> resVmNetList) {
        this.resVmNetList = resVmNetList;
    }

    public float getPowerCpuMaxUnits() {
        return powerCpuMaxUnits;
    }

    public void setPowerCpuMaxUnits(float powerCpuMaxUnits) {
        this.powerCpuMaxUnits = powerCpuMaxUnits;
    }

    public float getPowerCpuMinUnits() {
        return powerCpuMinUnits;
    }

    public void setPowerCpuMinUnits(float powerCpuMinUnits) {
        this.powerCpuMinUnits = powerCpuMinUnits;
    }

    public float getPowerCpuUsedUnits() {
        return powerCpuUsedUnits;
    }

    public void setPowerCpuUsedUnits(float powerCpuUsedUnits) {
        this.powerCpuUsedUnits = powerCpuUsedUnits;
    }

    public String getResViosId() {
        return resViosId;
    }

    public void setResViosId(String resViosId) {
        this.resViosId = resViosId;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParProfile() {
        return parProfile;
    }

    public void setParProfile(String parProfile) {
        this.parProfile = parProfile;
    }

    /**
     * @return the osName
     */
    public String getOsName() {
        return osName;
    }

    /**
     * @param osName the osName to set
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    public Integer getParType() {
        return parType;
    }

    public void setParType(Integer parType) {
        this.parType = parType;
    }

    public Integer getCpuCoresMax() {
        return cpuCoresMax;
    }

    public void setCpuCoresMax(Integer cpuCoresMax) {
        this.cpuCoresMax = cpuCoresMax;
    }

    public Integer getCpuCoresMin() {
        return cpuCoresMin;
    }

    public void setCpuCoresMin(Integer cpuCoresMin) {
        this.cpuCoresMin = cpuCoresMin;
    }

    public List<ResOsSoftware> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<ResOsSoftware> softwares) {
        this.softwares = softwares;
    }

    /**
     * @return the memoryMin
     */
    public Long getMemoryMin() {
        return memoryMin;
    }

    /**
     * @param memoryMin the memoryMin to set
     */
    public void setMemoryMin(Long memoryMin) {
        this.memoryMin = memoryMin;
    }

    /**
     * @return the memoryMax
     */
    public Long getMemoryMax() {
        return memoryMax;
    }

    /**
     * @param memoryMax the memoryMax to set
     */
    public void setMemoryMax(Long memoryMax) {
        this.memoryMax = memoryMax;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getExtIp() {
        return extIp;
    }

    public void setExtIp(String extIp) {
        this.extIp = extIp;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}