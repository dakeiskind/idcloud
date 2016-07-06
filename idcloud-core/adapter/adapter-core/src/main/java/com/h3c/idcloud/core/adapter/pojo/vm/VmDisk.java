package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;

import java.util.List;

public class VmDisk extends Base {

    private String id = "";
    private String name = "";
    private String label = "";
    private String description = "";
    private String location = "";
    private String size;
    private String oldSize = "";
    private String vmName = "";
    private String operate; // add&delete&expand
    private String uuid;
    private String path = "";

    private List<Volume> volumes;
    //for fdisk
    private String deviceTagert;//璁惧鏍囩ず  骞冲彴涓嶇敤浼�
    private String mountLocal;//鎸傝浇鐐�
    private String fileSystem;//鏂囦欢绯荤粺
    private String lvmParam;//lvm鐨勫弬鏁�
    //for power create disk oo
    private String mparSlotNumber;
    private String clusterName;
    private String sspName;
    private String fc;//锟斤拷锟斤拷锟斤拷丝锟�
    private String type;//系统锟斤拷为"sysDisk",锟斤拷锟斤拷锟轿�dataDisk"
    private Vios vios;

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

    public Vios getVios() {
        return vios;
    }

    public void setVios(Vios vios) {
        this.vios = vios;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getMparSlotNumber() {
        return mparSlotNumber;
    }

    public void setMparSlotNumber(String mparSlotNumber) {
        this.mparSlotNumber = mparSlotNumber;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getSspName() {
        return sspName;
    }

    public void setSspName(String sspName) {
        this.sspName = sspName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSize() {
        return this.size == null ? "" : this.size;
        // return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOldSize() {
        return oldSize;
    }

    public void setOldSize(String oldSize) {
        this.oldSize = oldSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
