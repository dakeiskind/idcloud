package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.Network;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;

import java.util.List;

public class VmRebuildResult extends BaseResult {

    /**
     * just return
     */
    private String vmName;

    /**
     * just return
     */
    private String id;

    /**
     * generate from adapter
     */
    private String uuid;

    private List<DiskCreateResult> disks;

    private List<VmNic> nics;

    private List<Network> networks;

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public List<DiskCreateResult> getDisks() {
        return disks;
    }

    public void setDisks(List<DiskCreateResult> disks) {
        this.disks = disks;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<VmNic> getNics() {
        return nics;
    }

    public void setNics(List<VmNic> nics) {
        this.nics = nics;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
