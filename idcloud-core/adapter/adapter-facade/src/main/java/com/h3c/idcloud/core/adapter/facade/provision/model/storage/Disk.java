package com.h3c.idcloud.core.adapter.facade.provision.model.storage;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Disk extends CommonAdapterResult {

    private String name;
    private String uuid;
    private String description;
    private String status;
    private String size;
    private String lable;
    private String location;
    private String vmName;
    private String operate;
    private String id;
    private String path;
    private String scsiUnit;
    private String type;
    private List<Volume> volumes;
    //for fdisk
    private String deviceTagert;//璁惧鏍囩ず  骞冲彴涓嶇敤浼�
    private String mountLocal;//鎸傝浇鐐�
    private String fileSystem;//鏂囦欢绯荤粺
    private String lvmParam;//lvm鐨勫弬鏁�
    //hmc 返回
    private String fc;
    private String wwpn;
    private String hostItemIndex;
    private String hostItemAddr;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public String getDeviceTagert() {
        return deviceTagert;
    }

    public void setDeviceTagert(String deviceTagert) {
        this.deviceTagert = deviceTagert;
    }

    public String getMountLocal() {
        return mountLocal;
    }

    public void setMountLocal(String mountLocal) {
        this.mountLocal = mountLocal;
    }

    public String getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(String fileSystem) {
        this.fileSystem = fileSystem;
    }

    public String getLvmParam() {
        return lvmParam;
    }

    public void setLvmParam(String lvmParam) {
        this.lvmParam = lvmParam;
    }

    public String getScsiUnit() {
        return scsiUnit;
    }

    public void setScsiUnit(String scsiUnit) {
        this.scsiUnit = scsiUnit;
    }

    public String getHostItemIndex() {
        return hostItemIndex;
    }

    public void setHostItemIndex(String hostItemIndex) {
        this.hostItemIndex = hostItemIndex;
    }

    public String getHostItemAddr() {
        return hostItemAddr;
    }

    public void setHostItemAddr(String hostItemAddr) {
        this.hostItemAddr = hostItemAddr;
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getWwpn() {
        return wwpn;
    }

    public void setWwpn(String wwpn) {
        this.wwpn = wwpn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("volume_id")
    public void setKvmUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
