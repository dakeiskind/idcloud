package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.ArrayList;
import java.util.List;

public class VmVO {

    private String cpuCores;
    private String memorySize;
    private String osVersion;
    private String vmName;
    private String status;
    private String uuid;
    private String storageCommitted;
    private String useStoreSize;
    private String provisionStorage;
    private String guestMemoryUsageMB;
    private String cpuUsage;
    private String memUsage;
    private String diskUasge;
    private String hostName;
    private List<IpVO> ips = new ArrayList<IpVO>();
    private List<VdVO> resVdList = new ArrayList<VdVO>();

    public String getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(String cpuCores) {
        this.cpuCores = cpuCores;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStorageCommitted() {
        return storageCommitted;
    }

    public void setStorageCommitted(String storageCommitted) {
        this.storageCommitted = storageCommitted;
    }

    public String getUseStoreSize() {
        return useStoreSize;
    }

    public void setUseStoreSize(String useStoreSize) {
        this.useStoreSize = useStoreSize;
    }

    public String getProvisionStorage() {
        return provisionStorage;
    }

    public void setProvisionStorage(String provisionStorage) {
        this.provisionStorage = provisionStorage;
    }

    public String getGuestMemoryUsageMB() {
        return guestMemoryUsageMB;
    }

    public void setGuestMemoryUsageMB(String guestMemoryUsageMB) {
        this.guestMemoryUsageMB = guestMemoryUsageMB;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(String memUsage) {
        this.memUsage = memUsage;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<IpVO> getIps() {
        return ips;
    }

    public void setIps(List<IpVO> ips) {
        this.ips = ips;
    }

    public List<VdVO> getResVdList() {
        return resVdList;
    }

    public void setResVdList(List<VdVO> resVdList) {
        this.resVdList = resVdList;
    }

    public String getDiskUasge() {
        return diskUasge;
    }

    public void setDiskUasge(String diskUasge) {
        this.diskUasge = diskUasge;
    }

}
