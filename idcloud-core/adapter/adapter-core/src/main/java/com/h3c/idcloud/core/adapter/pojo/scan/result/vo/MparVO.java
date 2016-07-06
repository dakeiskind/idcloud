package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class MparVO {
    private String serverSn;

    private String hostName;

    private String mparName;

    private String mparId;

    private String ip;

    private String mparProfile;

    private String lparEnv;

    private Integer minMem;

    private Integer desiredMem;

    private Integer maxMem;

    private Integer sharedProcPoolID;

    private Integer sharedProcPoolName;

    private String procMode;

    private Integer minProcs;

    private Integer desiredProcs;

    private Integer maxProcs;

    private float minProcUnits;

    private float desiredProcUnits;

    private float maxProcUnits;

    private String state;

    private String osVersion;

    private String currProfile;

    private String defaultProfile;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getServerSn() {
        return serverSn;
    }

    public void setServerSn(String serverSn) {
        this.serverSn = serverSn;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getMparName() {
        return mparName;
    }

    public void setMparName(String mparName) {
        this.mparName = mparName;
    }

    public String getMparId() {
        return mparId;
    }

    public void setMparId(String mparId) {
        this.mparId = mparId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMparProfile() {
        return mparProfile;
    }

    public void setMparProfile(String mparProfile) {
        this.mparProfile = mparProfile;
    }

    public String getLparEnv() {
        return lparEnv;
    }

    public void setLparEnv(String lparEnv) {
        this.lparEnv = lparEnv;
    }

    public Integer getMinMem() {
        return minMem;
    }

    public void setMinMem(Integer minMem) {
        this.minMem = minMem;
    }

    public Integer getDesiredMem() {
        return desiredMem;
    }

    public void setDesiredMem(Integer desiredMem) {
        this.desiredMem = desiredMem;
    }

    public Integer getMaxMem() {
        return maxMem;
    }

    public void setMaxMem(Integer maxMem) {
        this.maxMem = maxMem;
    }

    public Integer getSharedProcPoolID() {
        return sharedProcPoolID;
    }

    public void setSharedProcPoolID(Integer sharedProcPoolID) {
        this.sharedProcPoolID = sharedProcPoolID;
    }

    public Integer getSharedProcPoolName() {
        return sharedProcPoolName;
    }

    public void setSharedProcPoolName(Integer sharedProcPoolName) {
        this.sharedProcPoolName = sharedProcPoolName;
    }

    public String getProcMode() {
        return procMode;
    }

    public void setProcMode(String procMode) {
        this.procMode = procMode;
    }

    public Integer getMinProcs() {
        return minProcs;
    }

    public void setMinProcs(Integer minProcs) {
        this.minProcs = minProcs;
    }

    public Integer getDesiredProcs() {
        return desiredProcs;
    }

    public void setDesiredProcs(Integer desiredProcs) {
        this.desiredProcs = desiredProcs;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getCurrProfile() {
        return currProfile;
    }

    public void setCurrProfile(String currProfile) {
        this.currProfile = currProfile;
    }

    public String getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(String defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

}
