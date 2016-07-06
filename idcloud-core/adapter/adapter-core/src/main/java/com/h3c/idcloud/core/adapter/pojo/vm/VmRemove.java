package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;

import java.util.List;

public class VmRemove extends Base {
    private String sid;
    private String hostName;
    private String vmName;
    private String id;//vm id
    private boolean isDueToFailOfCreating;

    private String vmType;//表示删除虚拟机类型比如lpar,mpar
    private String ip;
    private List<Volume> volumes;
    //for lpar delete
    private String osName;

    //for mpar delete
    private String sysDiskUuid;
    private String ssp;
    private String cluster;
    private List<Vios> visoList;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public String getSysDiskUuid() {
        return sysDiskUuid;
    }

    public void setSysDiskUuid(String sysDiskUuid) {
        this.sysDiskUuid = sysDiskUuid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSsp() {
        return ssp;
    }

    public void setSsp(String ssp) {
        this.ssp = ssp;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public List<Vios> getVisoList() {
        return visoList;
    }

    public void setVisoList(List<Vios> visoList) {
        this.visoList = visoList;
    }

    public String getVmType() {
        return vmType;
    }

    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDueToFailOfCreating() {
        return isDueToFailOfCreating;
    }

    public void setDueToFailOfCreating(boolean isDueToFailOfCreating) {
        this.isDueToFailOfCreating = isDueToFailOfCreating;
    }

}
