package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;

import java.util.List;

public class VmCreate extends Base {

    private String id;
    private String tenantId;
    private String tenantName;
    private List<VmUserAdd> users;
    private String type;
    private String name;
    private String image;
    private String adminName;
    private String adminPass;
    private String cpu;
    private String memory;
    private String hostName;
    private String osCategory;
    private String osCategoryDetail;
    private String keyName;
    private KeyVo keypair;
    private String sysDiskLocation;
    private String sysDiskSize;
    private List<VmDisk> disks;
    private List<VmNic> nics;
    private List<FC> fcs;
    private String vncPort = "";
    private String software;
    private String vmType;//mpar、lpar
    private VmDomain domain;
    //properties for IBM power oo
    private String osName;
    private String spotResource;
    private String bosinstData;

    private String mparProfile;
    private String maxVirtualSlots;
    private String lparEnv;
    private String minMem;
    private String desiredMem;
    private String maxMem;
    private Integer sharedProcPoolID;
    private String procMode;
    private String sharingMode;
    private Integer uncapWeight;
    private Integer minProcs;
    private Integer desiredProcs;
    private Integer maxProcs;
    private float minProcUnits;
    private float desiredProcUnits;
    private float maxProcUnits;

    //for Lpar
    private String rootVGLocal;
    private Integer volumeSize;

    public KeyVo getKeypair() {
        return keypair;
    }

    public void setKeypair(KeyVo keypair) {
        this.keypair = keypair;
    }

    public List<FC> getFcs() {
        return fcs;
    }

    public void setFcs(List<FC> fcs) {
        this.fcs = fcs;
    }

    public Integer getVolumeSize() {
        return volumeSize;
    }

    public void setVolumeSize(Integer volumeSize) {
        this.volumeSize = volumeSize;
    }

    public VmDomain getDomain() {
        return domain;
    }

    public void setDomain(VmDomain domain) {
        this.domain = domain;
    }

    public List<VmUserAdd> getUsers() {
        return users;
    }

    public void setUsers(List<VmUserAdd> users) {
        this.users = users;
    }

    public String getVmType() {
        return vmType;
    }

    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    public String getRootVGLocal() {
        return rootVGLocal;
    }

    public void setRootVGLocal(String rootVGLocal) {
        this.rootVGLocal = rootVGLocal;
    }

    public String getSpotResource() {
        return spotResource;
    }

    public void setSpotResource(String spotResource) {
        this.spotResource = spotResource;
    }

    public String getBosinstData() {
        return bosinstData;
    }

    public void setBosinstData(String bosinstData) {
        this.bosinstData = bosinstData;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getMparProfile() {
        return mparProfile;
    }

    public void setMparProfile(String mparProfile) {
        this.mparProfile = mparProfile;
    }

    public String getMaxVirtualSlots() {
        return maxVirtualSlots;
    }

    public void setMaxVirtualSlots(String maxVirtualSlots) {
        this.maxVirtualSlots = maxVirtualSlots;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getMinMem() {
        return minMem;
    }

    public void setMinMem(String minMem) {
        this.minMem = minMem;
    }

    public String getDesiredMem() {
        return desiredMem;
    }

    public void setDesiredMem(String desiredMem) {
        this.desiredMem = desiredMem;
    }

    public String getMaxMem() {
        return maxMem;
    }

    public void setMaxMem(String maxMem) {
        this.maxMem = maxMem;
    }

    public String getLparEnv() {
        return lparEnv;
    }

    public void setLparEnv(String lparEnv) {
        this.lparEnv = lparEnv;
    }

    public Integer getSharedProcPoolID() {
        return sharedProcPoolID;
    }

    public void setSharedProcPoolID(Integer sharedProcPoolID) {
        this.sharedProcPoolID = sharedProcPoolID;
    }

    public String getProcMode() {
        return procMode;
    }

    public void setProcMode(String procMode) {
        this.procMode = procMode;
    }

    public String getSharingMode() {
        return sharingMode;
    }

    public void setSharingMode(String sharingMode) {
        this.sharingMode = sharingMode;
    }

    public Integer getUncapWeight() {
        return uncapWeight;
    }

    public void setUncapWeight(Integer uncapWeight) {
        this.uncapWeight = uncapWeight;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getOsCategory() {
        return osCategory;
    }

    public void setOsCategory(String osCategory) {
        this.osCategory = osCategory;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getSysDiskLocation() {
        return sysDiskLocation;
    }

    public void setSysDiskLocation(String sysDiskLocation) {
        this.sysDiskLocation = sysDiskLocation;
    }


    public String getSysDiskSize() {
        return sysDiskSize;
    }

    public void setSysDiskSize(String sysDiskSize) {
        this.sysDiskSize = sysDiskSize;
    }

    public List<VmNic> getNics() {
        return nics;
    }

    public void setNics(List<VmNic> nics) {
        this.nics = nics;
    }

    public String getOsCategoryDetail() {
        return osCategoryDetail;
    }

    public void setOsCategoryDetail(String osCategoryDetail) {
        this.osCategoryDetail = osCategoryDetail;
    }

    public String getVncPort() {
        return vncPort == null ? "" : vncPort;
    }

    public void setVncPort(String vncPort) {
        this.vncPort = vncPort;
    }

    public List<VmDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<VmDisk> disks) {
        this.disks = disks;
    }

    @Override
    public String toString() {
        return "VmCreate{" +
                "id='" + id + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", users=" + users +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPass='" + adminPass + '\'' +
                ", cpu='" + cpu + '\'' +
                ", memory='" + memory + '\'' +
                ", hostName='" + hostName + '\'' +
                ", osCategory='" + osCategory + '\'' +
                ", osCategoryDetail='" + osCategoryDetail + '\'' +
                ", keyName='" + keyName + '\'' +
                ", sysDiskLocation='" + sysDiskLocation + '\'' +
                ", sysDiskSize='" + sysDiskSize + '\'' +
                ", disks=" + disks +
                ", nics=" + nics +
                ", fcs=" + fcs +
                ", vncPort='" + vncPort + '\'' +
                ", software='" + software + '\'' +
                ", vmType='" + vmType + '\'' +
                ", domain=" + domain +
                ", osName='" + osName + '\'' +
                ", spotResource='" + spotResource + '\'' +
                ", bosinstData='" + bosinstData + '\'' +
                ", mparProfile='" + mparProfile + '\'' +
                ", maxVirtualSlots='" + maxVirtualSlots + '\'' +
                ", lparEnv='" + lparEnv + '\'' +
                ", minMem='" + minMem + '\'' +
                ", desiredMem='" + desiredMem + '\'' +
                ", maxMem='" + maxMem + '\'' +
                ", sharedProcPoolID=" + sharedProcPoolID +
                ", procMode='" + procMode + '\'' +
                ", sharingMode='" + sharingMode + '\'' +
                ", uncapWeight=" + uncapWeight +
                ", minProcs=" + minProcs +
                ", desiredProcs=" + desiredProcs +
                ", maxProcs=" + maxProcs +
                ", minProcUnits=" + minProcUnits +
                ", desiredProcUnits=" + desiredProcUnits +
                ", maxProcUnits=" + maxProcUnits +
                ", rootVGLocal='" + rootVGLocal + '\'' +
                ", volumeSize=" + volumeSize +
                '}';
    }
}