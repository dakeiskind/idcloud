package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;

import java.util.List;

public class DiskFormat extends Base {
    private String targetSerIp;
    private String targetUser;
    private String targetPwd;
    private List<VmDisk> disks;

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

    public List<VmDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<VmDisk> disks) {
        this.disks = disks;
    }
}
