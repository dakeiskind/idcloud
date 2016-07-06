package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Host extends CommonAdapterResult {

    private String cpuUsage;
    private String menUsage;
    private String host;
    private String hostName;
    private String cpuNumber;
    private String cpuCores;
    private String cpuType;
    private String memorySize;
    private String nicNumber;
    private String hostIp;
    private String hostOsType;
    private String status;
    private String uuid;
    private String cpuGhz;
    private String state;

    // hmc host property
    private String availCpu;
    private String availMem;
    private String viosCapable;
    private String serverModel;
    private List<Vm> vms = new ArrayList<Vm>();
    private List<DataStore> dataStorages = new ArrayList<DataStore>();
    private Network netWorks;

    public String getAvailCpu() {
        return availCpu;
    }

    public void setAvailCpu(String availCpu) {
        this.availCpu = availCpu;
    }

    public String getServerModel() {
        return serverModel;
    }

    public void setServerModel(String serverModel) {
        this.serverModel = serverModel;
    }

    @JsonProperty("hostType")
    public void setVmwareServerModel(String serverModel) {
        this.serverModel = serverModel;
    }

    public String getAvailMem() {
        return availMem;
    }

    public void setAvailMem(String availMem) {
        this.availMem = availMem;
    }

    public String getViosCapable() {
        return viosCapable;
    }

    public void setViosCapable(String viosCapable) {
        this.viosCapable = viosCapable;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getMenUsage() {
        return menUsage;
    }

    public void setMenUsage(String menUsage) {
        this.menUsage = menUsage;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCpuNumber() {
        return cpuNumber;
    }

    public void setCpuNumber(String cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    public String getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(String cpuCores) {
        this.cpuCores = cpuCores;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostOsType() {
        return hostOsType;
    }

    public void setHostOsType(String hostOsType) {
        this.hostOsType = hostOsType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Vm> getVms() {
        return vms;
    }

    public void setVms(List<Vm> vms) {
        this.vms = vms;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty("hypervisor_hostname")
    public void setKvmHost(String host) {
        this.host = host;
    }

    @JsonProperty("vcpus_used")
    public void setKvmCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    @JsonProperty("hostname")
    public void setKvmHostName(String hostName) {
        this.hostName = hostName;
    }

    @JsonProperty("cpuCore")
    public void setPowerCpuCores(String cpuCores) {
        this.cpuCores = "1";
    }

    @JsonProperty("totalMem")
    public void setPowerMemorySize(String memorySize) {
        this.memorySize = Long.toString((Long.parseLong(memorySize) * 1024 * 1024));
    }

    @JsonProperty("ip")
    public void setPowerHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    @JsonProperty("memory_mb_used")
    public void setKvmMenUsage(String menUsage) {
        this.menUsage = menUsage;
    }

    /*
        @JsonProperty("hostname")
        public void setKvmHostName(String hostName) {
            this.hostName = hostName;
        }
        */
    @JsonProperty("cpu_number")
    public void setKvmCpuNumber(String cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    @JsonProperty("totalCpu")
    public void setPowerCpuNumber(String cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    @JsonProperty("cpu_cores")
    public void setKvmCpuCores(String cpuCores) {
        this.cpuCores = cpuCores;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    @JsonProperty("memory_mb")
    public void setKvmMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    @JsonProperty("host_ip")
    public void setKvmHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    @JsonProperty("host_type")
    public void setKvmHostOsType(String hostOsType) {
        this.hostOsType = hostOsType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("id")
    public void setKvmUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("serverSn")
    public void setHmcUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCpuGhz() {
        return cpuGhz;
    }

    public void setCpuGhz(String cpuGhz) {
        this.cpuGhz = cpuGhz;
    }

    @JsonProperty("servers")
    public void setKvmVms(List<Vm> vms) {
        this.vms = vms;
    }

    public List<DataStore> getDataStorages() {
        return dataStorages;
    }

    public void setDataStorages(List<DataStore> dataStorages) {
        this.dataStorages = dataStorages;
    }

    @JsonProperty("disks")
    public void setKvmDisks(List<DataStore> dataStorages) {
        this.dataStorages = dataStorages;
    }

    public Network getNetWorks() {
        return netWorks;
    }

    public void setNetWorks(Network netWorks) {
        this.netWorks = netWorks;
    }

}
