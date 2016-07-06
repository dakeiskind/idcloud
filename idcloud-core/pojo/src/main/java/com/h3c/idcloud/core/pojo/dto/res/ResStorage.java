package com.h3c.idcloud.core.pojo.dto.res;

import com.google.common.base.Strings;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataStoreVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SSPVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ResStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resStorageSid;

    /**
     * 业务sid
     */
    private Long resBizSid;

    /**
     * 业务级别
     */
    private String resBizLevel;

    /**
     * 资源IDs
     */
    private String resStorageSids;

    /**
     * 上级拓扑结构ID
     */
    private String parentTopologySid;

    /**
     * 存储类别sid
     */
    private String resStorageClassSid;

    /**
     * 所属资源池ID
     */
    private String resPoolSid;

    /**
     * 查询是否存在可用的
     */
    private String notExistResPoolSid;

    /**
     * 资源分区SID
     */
    private String zone;

    /**
     * 集群sid集合，用于查询集群下面的存储
     */
    private String clusterSidCollection;

    /**
     * 存储卷名称
     */
    // private String volumeName;
    private String storageName;

    /**
     * 业务名称
     */
    private String mgtObjName;

    /**
     * 存储卷名称查询
     */
    private String storageNameLike;

    /**
     * 存储卷类型（VG, Datastore)
     */
    // private String volumeType;

    /**
     * 存储卷标签
     */
    // private String volumeTag;
    private String storageTag;

    /**
     * 存储逻辑单元号
     */
    private String storageUnitNo;

    /**
     * 可用容量(G)
     */
    private Long availableCapacity;

    /**
     * 已用容量(G)
     */
    private Long hadUsedCapacity;

    /**
     * 可用容量(G)(资源检查时用)
     */
    private long stoAllotSize;
    /**
     * 使用率
     */
    private String storageUsage;

    /**
     * 分配率
     */
    private String storageRate;

    /**
     * 硬盘类型
     */
    private String hardDiskType;

    /**
     * 硬盘类型名称
     */
    private String hardDiskTypeName;

    /**
     * 存储类别
     */
    private String storageCategory;

    /**
     * 存储类别名称
     */
    private String storageCategoryName;

    /**
     * 存储分类
     */
    private String storageClassify;

    /**
     * 存储用途
     */
    private String storagePurpose;

    /**
     * 存储用途名称
     */
    private String storagePurposeName;


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
     * 版本
     */
    private Long version;

    /**
     * 状态
     */
    private String status;

    /**
     * 使用状态
     */
    private String usageStatus;

    /**
     * 使用状态名称
     */
    private String usageStatusName;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 总容量
     */
    private Long totalCapacity;
    private Long localTotalCapacity;
    private Long shareTotalCapacity;

    private String resTopologySid;

    private String resHostSid;

    private String resTopologyName;

    private String resTopologyType;

    private String storageType;

    private String storageTypeName;

    private String storageStatus;
    private String uuid;

    /**
     * LUN编号
     */
    private String lunNo;

    /**
     * 关联主机
     */
    private String ownerHost;

    /**
     * 所属集群
     */
    private String ownerCluster;

    /**
     * 置备容量
     */
    private Long provisionCapacity;

    /**
     * 置备容量
     */
    private Long localProvisionCapacity;

    /**
     * 置备容量
     */
    private Long shareProvisionCapacity;

    /**
     * 分配容量
     */
    private Long allotCapacity;
    private Long localAllotCapacity;
    private Long shareAllotCapacity;

    /**
     * 存储总数量
     */
    private Integer staTotalStorage;

    /**
     * 存储可用数量
     */
    private Integer staUsableStorage;

    /**
     * 存储故障数量
     */
    private Integer staFaultStorage;

    /**
     * 存储不可用数量
     */
    private Integer staUnusableStorage;

    /**
     * 存储分配率
     */
    private String allotRate;

    private String localAllotRate;
    private String shareAllotRate;

    /**
     * 操作类型
     */
    private String action;

    /**
     * power存储集群名称
     */
    private String powerClusterName;

    private List<String> hostNames;

    private ResVfc resVfc;

    /**
     * 所属存储设备Sid
     */
    private String resEquipStorageSid;

    public ResStorage() {

    }

    /**
     * MQ存储对象转换平台存储对象
     *
     * @param data
     */
    public ResStorage(DataStoreVO data) {
        this.uuid = data.getUuid();
        this.storageName = data.getStorageName();
        if ("true".equals(data.getStorageCategory())) {
            this.storageCategory = WebConstants.StorageCategory.SHARE;
        } else {
            this.storageCategory = WebConstants.StorageCategory.LOCAL;
        }
        if (!Strings.isNullOrEmpty(data.getAvailableCapacity())) {
            this.availableCapacity = Long.parseLong(data.getAvailableCapacity());
        }
        if (!Strings.isNullOrEmpty(data.getTotalCapacity())) {
            this.totalCapacity = Long.parseLong(data.getTotalCapacity());
        }
        if (!Strings.isNullOrEmpty(data.getProvisionCapacity())) {
            this.provisionCapacity = Long.parseLong(data.getProvisionCapacity());
        }
        if ("true".equals(data.getStatus())) {
            this.status = WebConstants.ResStorageStatus.NORMAL;
        } else {
            this.status = WebConstants.ResStorageStatus.OUTLINE;
        }
        this.hostNames = Arrays.asList(data.getHostNames());
        this.storagePurpose = WebConstants.StoragePurpose.SYSTEM_DATA_DISK;
    }

    /**
     * Power 存储池
     *
     * @param sspVo
     */
    public ResStorage(SSPVo sspVo) {
        this.storageName = sspVo.getPoolName();
        this.totalCapacity = new BigDecimal(sspVo.getPoolSize()).longValue();
        this.availableCapacity = new BigDecimal(sspVo.getFreeSpace()).longValue();
        this.powerClusterName = sspVo.getClusterName();
        this.uuid = sspVo.getUuid();
        this.storageCategory = WebConstants.StorageCategory.SHARE;
        this.storagePurpose = WebConstants.StoragePurpose.SYSTEM_DISK;
        this.storageStatus = WebConstants.ResStorageStatus.NORMAL;
        this.status = WebConstants.ResStorageStatus.NORMAL;
    }

    /**
     * @return the resEquipStorageSid
     */
    public String getResEquipStorageSid() {
        return resEquipStorageSid;
    }

    /**
     * @param resEquipStorageSid the resEquipStorageSid to set
     */
    public void setResEquipStorageSid(String resEquipStorageSid) {
        this.resEquipStorageSid = resEquipStorageSid;
    }

    public ResVfc getResVfc() {
        return resVfc;
    }

    public void setResVfc(ResVfc resVfc) {
        this.resVfc = resVfc;
    }

    public List<String> getHostNames() {
        return hostNames;
    }

    public void setHostNames(List<String> hostNames) {
        this.hostNames = hostNames;
    }

    public String getResTopologyType() {
        return resTopologyType;
    }

    public void setResTopologyType(String resTopologyType) {
        this.resTopologyType = resTopologyType;
    }

    public String getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(String storageStatus) {
        this.storageStatus = storageStatus;
    }

    public String getResTopologyName() {
        return resTopologyName;
    }

    public void setResTopologyName(String resTopologyName) {
        this.resTopologyName = resTopologyName;
    }

    /**
     * @return 上级拓扑结构ID
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * @return 存储卷名称
     */
    // public String getVolumeName() {
    // return volumeName;
    // }

    /**
     * @param volumeName
     *            存储卷名称
     */
    // public void setVolumeName(String volumeName) {
    // this.volumeName = volumeName;
    // }

    /**
     * @return 存储卷类型（VG, Datastore)
     */
    // public String getVolumeType() {
    // return volumeType;
    // }

    /**
     * @param volumeType
     *            存储卷类型（VG, Datastore)
     */
    // public void setVolumeType(String volumeType) {
    // this.volumeType = volumeType;
    // }

    /**
     * @return 存储卷标签
     */
    // public String getVolumeTag() {
    // return volumeTag;
    // }

    /**
     * @param volumeTag
     *            存储卷标签
     */
    // public void setVolumeTag(String volumeTag) {
    // this.volumeTag = volumeTag;
    // }

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
     * @return 可用容量(G)
     */
    public Long getAvailableCapacity() {
        return availableCapacity;
    }

    /**
     * @param availableCapacity 可用容量(G)
     */
    public void setAvailableCapacity(Long availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    /**
     * @return the storageUnitNo
     */
    public String getStorageUnitNo() {
        return storageUnitNo;
    }

    /**
     * @param storageUnitNo the storageUnitNo to set
     */
    public void setStorageUnitNo(String storageUnitNo) {
        this.storageUnitNo = storageUnitNo;
    }

    /**
     * @return 硬盘类型
     */
    public String getHardDiskType() {
        return hardDiskType;
    }

    /**
     * @param hardDiskType 硬盘类型
     */
    public void setHardDiskType(String hardDiskType) {
        this.hardDiskType = hardDiskType;
    }

    /**
     * @return 存储类别
     */
    public String getStorageCategory() {
        return storageCategory;
    }

    /**
     * @param storageCategory 存储类别
     */
    public void setStorageCategory(String storageCategory) {
        this.storageCategory = storageCategory;
    }

    /**
     * @return 存储用途
     */
    public String getStoragePurpose() {
        return storagePurpose;
    }

    /**
     * @param storagePurpose 存储用途
     */
    public void setStoragePurpose(String storagePurpose) {
        this.storagePurpose = storagePurpose;
    }

    public String getResStorageSid() {
        return resStorageSid;
    }

    public void setResStorageSid(String resStorageSid) {
        this.resStorageSid = resStorageSid;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResTopologySid() {
        return resTopologySid;
    }

    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    public Long getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStorageTag() {
        return storageTag;
    }

    public void setStorageTag(String storageTag) {
        this.storageTag = storageTag;
    }

    public String getHardDiskTypeName() {
        return hardDiskTypeName;
    }

    public void setHardDiskTypeName(String hardDiskTypeName) {
        this.hardDiskTypeName = hardDiskTypeName;
    }

    public String getStorageCategoryName() {
        return storageCategoryName;
    }

    public void setStorageCategoryName(String storageCategoryName) {
        this.storageCategoryName = storageCategoryName;
    }

    public String getStoragePurposeName() {
        return storagePurposeName;
    }

    public void setStoragePurposeName(String storagePurposeName) {
        this.storagePurposeName = storagePurposeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStorageTypeName() {
        return storageTypeName;
    }

    public void setStorageTypeName(String storageTypeName) {
        this.storageTypeName = storageTypeName;
    }

    /**
     * @return the storageUsage
     */
    public String getStorageUsage() {
        return storageUsage;
    }

    /**
     * @param storageUsage the storageUsage to set
     */
    public void setStorageUsage(String storageUsage) {
        this.storageUsage = storageUsage;
    }

    /**
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public String getNotExistResPoolSid() {
        return notExistResPoolSid;
    }

    public void setNotExistResPoolSid(String notExistResPoolSid) {
        this.notExistResPoolSid = notExistResPoolSid;
    }

    public String getClusterSidCollection() {
        return clusterSidCollection;
    }

    public void setClusterSidCollection(String clusterSidCollection) {
        this.clusterSidCollection = clusterSidCollection;
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

    public String getStorageNameLike() {
        return storageNameLike;
    }

    public void setStorageNameLike(String storageNameLike) {
        this.storageNameLike = storageNameLike;
    }

    /**
     * @return the stoAllotSize
     */
    public long getStoAllotSize() {
        return stoAllotSize;
    }

    /**
     * @param stoAllotSize the stoAllotSize to set
     */
    public void setStoAllotSize(long stoAllotSize) {
        this.stoAllotSize = stoAllotSize;
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

    public String getStorageClassify() {
        return storageClassify;
    }

    public void setStorageClassify(String storageClassify) {
        this.storageClassify = storageClassify;
    }

    public String getResStorageSids() {
        return resStorageSids;
    }

    public void setResStorageSids(String resStorageSids) {
        this.resStorageSids = resStorageSids;
    }

    public String getOwnerHost() {
        return ownerHost;
    }

    public void setOwnerHost(String ownerHost) {
        this.ownerHost = ownerHost;
    }

    public String getOwnerCluster() {
        return ownerCluster;
    }

    public void setOwnerCluster(String ownerCluster) {
        this.ownerCluster = ownerCluster;
    }

    public Long getProvisionCapacity() {
        return provisionCapacity;
    }

    public void setProvisionCapacity(Long provisionCapacity) {
        this.provisionCapacity = provisionCapacity;
    }

    public String getResStorageClassSid() {
        return resStorageClassSid;
    }

    public void setResStorageClassSid(String resStorageClassSid) {
        this.resStorageClassSid = resStorageClassSid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStorageRate() {
        return storageRate;
    }

    public void setStorageRate(String storageRate) {
        this.storageRate = storageRate;
    }

    public Integer getStaTotalStorage() {
        return staTotalStorage;
    }

    public void setStaTotalStorage(Integer staTotalStorage) {
        this.staTotalStorage = staTotalStorage;
    }

    public Integer getStaUsableStorage() {
        return staUsableStorage;
    }

    public void setStaUsableStorage(Integer staUsableStorage) {
        this.staUsableStorage = staUsableStorage;
    }

    public Integer getStaFaultStorage() {
        return staFaultStorage;
    }

    public void setStaFaultStorage(Integer staFaultStorage) {
        this.staFaultStorage = staFaultStorage;
    }

    public Integer getStaUnusableStorage() {
        return staUnusableStorage;
    }

    public void setStaUnusableStorage(Integer staUnusableStorage) {
        this.staUnusableStorage = staUnusableStorage;
    }

    public Long getAllotCapacity() {
        return allotCapacity;
    }

    public void setAllotCapacity(Long allotCapacity) {
        this.allotCapacity = allotCapacity;
    }

    public String getAllotRate() {
        return allotRate;
    }

    public void setAllotRate(String allotRate) {
        this.allotRate = allotRate;
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

    public String getResHostSid() {
        return resHostSid;
    }

    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
    }

    public Long getLocalTotalCapacity() {
        return localTotalCapacity;
    }

    public void setLocalTotalCapacity(Long localTotalCapacity) {
        this.localTotalCapacity = localTotalCapacity;
    }

    public Long getShareTotalCapacity() {
        return shareTotalCapacity;
    }

    public void setShareTotalCapacity(Long shareTotalCapacity) {
        this.shareTotalCapacity = shareTotalCapacity;
    }

    public Long getLocalProvisionCapacity() {
        return localProvisionCapacity;
    }

    public void setLocalProvisionCapacity(Long localProvisionCapacity) {
        this.localProvisionCapacity = localProvisionCapacity;
    }

    public Long getShareProvisionCapacity() {
        return shareProvisionCapacity;
    }

    public void setShareProvisionCapacity(Long shareProvisionCapacity) {
        this.shareProvisionCapacity = shareProvisionCapacity;
    }

    public Long getLocalAllotCapacity() {
        return localAllotCapacity;
    }

    public void setLocalAllotCapacity(Long localAllotCapacity) {
        this.localAllotCapacity = localAllotCapacity;
    }

    public Long getShareAllotCapacity() {
        return shareAllotCapacity;
    }

    public void setShareAllotCapacity(Long shareAllotCapacity) {
        this.shareAllotCapacity = shareAllotCapacity;
    }

    public String getLocalAllotRate() {
        return localAllotRate;
    }

    public void setLocalAllotRate(String localAllotRate) {
        this.localAllotRate = localAllotRate;
    }

    public String getShareAllotRate() {
        return shareAllotRate;
    }

    public void setShareAllotRate(String shareAllotRate) {
        this.shareAllotRate = shareAllotRate;
    }

    public String getMgtObjName() {
        return mgtObjName;
    }

    public void setMgtObjName(String mgtObjName) {
        this.mgtObjName = mgtObjName;
    }

    public String getPowerClusterName() {
        return powerClusterName;
    }

    public void setPowerClusterName(String powerClusterName) {
        this.powerClusterName = powerClusterName;
    }

    /**
     * @return the lunNo
     */
    public String getLunNo() {
        return lunNo;
    }

    /**
     * @param lunNo the lunNo to set
     */
    public void setLunNo(String lunNo) {
        this.lunNo = lunNo;
    }

    /**
     * @return the hadUsedCapacity
     */
    public Long getHadUsedCapacity() {
        return hadUsedCapacity;
    }

    /**
     * @param hadUsedCapacity the hadUsedCapacity to set
     */
    public void setHadUsedCapacity(Long hadUsedCapacity) {
        this.hadUsedCapacity = hadUsedCapacity;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}