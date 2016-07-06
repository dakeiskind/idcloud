package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;

import java.util.ArrayList;
import java.util.List;

public class VmModifyResult extends BaseResult {

    private String vmName;
    private String vmId;
    private String cpu;
    private String memory;
    private String oldCpu;
    private String oldMemory;

    private List<VmNic> nics = new ArrayList<VmNic>();
    private List<VmDisk> disks = new ArrayList<VmDisk>();

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

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
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
