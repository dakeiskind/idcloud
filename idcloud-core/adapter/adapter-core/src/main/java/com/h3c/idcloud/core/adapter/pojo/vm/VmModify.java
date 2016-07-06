package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.ArrayList;
import java.util.List;

public class VmModify extends Base {

    private String vmId;
    private String vmName;
    private String osCategory;
    private String osCategoryDetail;
    private String cpu = "";
    private String memory = "";
    private List<VmNic> nics = new ArrayList<VmNic>();
    private List<VmDisk> disks = new ArrayList<VmDisk>();
    private String oldCpu;
    private String oldMemory;

    private String targetSerIp;//目标虚拟机IP
    private String targetUser;//虚拟机用户名
    private String targetPwd;//目标主机密码

    public String getTargetSerIp() {
        return targetSerIp;
    }

    public void setTargetSerIp(String targetSerIp) {
        this.targetSerIp = targetSerIp;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getTargetPwd() {
        return targetPwd;
    }

    public void setTargetPwd(String targetPwd) {
        this.targetPwd = targetPwd;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getOsCategory() {
        return osCategory;
    }

    public void setOsCategory(String osCategory) {
        this.osCategory = osCategory;
    }

    public String getOsCategoryDetail() {
        return osCategoryDetail;
    }

    public void setOsCategoryDetail(String osCategoryDetail) {
        this.osCategoryDetail = osCategoryDetail;
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

    public List<VmNic> getNics() {
        return nics;
    }

    public void setNics(List<VmNic> nics) {
        this.nics = nics;
    }

    public List<VmDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<VmDisk> disks) {
        this.disks = disks;
    }

    public String getOldCpu() {
        return oldCpu;
    }

    public void setOldCpu(String oldCpu) {
        this.oldCpu = oldCpu;
    }

    public String getOldMemory() {
        return oldMemory;
    }

    public void setOldMemory(String oldMemory) {
        this.oldMemory = oldMemory;
    }

}
