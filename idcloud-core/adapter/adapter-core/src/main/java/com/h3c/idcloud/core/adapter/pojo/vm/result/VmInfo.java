package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.Network;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;

import java.util.List;

public class VmInfo {
    private List<DiskCreateResult> disks;
    private String hostName;
    private String host;

    /**
     * just return
     */
    private String id;
    private String name;

    private List<Network> networks;

    private List<VmNic> nics;

    private String status;

    /**
     * generate from adapter
     */
    private String uuid;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<DiskCreateResult> getDisks() {
        return disks;
    }

    public void setDisks(List<DiskCreateResult> disks) {
        this.disks = disks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<VmNic> getNics() {
        return nics;
    }

    public void setNics(List<VmNic> nics) {
        this.nics = nics;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
