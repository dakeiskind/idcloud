package com.h3c.idcloud.core.adapter.pojo.vm;


import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.List;

public class VmReconfig extends Base {

    private String vmName;
    private String cpu;
    private String memory;
    private String hostName;

    /******
     * for power
     *******/
    private Integer minMem;
    private Integer maxMem;

    private Integer minProcs;
    private Integer maxProcs;

    private float minProcUnits;
    private float desiredProcUnits;
    private float maxProcUnits;
    private String parProfile;
    private String parId;
    private String parType;

    //for power add dataDisk
    private String hostIp;

    private String userName;

    private String password;

    private List<VmDisk> disks;

    /******
     * for power
     *******/

    public Integer getMinMem() {
        return minMem;
    }

    public void setMinMem(Integer minMem) {
        this.minMem = minMem;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<VmDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<VmDisk> disks) {
        this.disks = disks;
    }

    public Integer getMaxMem() {
        return maxMem;
    }

    public void setMaxMem(Integer maxMem) {
        this.maxMem = maxMem;
    }

    public Integer getMinProcs() {
        return minProcs;
    }

    public void setMinProcs(Integer minProcs) {
        this.minProcs = minProcs;
    }

    public Integer getMaxProcs() {
        return maxProcs;
    }

    public void setMaxProcs(Integer maxProcs) {
        this.maxProcs = maxProcs;
    }

    public float getMinProcUnits() {
        return minProcUnits;
    }

    public void setMinProcUnits(float minProcUnits) {
        this.minProcUnits = minProcUnits;
    }

    public float getDesiredProcUnits() {
        return desiredProcUnits;
    }

    public void setDesiredProcUnits(float desiredProcUnits) {
        this.desiredProcUnits = desiredProcUnits;
    }

    public float getMaxProcUnits() {
        return maxProcUnits;
    }

    public void setMaxProcUnits(float maxProcUnits) {
        this.maxProcUnits = maxProcUnits;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getParProfile() {
        return parProfile;
    }

    public void setParProfile(String parProfile) {
        this.parProfile = parProfile;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParType() {
        return parType;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

}
