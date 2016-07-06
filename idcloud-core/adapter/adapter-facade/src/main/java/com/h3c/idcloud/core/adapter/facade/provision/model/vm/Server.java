package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.ServerSecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.Disk;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Set;

public class Server extends CommonAdapterResult {

    private String status;

    private String hostName;
    private String host;
    private String name;
    private String uuid;
    private List<ServerNic> nics;
    private List<ServerNetwork> networks;
    private List<ServerSecurityGroup> securitygroups;
    private List<Disk> disks;
    private List<Volume> volumes;
    private Set<Port> ports;

    public Set<Port> getPorts() {
        return ports;
    }

    public void setPorts(Set<Port> ports) {
        this.ports = ports;
    }

    private List<VmNic> vmNics;

    public List<VmNic> getVmNics() {
        return vmNics;
    }

    public void setVmNics(List<VmNic> vmNics) {
        this.vmNics = vmNics;
    }

    @JsonProperty("security_groups")
    public void setKVmSecuritygroups(List<ServerSecurityGroup> securitygroups) {
        this.securitygroups = securitygroups;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @JsonProperty("hostname")
    public void setKvmHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHost() {
        return host;
    }

    @JsonProperty("hypervisor_hostname")
    public void setHost(String host) {
        this.host = host;
    }

    public List<ServerNic> getNics() {
        return nics;
    }

    public void setNics(List<ServerNic> nics) {
        this.nics = nics;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServerNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(List<ServerNetwork> networks) {
        this.networks = networks;
    }

    public List<ServerSecurityGroup> getSecuritygroups() {
        return securitygroups;
    }

    public void setSecuritygroups(List<ServerSecurityGroup> securitygroups) {
        this.securitygroups = securitygroups;
    }

    public List<Disk> getDisks() {
        return disks;
    }

    public void setDisks(List<Disk> disks) {
        this.disks = disks;
    }

    @JsonProperty("volumes")
    public void setKvmDisks(List<Disk> disks) {
        this.disks = disks;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("id")
    public void setKvmUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Server{" +
                "status='" + status + '\'' +
                ", hostName='" + hostName + '\'' +
                ", host='" + host + '\'' +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", nics=" + nics +
                ", networks=" + networks +
                ", securitygroups=" + securitygroups +
                ", disks=" + disks +
                ", volumes=" + volumes +
                '}';
    }
}
