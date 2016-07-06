package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.MparVO;

import java.io.Serializable;

public class ResVios implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resViosSid;

    /**
     * 资源ID
     */
    private String resHostSid;

    /**
     * 虚拟环境拓扑Sid
     */
    private String resVeSid;

    private String hostName;

    private String hostNameLike;

    private Float powerCpuMaxUnits;

    private Float powerCpuMinUnits;

    private Float powerCpuUnits;

    private Integer powerCpuMaxCores;

    private Integer powerCpuMinCores;

    private Integer powerCpuCores;

    private String user;

    private String password;

    private String ip;

    private String version;
    private String viosLparName;
    private String viosLparNameLike;
    private String viosLparId;
    private String viosProfile;

    private String uuid;

    private Integer memorySize;

    private Integer memoryMin;

    private Integer memoryMax;

    public ResVios() {

    }

    public ResVios(MparVO mparVO) {
        this.uuid = mparVO.getUuid();
        this.ip = mparVO.getIp();
        this.powerCpuCores = mparVO.getDesiredProcs();
        this.powerCpuMaxCores = mparVO.getMaxProcs();
        this.powerCpuMaxUnits = mparVO.getMaxProcUnits();
        this.powerCpuMinCores = mparVO.getMinProcs();
        this.powerCpuUnits = mparVO.getDesiredProcUnits();
        this.version = mparVO.getOsVersion();
        this.viosLparId = mparVO.getMparId();
        this.viosProfile = mparVO.getCurrProfile();
        this.viosLparName = mparVO.getMparName();
        this.memorySize = mparVO.getDesiredMem();
        this.memoryMax = mparVO.getMaxMem();
        this.memoryMin = mparVO.getMinMem();
    }

    public String getResViosSid() {
        return resViosSid;
    }

    public void setResViosSid(String resViosSid) {
        this.resViosSid = resViosSid;
    }

    /**
     * @return 资源ID
     */
    public String getResHostSid() {
        return resHostSid;
    }

    /**
     * @param resHostSid 资源ID
     */
    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
    }

    public Float getPowerCpuMaxUnits() {
        return powerCpuMaxUnits;
    }

    public void setPowerCpuMaxUnits(Float powerCpuMaxUnits) {
        this.powerCpuMaxUnits = powerCpuMaxUnits;
    }

    public Float getPowerCpuMinUnits() {
        return powerCpuMinUnits;
    }

    public void setPowerCpuMinUnits(Float powerCpuMinUnits) {
        this.powerCpuMinUnits = powerCpuMinUnits;
    }

    public Float getPowerCpuUnits() {
        return powerCpuUnits;
    }

    public void setPowerCpuUnits(Float powerCpuUnits) {
        this.powerCpuUnits = powerCpuUnits;
    }

    public Integer getPowerCpuMaxCores() {
        return powerCpuMaxCores;
    }

    public void setPowerCpuMaxCores(Integer powerCpuMaxCores) {
        this.powerCpuMaxCores = powerCpuMaxCores;
    }

    public Integer getPowerCpuMinCores() {
        return powerCpuMinCores;
    }

    public void setPowerCpuMinCores(Integer powerCpuMinCores) {
        this.powerCpuMinCores = powerCpuMinCores;
    }

    public Integer getPowerCpuCores() {
        return powerCpuCores;
    }

    public void setPowerCpuCores(Integer powerCpuCores) {
        this.powerCpuCores = powerCpuCores;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getViosLparName() {
        return viosLparName;
    }

    public void setViosLparName(String viosLparName) {
        this.viosLparName = viosLparName;
    }

    public String getViosLparId() {
        return viosLparId;
    }

    public void setViosLparId(String viosLparId) {
        this.viosLparId = viosLparId;
    }

    public String getViosProfile() {
        return viosProfile;
    }

    public void setViosProfile(String viosProfile) {
        this.viosProfile = viosProfile;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the resVeSid
     */
    public String getResVeSid() {
        return resVeSid;
    }

    /**
     * @param resVeSid the resVeSid to set
     */
    public void setResVeSid(String resVeSid) {
        this.resVeSid = resVeSid;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the memorySize
     */
    public Integer getMemorySize() {
        return memorySize;
    }

    /**
     * @param memorySize the memorySize to set
     */
    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    /**
     * @return the memoryMin
     */
    public Integer getMemoryMin() {
        return memoryMin;
    }

    /**
     * @param memoryMin the memoryMin to set
     */
    public void setMemoryMin(Integer memoryMin) {
        this.memoryMin = memoryMin;
    }

    /**
     * @return the memoryMax
     */
    public Integer getMemoryMax() {
        return memoryMax;
    }

    /**
     * @param memoryMax the memoryMax to set
     */
    public void setMemoryMax(Integer memoryMax) {
        this.memoryMax = memoryMax;
    }

    /**
     * @return the hostNameLike
     */
    public String getHostNameLike() {
        return hostNameLike;
    }

    /**
     * @param hostNameLike the hostNameLike to set
     */
    public void setHostNameLike(String hostNameLike) {
        this.hostNameLike = hostNameLike;
    }

    /**
     * @return the viosLparNameLike
     */
    public String getViosLparNameLike() {
        return viosLparNameLike;
    }

    /**
     * @param viosLparNameLike the viosLparNameLike to set
     */
    public void setViosLparNameLike(String viosLparNameLike) {
        this.viosLparNameLike = viosLparNameLike;
    }


}