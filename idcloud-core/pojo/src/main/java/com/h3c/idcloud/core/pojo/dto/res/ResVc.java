package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ClusterVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataStoreVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResVc extends ResTopology implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源拓扑结构ID
     */
    private String resTopologySid;

    /**
     * 集群sid
     */
    private String resClusterSids;

    /**
     *
     */
    private String resVcTopologyType;

    /**
     * 资源拓扑结构名称
     */
    private String resTopologyName;

    /**
     * 所属资源池
     */
    private String ownerPool;

    /**
     * 所属资源分区
     */
    private String resPoolSid;

    /**
     * 资源分区SID
     */
    private String zone;

    /**
     * 查询是否存在可用的
     */
    private String notExistResPoolSid;

    /**
     * 是否打开HA功能（1：打开，0：关闭）
     */
    private String openHa;

    /**
     * 是否打开HA功能名称
     */
    private String openHaName;


    /**
     * 是否打开HA资源预留（1：打开，0：关闭）
     */
    private String haResReserve;

    /**
     * 是否打开HA资源预留
     */
    private String haResReserveName;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * CPU预留(%)
     */
    private Integer cpuReserve;

    /**
     * 内存预留(%)
     */
    private Integer memoryReserve;

    /**
     * 描述
     */
    private String description;

    /**
     * 查询所属Dc
     */
    private String findOwnerDc;

    /**
     * 集群里的所有cpu总核数
     */
    private Integer cpuTotalCount;

    /**
     * 集群里的分配的核数
     */
    private Integer cpuAllotCount;

    /**
     * 集群里的cpu分配率
     */
    private String cpuAllotRate;

    /**
     * 集群里的所有memory总容量
     */
    private Integer memoryTotalVolume;

    /**
     * 集群里的分配的容量
     */
    private Integer memoryAllotVolume;

    /**
     * 集群里的分配率
     */
    private String memoryAllotRate;

    /**
     * 主机个数
     */
    private Integer hostTotalCount;

    /**
     * 虚拟机个数
     */
    private Integer vmTotalCount;

    /**
     * 存储总量
     */
    private Integer storageTotalVolume;

    private Integer storageLocalTotalVolume;

    private Integer storageShareTotalVolume;

    /**
     * 存储分配率
     */
    private String storageAllotVolumeRate;

    private String storageLocalAllotVolumeRate;

    private String storageShareAllotVolumeRate;

    private List<ResHost> hosts = new ArrayList<ResHost>();

    private List<ResStorage> datastores;

    public ResVc() {

    }

    public ResVc(ClusterVO clusterVO) {
        this.clusterName = clusterVO.getResTopologyName();
        this.openHa = clusterVO.getOpenHa();
        this.haResReserve = clusterVO.getOpenHa();
        if (clusterVO.getHosts() != null && clusterVO.getHosts().size() > 0) {
            for (HostVO hostVO : clusterVO.getHosts()) {
                this.hosts.add(new ResHost(hostVO));
            }
        }
        if (clusterVO.getDatastores() != null && clusterVO.getDatastores().size() > 0) {
            for (DataStoreVO dataStoreVO : clusterVO.getDatastores()) {
                this.datastores.add(new ResStorage(dataStoreVO));
            }
        }
    }

    public List<ResHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<ResHost> hosts) {
        this.hosts = hosts;
    }

    public List<ResStorage> getDatastores() {
        return datastores;
    }

    public void setDatastores(List<ResStorage> datastores) {
        this.datastores = datastores;
    }

    /**
     * @return 资源拓扑结构ID
     */
    public String getResTopologySid() {
        return resTopologySid;
    }

    /**
     * @param resTopologySid 资源拓扑结构ID
     */
    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    public String getResPoolSid() {
        return resPoolSid;
    }

    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    /**
     * @return 是否打开HA功能（1：打开，0：关闭）
     */
    public String getOpenHa() {
        return openHa;
    }

    /**
     * @param openHa 是否打开HA功能（1：打开，0：关闭）
     */
    public void setOpenHa(String openHa) {
        this.openHa = openHa;
    }

    /**
     * @return 是否打开HA资源预留（1：打开，0：关闭）
     */
    public String getHaResReserve() {
        return haResReserve;
    }

    /**
     * @param haResReserve 是否打开HA资源预留（1：打开，0：关闭）
     */
    public void setHaResReserve(String haResReserve) {
        this.haResReserve = haResReserve;
    }

    /**
     * @return CPU预留(%)
     */
    public Integer getCpuReserve() {
        return cpuReserve;
    }

    /**
     * @param cpuReserve CPU预留(%)
     */
    public void setCpuReserve(Integer cpuReserve) {
        this.cpuReserve = cpuReserve;
    }

    /**
     * @return 内存预留(%)
     */
    public Integer getMemoryReserve() {
        return memoryReserve;
    }

    /**
     * @param memoryReserve 内存预留(%)
     */
    public void setMemoryReserve(Integer memoryReserve) {
        this.memoryReserve = memoryReserve;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getResTopologyName() {
        return resTopologyName;
    }

    public void setResTopologyName(String resTopologyName) {
        this.resTopologyName = resTopologyName;
    }

    public String getOwnerPool() {
        return ownerPool;
    }

    public void setOwnerPool(String ownerPool) {
        this.ownerPool = ownerPool;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpenHaName() {
        return openHaName;
    }

    public void setOpenHaName(String openHaName) {
        this.openHaName = openHaName;
    }

    public String getHaResReserveName() {
        return haResReserveName;
    }

    public void setHaResReserveName(String haResReserveName) {
        this.haResReserveName = haResReserveName;
    }

    public String getNotExistResPoolSid() {
        return notExistResPoolSid;
    }

    public void setNotExistResPoolSid(String notExistResPoolSid) {
        this.notExistResPoolSid = notExistResPoolSid;
    }

    public String getFindOwnerDc() {
        return findOwnerDc;
    }

    public void setFindOwnerDc(String findOwnerDc) {
        this.findOwnerDc = findOwnerDc;
    }

    public Integer getCpuTotalCount() {
        return cpuTotalCount;
    }

    public void setCpuTotalCount(Integer cpuTotalCount) {
        this.cpuTotalCount = cpuTotalCount;
    }

    public Integer getCpuAllotCount() {
        return cpuAllotCount;
    }

    public void setCpuAllotCount(Integer cpuAllotCount) {
        this.cpuAllotCount = cpuAllotCount;
    }

    public Integer getMemoryTotalVolume() {
        return memoryTotalVolume;
    }

    public void setMemoryTotalVolume(Integer memoryTotalVolume) {
        this.memoryTotalVolume = memoryTotalVolume;
    }

    public Integer getMemoryAllotVolume() {
        return memoryAllotVolume;
    }

    public void setMemoryAllotVolume(Integer memoryAllotVolume) {
        this.memoryAllotVolume = memoryAllotVolume;
    }

    public Integer getHostTotalCount() {
        return hostTotalCount;
    }

    public void setHostTotalCount(Integer hostTotalCount) {
        this.hostTotalCount = hostTotalCount;
    }

    public Integer getVmTotalCount() {
        return vmTotalCount;
    }

    public void setVmTotalCount(Integer vmTotalCount) {
        this.vmTotalCount = vmTotalCount;
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

    public Integer getStorageTotalVolume() {
        return storageTotalVolume;
    }

    public void setStorageTotalVolume(Integer storageTotalVolume) {
        this.storageTotalVolume = storageTotalVolume;
    }

    public String getStorageAllotVolumeRate() {
        return storageAllotVolumeRate;
    }

    public void setStorageAllotVolumeRate(String storageAllotVolumeRate) {
        this.storageAllotVolumeRate = storageAllotVolumeRate;
    }

    public String getResClusterSids() {
        return resClusterSids;
    }

    public void setResClusterSids(String resClusterSids) {
        this.resClusterSids = resClusterSids;
    }

    public Integer getStorageLocalTotalVolume() {
        return storageLocalTotalVolume;
    }

    public void setStorageLocalTotalVolume(Integer storageLocalTotalVolume) {
        this.storageLocalTotalVolume = storageLocalTotalVolume;
    }

    public Integer getStorageShareTotalVolume() {
        return storageShareTotalVolume;
    }

    public void setStorageShareTotalVolume(Integer storageShareTotalVolume) {
        this.storageShareTotalVolume = storageShareTotalVolume;
    }

    public String getStorageLocalAllotVolumeRate() {
        return storageLocalAllotVolumeRate;
    }

    public void setStorageLocalAllotVolumeRate(String storageLocalAllotVolumeRate) {
        this.storageLocalAllotVolumeRate = storageLocalAllotVolumeRate;
    }

    public String getStorageShareAllotVolumeRate() {
        return storageShareAllotVolumeRate;
    }

    public void setStorageShareAllotVolumeRate(String storageShareAllotVolumeRate) {
        this.storageShareAllotVolumeRate = storageShareAllotVolumeRate;
    }

    /**
     * @return the resVcTopologyType
     */
    public String getResVcTopologyType() {
        return resVcTopologyType;
    }

    /**
     * @param resVcTopologyType the resVcTopologyType to set
     */
    public void setResVcTopologyType(String resVcTopologyType) {
        this.resVcTopologyType = resVcTopologyType;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}