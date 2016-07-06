package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.ServerSecurityGroup;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Vm extends CommonAdapterResult {
    private String cpuCores;
    private String memorySize;
    private String osVersion;
    private String osType;
    private String vmName;
    private String status;
    private String uuid;
    private String storageCommitted;
    private String useStoreSize;
    private String provisionStorage;
    private String guestMemoryUsageMB;
    private String cpuUsage;
    private String memUsage;
    private String host;
    private String hostName;
    private String hostId;
    private String disk;
    private List<ServerSecurityGroup> securityGroups;
    private List<Ip> ips = new ArrayList<Ip>();
    private List<Vd> resVdList = new ArrayList<Vd>();

    public List<ServerSecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(List<ServerSecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }

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

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHost() {
        return host;
    }

    @JsonProperty("hypervisor_hostname")
    public void setHost(String host) {
        this.host = host;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    @JsonProperty("os_type")
    public void setKvmOsType(String osType) {
        this.osType = osType;
    }

    @JsonProperty("host_id")
    public void setKvmHostId(String hostId) {
        this.hostId = hostId;
    }

    @JsonProperty("cpu_cores")
    public void setKvmCpuCores(String cpuCores) {
        this.cpuCores = cpuCores;
    }

    @JsonProperty("memory")
    public void setKvmMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    @JsonProperty("os_version")
    public void setKvmOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @JsonProperty("server_name")
    public void setKvmVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("server_id")
    public void setKvmUuid(String uuid) {
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

    @JsonProperty("hostname")
    public void setKvmHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<Ip> getIps() {
        return ips;
    }

    public void setIps(List<Ip> ips) {
        this.ips = ips;
    }

    @JsonProperty("networks")
    public void setKvmIps(List<Ip> ips) {
        this.ips = ips;
    }

    public List<Vd> getResVdList() {
        return resVdList;
    }

    public void setResVdList(List<Vd> resVdList) {
        this.resVdList = resVdList;
    }

    @JsonProperty("volumes")
    public void setKvmVdList(List<Vd> resVdList) {
        this.resVdList = resVdList;
    }

}
