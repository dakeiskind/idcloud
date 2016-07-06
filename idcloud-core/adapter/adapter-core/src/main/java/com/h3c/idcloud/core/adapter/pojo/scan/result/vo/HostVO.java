package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.ArrayList;
import java.util.List;

public class HostVO {

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
    //hmc host property
    private String serverModel;

    private String availCpu;

    private String availMem;

    private String viosCapable;

    private List<VmVO> vms;
    private List<DataStoreVO> dataStorages = new ArrayList<DataStoreVO>();
    private NetworkVO netWorks;

    public String getServerModel() {
        return serverModel;
    }

    public void setServerModel(String serverModel) {
        this.serverModel = serverModel;
    }

    public String getViosCapable() {
        return viosCapable;
    }

    public void setViosCapable(String viosCapable) {
        this.viosCapable = viosCapable;
    }

    public String getAvailCpu() {
        return availCpu;
    }

    public void setAvailCpu(String availCpu) {
        this.availCpu = availCpu;
    }

    public String getAvailMem() {
        return availMem;
    }

    public void setAvailMem(String availMem) {
        this.availMem = availMem;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public void setHostname(String hostName) {
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

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
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

    public String getCpuGhz() {
        return cpuGhz;
    }

    public void setCpuGhz(String cpuGhz) {
        this.cpuGhz = cpuGhz;
    }

    public List<VmVO> getVms() {
        return vms;
    }

    public void setVms(List<VmVO> vms) {
        this.vms = vms;
    }

    public List<DataStoreVO> getDataStorages() {
        return dataStorages;
    }

    public void setDataStorages(List<DataStoreVO> dataStorages) {
        this.dataStorages = dataStorages;
    }

    public NetworkVO getNetWorks() {
        return netWorks;
    }

    public void setNetWorks(NetworkVO netWorks) {
        this.netWorks = netWorks;
    }

}
